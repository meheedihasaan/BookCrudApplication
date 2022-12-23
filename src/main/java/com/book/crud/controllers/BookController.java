package com.book.crud.controllers;

import com.book.crud.entities.Book;
import com.book.crud.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    //Add a new book
    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    //Get all the books
    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    //Get a book by its id
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    //Update an existing book
    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    //Delete a book by its id
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
    }

}
