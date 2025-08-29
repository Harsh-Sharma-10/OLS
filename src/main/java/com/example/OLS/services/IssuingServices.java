package com.example.OLS.services;

import com.example.OLS.Model.Book;
import com.example.OLS.Model.IssueTransaction;
import com.example.OLS.Model.User;
import com.example.OLS.Repository.IssueRepository;
import com.example.OLS.Repository.RepositoryBooks;
import com.example.OLS.Repository.Repouser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public  class IssuingServices implements com.example.OLS.Repository.IssuingServices {



    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private Repouser repouser;

    @Autowired
    private RepositoryBooks repositoryBooks;


    @Override
    public List<IssueTransaction> getALLtransactions() {
        return issueRepository.findAll();
    }

    @Override
    public IssueTransaction gettransactionbyid(UUID id){
        return issueRepository.findById(id).orElse(null);
    }
    @Override
    public IssueTransaction saveIssueTransaction(int userid,String bookid){
        User user = repouser.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = repositoryBooks.findById(bookid)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        boolean alreadyExists = false;
        alreadyExists = issueRepository.existsByUserAndBookAndReturnedFalse(user,book);
        if(alreadyExists){
            throw new RuntimeException("This transaction is already exists with "+user.getUsername());
        }

        if ((!book.isStatus()) || book.getQuantity() == 0) throw new RuntimeException("Book Status Not Active");
        IssueTransaction issueTransaction = new IssueTransaction();
        issueTransaction.setUser(user);
        issueTransaction.setBook(book);
        issueTransaction.setIssueDate(LocalDate.now());
        issueTransaction.setReturned(false);
        issueTransaction.setFine(0.0);
        book.setQuantity(book.getQuantity() - 1);   /// Updating a book after a transaction
        repositoryBooks.save(book);

        return issueRepository.save(issueTransaction); /// finally save the transaction
    }
    @Override
    public IssueTransaction returnBook(int id,String bookid) {
        IssueTransaction tx = issueRepository.findByUserIdAndBookId(id,bookid);


        if (tx.isReturned()) {
            throw new RuntimeException("Book already returned");
        }
        if(!(tx.getUser().getId().equals(id))){
            throw new AccessDeniedException("You can not return a book which is not belongs to you");
        }
        tx.setReturned(true);
        tx.setReturnDate(LocalDate.now());

        // Restore stock
        Book book = tx.getBook();
        book.setQuantity(book.getQuantity() + 1);
        repositoryBooks.save(book);

        // Fine calculation (example: ₹10 per day after 7 days)
        long days = java.time.temporal.ChronoUnit.DAYS.between(tx.getIssueDate(), tx.getReturnDate());
        if (days > 7) {
            tx.setFine((days - 7) * 10);
        }

        return  issueRepository.save(tx);
    }


}

