package com.javarush.springproject.exception;

public class DeleteUserWithCharacters extends RuntimeException {
    public DeleteUserWithCharacters(String message) {
        super(message);
    }
}
