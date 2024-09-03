package com.javarush.springproject.controller;

import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public ModelAndView empty() {
        return new ModelAndView("/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        return new ModelAndView("registration");
    }

    @PostMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@ModelAttribute @Valid UserRequestTo userRequestTo, ModelAndView modelAndView) {
        modelAndView = loginService.register(userRequestTo, modelAndView);
        return modelAndView;
    }
}
