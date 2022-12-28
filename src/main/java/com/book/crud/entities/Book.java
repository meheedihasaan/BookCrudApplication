package com.book.crud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Book title is required.")
    private String title;

    @NotBlank(message = "Author name is required.")
    private String author;

    @NotBlank(message = "Publisher or publication name is required.")
    private String publisher;

    @NotBlank(message = "Book edition is required.")
    private String edition;

    @NotBlank(message = "ISBN number is required.")
    private String isbn;

    @NotNull(message = "Page number is required.")
    @Min(value = 1, message = "Page number must be greater than or equal 1.")
    private int pages;

    private boolean status;

}
