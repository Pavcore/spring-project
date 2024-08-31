package com.javarush.springproject.controller;

import com.javarush.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView startPage(ModelAndView modelAndView) {
        modelAndView.setViewName("/user");
        return modelAndView;
    }

    @PutMapping("/user/updateLogin")
    public ModelAndView updateLogin(@RequestParam String editLogin,
                                    Principal principal,
                                    ModelAndView modelAndView) {
        userService.updateUserLogin(editLogin, principal, modelAndView);
        return modelAndView;
    }

    @PutMapping("/user/updatePassword")
    public ModelAndView updatePassword(@RequestParam String editPassword,
                                       Principal principal,
                                       ModelAndView modelAndView) {
        userService.updateUserPassword(editPassword, principal, modelAndView);
        return modelAndView;
    }

    @DeleteMapping("/user/deleteUser")
    public ModelAndView deleteUser(Principal principal, ModelAndView modelAndView) {
        userService.deleteUser(principal, modelAndView);
        return modelAndView;
    }
}
