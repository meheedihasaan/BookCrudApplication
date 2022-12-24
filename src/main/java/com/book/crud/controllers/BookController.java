package com.book.crud.controllers;

import com.book.crud.entities.Book;
import com.book.crud.services.BookService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    //To view index page
    @GetMapping("/")
    public String home(Model model){
    	List<Book> books = bookService.getAllBooks();
    	model.addAttribute("books", books);
    	
        return "index";
    }

    //To view add book page
    @GetMapping("/addBook")
    public String viewAddBookPage(){
        return "addBook";
    }

    //Add a new book
    @PostMapping("/addBookProcess")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes){
    	try {
    		//When a user add a book, automatically will be available for sharing
        	book.setStatus(true);
        	//System.out.println(book);
            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("msg", "Your book is added successfully.");
            
            return "redirect:/addBook";
    	}
    	catch(Exception E) {
            redirectAttributes.addFlashAttribute("msg", "Something went wrong! Please Try again later.");
    		return "redirect:/addBook";
    	}
    }

    //To view edit book page
    @GetMapping("/editBook/{id}")
    public String viewEditBook(@PathVariable long id, Model model, RedirectAttributes redirectAttributes){
        Book book = bookService.getBookById(id);
        if(book == null){
            redirectAttributes.addFlashAttribute("msg", "Something went wrong! Please try again later.");
            return "redirect:/";
        }
        else{
            model.addAttribute("book", book);
            return "editBook";
        }
    }

    //To edit an existing book
    @PostMapping("/editBookProcess")
    public String editBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes){
        try{
            Book existing = bookService.getBookById(book.getId());
            book.setStatus(existing.isStatus()); //Set the previous status
            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("msg", "Your book information is updated.");
            return "redirect:/";
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("msg", "Something went wrong! Please try again later.");
            return "redirect:/";
        }
    }

//    //Get all the books
//    @GetMapping("/books")
//    public List<Book> getAllBooks(){
//        return bookService.getAllBooks();
//    }

//    //Get a book by its id
//    @GetMapping("/books/{id}")
//    public Book getBookById(@PathVariable long id){
//        return bookService.getBookById(id);
//    }

    //Update an existing book
    @PutMapping("/update")
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    //Delete a book by its id
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable long id, RedirectAttributes redirectAttributes){
        //To get the existing book
        Book existing  = bookService.getBookById(id);
        if(existing == null){
            redirectAttributes.addFlashAttribute("msg", "Something went wrong! Please try again later.");
            return "redirect:/";
        }
        else{
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("msg", "Book is deleted successfully.");
            return "redirect:/";
        }
    }

}
