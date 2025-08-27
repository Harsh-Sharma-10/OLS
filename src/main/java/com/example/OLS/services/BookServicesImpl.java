package com.example.OLS.services;

import com.example.OLS.Model.Book;
import com.example.OLS.Repository.RepositoryBooks;
import com.example.OLS.Repository.bookservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookServicesImpl implements bookservices {
    @Autowired
    private RepositoryBooks repositoryBooks;
    @Override
    public Book addBook(Book book){
        return repositoryBooks.save(book);
    }
    @Override
    public void addbooklist(List<Book> books) {
             repositoryBooks.saveAll(books);
    }
    @Override
    public Book updateBook(String id, Book book)throws Exception {
       Book book1 = repositoryBooks.findById(id).orElse(null);
       if(book1 != null) {
           book1.setTitle(book.getTitle());
           book1.setQuantity(book.getQuantity());
           book1.setStatus(book.isStatus());
           book1.setISBN(book.getISBN());
           return repositoryBooks.save(book1);
       }else {
           throw new Exception("Book Not Found");
       }
    }
    @Override public void deleteBook(String id) {
        repositoryBooks.deleteById(id);
    }
    @Override
    public List<Book> getAllBooks() {
        return repositoryBooks.findAll();
    }
      @Override
     public Book getBookById(String id){
        return repositoryBooks.findById(id).orElse(null);
    }
     @Override
    public Optional<Book> getBookbyName(String title) {
        return repositoryBooks.findBytitle(title);
     }
     @Override
    public Optional<List<Book>> getBookbyAuthor(String author) {
        return repositoryBooks.findAllByauthor(author);
     }

}
