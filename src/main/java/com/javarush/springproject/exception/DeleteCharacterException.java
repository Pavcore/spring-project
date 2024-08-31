package com.javarush.springproject.exception;

public class DeleteCharacterException extends RuntimeException {
    public DeleteCharacterException(String message) {
        super(message);
    }
}
