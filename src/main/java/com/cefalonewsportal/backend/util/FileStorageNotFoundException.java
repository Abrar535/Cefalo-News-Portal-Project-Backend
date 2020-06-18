package com.cefalonewsportal.backend.util;

public class FileStorageNotFoundException extends FileStorageException {
    public FileStorageNotFoundException(String message) {
        super(message);
    }

    public FileStorageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
