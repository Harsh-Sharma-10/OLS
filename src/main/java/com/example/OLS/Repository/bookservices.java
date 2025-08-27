package com.example.OLS.Repository;

import com.example.OLS.Model.Book;

import java.util.List;
import java.util.Optional;


public interface bookservices{
    Book addBook(Book book);
    void addbooklist(List<Book> books);
    Book updateBook(String id, Book book) throws Exception;
    void deleteBook(String id);
    List<Book> getAllBooks();
    Book getBookById(String id);
    Optional<Book> getBookbyName(String title);
    Optional<List<Book>>getBookbyAuthor(String author);
}
