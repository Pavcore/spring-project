package com.javarush.springproject.controller;

import com.javarush.springproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView startPage(ModelAndView modelAndView) {
        modelAndView.setViewName("users");
        return modelAndView;
    }

    @PutMapping("/users/updateLogin")
    public ModelAndView updateLogin(@RequestParam @NotBlank @Size(min = 4, max = 32) String editLogin,
                                    Principal principal,
                                    ModelAndView modelAndView,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        userService.updateUserLogin(editLogin, principal, modelAndView, request, response);
        return modelAndView;
    }

    @PutMapping("/users/updatePassword")
    public ModelAndView updatePassword(@RequestParam @NotBlank @Size(min = 8, max = 32) String editPassword,
                                       Principal principal,
                                       ModelAndView modelAndView) {
        userService.updateUserPassword(editPassword, principal, modelAndView);
        return modelAndView;
    }

    @DeleteMapping("/users/deleteUser")
    public ModelAndView deleteUser(Principal principal,
                                   ModelAndView modelAndView,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        userService.deleteUser(principal, modelAndView, request, response);
        return modelAndView;
    }
}
