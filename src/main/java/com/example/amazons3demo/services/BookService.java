package com.example.amazons3demo.services;

import com.example.amazons3demo.models.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

        Book saveBook(String title, String description, MultipartFile file);
        byte[] downloadBookImage(Long id);
        List getAllBooks();

}
