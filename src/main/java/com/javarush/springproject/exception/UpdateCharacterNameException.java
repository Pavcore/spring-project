package com.javarush.springproject.exception;

public class UpdateCharacterNameException extends RuntimeException {
    public UpdateCharacterNameException(String message) {
        super(message);
    }
}
