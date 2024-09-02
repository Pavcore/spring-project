package com.javarush.springproject.exception;

public class LoginBusyException extends RuntimeException {
    public LoginBusyException(String message) {
        super(message);
    }
}
