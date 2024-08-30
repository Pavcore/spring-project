package com.javarush.springproject.exception;

public class CreateCharacterException extends RuntimeException {
    public CreateCharacterException(String message) {
        super(message);
    }
}
