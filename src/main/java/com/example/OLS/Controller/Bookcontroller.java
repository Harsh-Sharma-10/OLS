package com.example.OLS.Controller;

import com.example.OLS.Model.Book;
import com.example.OLS.Repository.bookservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class Bookcontroller{
    @Autowired
    private bookservices bookServices;
    @GetMapping("/books")
    public ResponseEntity<List<Book>> listofbooks(){
        return new ResponseEntity<>(bookServices.getAllBooks(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getbyid(@PathVariable String id ){
        try {
            Book book = bookServices.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("title/{title}")
    public ResponseEntity<Book> getbytitle(@PathVariable String title){
        Book book1 = bookServices.getBookbyName(title).orElse(null);
        if(book1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(book1, HttpStatus.OK);
        }
    }
    @GetMapping("Author/{author}")
    public ResponseEntity<List<Book>> getbyauthor(@PathVariable String author){
        List<Book> book1 = bookServices.getBookbyAuthor(author).orElse(null);
            if(book1 == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(book1, HttpStatus.OK);
            }
    }
    @PostMapping("/addbook")
    public ResponseEntity<?> addBook(@RequestBody Book book){
        Book book1 = null;
        try{
            book1 = bookServices.addBook(book);
            return new ResponseEntity<>(book1,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addbookList")
    public ResponseEntity<?> addBooks(@RequestBody List<Book> books){
        try{
            bookServices.addbooklist(books);
            return new ResponseEntity<>("Books Inserted successfully",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @RequestBody  Book book) throws Exception {
        Book book1 = null;
        try{
            book1 = bookServices.updateBook(id,book);
            return new ResponseEntity<>("Book Updated",HttpStatus.OK);
        }catch(Exception e){
            return  new ResponseEntity<>("Book Not Found",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookServices.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
