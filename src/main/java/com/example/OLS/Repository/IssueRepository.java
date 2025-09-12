package com.example.OLS.Repository;

import com.example.OLS.Model.Book;
import com.example.OLS.Model.IssueTransaction;
import com.example.OLS.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<IssueTransaction, UUID> {

    Optional<IssueTransaction> findByUserUsernameAndBookId(String username, String bookid);
    boolean existsByUserAndBookAndReturnedFalse(User user, Book book);///this method ckeck wether the book is already issued to person
}
