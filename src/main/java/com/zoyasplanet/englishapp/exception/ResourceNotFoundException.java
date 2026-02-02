package com.zoyasplanet.englishapp.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Long id, String resource) {
        super(resource + " not found with id: " + id);
    }
}
