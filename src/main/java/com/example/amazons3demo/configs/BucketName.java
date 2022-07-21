package com.example.amazons3demo.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum BucketName {

    BOOK_IMAGE("ebookjava5");
    private final String bucketName;

}

