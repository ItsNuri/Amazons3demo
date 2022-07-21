package com.example.amazons3demo.services;

import com.example.amazons3demo.configs.BucketName;
import com.example.amazons3demo.models.Book;
import com.example.amazons3demo.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService{
    private final FileStore fileStore;
    private final BookRepository repository;
    @Override
    public Book saveBook(String title, String description, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", BucketName.BOOK_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName,Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Book book = Book.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();
        repository.save(book);
        return repository.findByTitle(book.getTitle());
    }

    @Override
    public byte[] downloadBookImage(Long id) {
        Book book = (Book) repository.findById(id).get();
        return fileStore.download(book.getImagePath(), book.getImageFileName());
    }
    @Override
    public List getAllBooks() {
        List todos = new ArrayList<>();
        repository.findAll().forEach(todos::add);
        return todos;
    }

}