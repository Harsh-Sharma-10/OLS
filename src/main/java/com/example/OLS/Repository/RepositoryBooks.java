package com.example.OLS.Repository;

import com.example.OLS.Model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryBooks extends JpaRepository<Book,String>{
    Optional<Book>findBytitle(String title);
    Optional<List<Book>>findAllByauthor(String author);

}
