package com.example.amazons3demo.controllers;

import com.example.amazons3demo.models.Book;
import com.example.amazons3demo.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@AllArgsConstructor
@CrossOrigin("*")
public class BookController {
    BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> saveBook(@RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(service.saveBook(title, description, file), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadBookImage(@PathVariable("id") Long id) {
        return service.downloadBookImage(id);
    }
}
