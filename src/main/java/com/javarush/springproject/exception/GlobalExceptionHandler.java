package com.javarush.springproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CreateCharacterException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleCreateCharacterException(CreateCharacterException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/characters");
        return handleException(modelAndView, ex);
    }

    @ExceptionHandler(LoadCharacterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleLoadCharacterException(LoadCharacterException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/characters");
        return handleException(modelAndView, ex);
    }

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleRegistrationException(RegistrationException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/registration");
        return handleException(modelAndView, ex);
    }

    @ExceptionHandler(DeleteCharacterException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleDeleteCharacterException(DeleteCharacterException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/characters");
        return handleException(modelAndView, ex);
    }

    @ExceptionHandler(LoginBusyException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleLoginBusyException(LoginBusyException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/users");
        return handleException(modelAndView, ex);
    }

    @ExceptionHandler(UpdateCharacterNameException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleUpdateCharacterNameException(UpdateCharacterNameException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/characters");
        return handleException(modelAndView, ex);
    }

    @ExceptionHandler(DeleteUserWithCharacters.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleDeleteUserWithCharacter(DeleteUserWithCharacters ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path", "/users");
        return handleException(modelAndView, ex);
    }

    private ModelAndView handleException(ModelAndView modelAndView, RuntimeException ex) {
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setViewName("dataError");
        return modelAndView;
    }
}
