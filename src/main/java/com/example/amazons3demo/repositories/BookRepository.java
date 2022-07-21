package com.example.amazons3demo.repositories;

import com.example.amazons3demo.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository {

    Book findByTitle(String title);
}
