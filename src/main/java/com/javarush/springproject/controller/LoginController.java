package com.javarush.springproject.controller;

import com.javarush.springproject.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("/")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public ModelAndView empty() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.setViewName("/login");
        return modelAndView;
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
    public ModelAndView login(@RequestParam String login,
                              @RequestParam String password,
                              HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        if (loginService.login(login, password)) {
            req.getSession().setAttribute("login", login);
            modelAndView.setViewName("index");
        } else {
            String incorrectData = "incorrect login or password";
            modelAndView.addObject("incorrectData", incorrectData);
            modelAndView.addObject("path", "/login");
            modelAndView.setViewName("dataError");
        }
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@RequestParam String login,
                                     @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = loginService.register(login, password, modelAndView);
        return modelAndView;
    }
}
