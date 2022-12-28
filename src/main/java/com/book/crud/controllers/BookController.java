package com.book.crud.controllers;

import com.book.crud.entities.Book;
import com.book.crud.helpers.Message;
import com.book.crud.services.BookService;

import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    //To view index page
    @GetMapping("/")
    public String home(){
        return "index";
    }

    //To view add book page
    @GetMapping("/addBook")
    public String viewAddBookPage(Model model){
        model.addAttribute("book", new Book());
        return "addBook";
    }

    //Add a new book
    @PostMapping("/addBookProcess")
    public String addBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
    	try {
            //Server-side validation
            if(bindingResult.hasErrors()){
                model.addAttribute("book", book);
                return "addBook";
            }

    		//When a user add a book, automatically will be available for sharing
        	book.setStatus(true);
        	//System.out.println(book);
            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("msg", new Message("Your book is added successfully.", "alert-info"));
            
            return "redirect:/addBook";
    	}
    	catch(Exception E) {
            model.addAttribute("book", book);
            redirectAttributes.addFlashAttribute("msg", new Message("Something went wrong! Please Try again later.", "alert-danger"));
    		return "redirect:/addBook";
    	}
    }

    //To view book shelf page
    @GetMapping("/bookShelf/{page}")
    public String bookShelf(@PathVariable int page, Model model){
        Pageable pageable = PageRequest.of(page, 5);
        Page<Book> books = bookService.getAllBooks(pageable);

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", books.getTotalPages());

        return "bookShelf";
    }

    //To view edit book page
    @GetMapping("/editBook/{id}")
    public String viewEditBook(@PathVariable long id, Model model, RedirectAttributes redirectAttributes){
        Book book = bookService.getBookById(id);
        if(book == null){
            redirectAttributes.addFlashAttribute("msg", new Message("Something went wrong! Please try again later.", "alert-danger"));
            return "redirect:/bookShelf/0";
        }
        else{
            model.addAttribute("book", book);
            return "editBook";
        }
    }

    //To edit an existing book
    @PostMapping("/editBookProcess")
    public String editBook(@Valid @ModelAttribute Book book, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        try{
            //Server-side Validation
            if(bindingResult.hasErrors()){
                model.addAttribute("book", book);
                return "editBook";
            }

            Book existing = bookService.getBookById(book.getId());
            book.setStatus(existing.isStatus()); //Set the previous status
            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("msg", new Message("Your book information is updated.", "alert-info"));
            return "redirect:/bookShelf/0";
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("msg", new Message("Something went wrong! Please try again later.", "alert-danger"));
            return "redirect:/bookShelf/0";
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

//    //Update an existing book
//    @PutMapping("/update")
//    public Book updateBook(@RequestBody Book book){
//        return bookService.updateBook(book);
//    }

    //Delete a book by its id
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable long id, RedirectAttributes redirectAttributes){
        //To get the existing book
        Book existing  = bookService.getBookById(id);
        if(existing == null){
            redirectAttributes.addFlashAttribute("msg", new Message("Something went wrong! Please try again later.", "alert-danger"));
            return "redirect:/bookShelf/0";
        }
        else{
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("msg", new Message("Book is deleted successfully.", "alert-info"));
            return "redirect:/bookShelf/0";
        }
    }

}
