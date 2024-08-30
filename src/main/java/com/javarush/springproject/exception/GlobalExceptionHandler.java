package com.javarush.springproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CreateCharacterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleCreateCharacterException(CreateCharacterException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("path", "/index");
        modelAndView.setViewName("dataError");
        return modelAndView;
    }
    @ExceptionHandler(LoadCharacterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleLoadCharacterException(LoadCharacterException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("path", "/index");
        modelAndView.setViewName("dataError");
        return modelAndView;
    }
    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleRegistrationException(RegistrationException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("path", "/");
        modelAndView.setViewName("dataError");
        return modelAndView;
    }
}
