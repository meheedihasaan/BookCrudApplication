package com.book.crud.services;

import com.book.crud.entities.Book;
import com.book.crud.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    //To add a single Book
    public Book addBook(Book book){
        return bookRepo.save(book);
    }

    //To get all the books
    public Page<Book> getAllBooks(Pageable pageable){
        return bookRepo.findAll(pageable);
    }

    //To get a book by it's id
    public Book getBookById(long id){
        return bookRepo.findById(id).orElse(null);
    }

    //To update an existing book
    public Book updateBook(Book book){
        Book existing = bookRepo.findById(book.getId()).orElse(null);

        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPublisher(book.getPublisher());
        existing.setEdition(book.getEdition());
        existing.setIsbn(book.getIsbn());
        existing.setPages(book.getPages());

        return bookRepo.save(existing);
    }

    //To delete a book
    public void deleteBook(long id){
        System.out.println(id+" is deleted.");
        bookRepo.deleteById(id);
    }

}
