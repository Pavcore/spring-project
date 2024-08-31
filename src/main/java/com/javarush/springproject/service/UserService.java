package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updateUserLogin(String editLogin, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal);
        user.setLogin(editLogin);
        userRepository.save(user);
        modelAndView.setViewName("redirect:/user");
    }

    @Transactional
    public void updateUserPassword(String editPassword, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal);
        user.setPassword(passwordEncoder.encode(editPassword));
        userRepository.save(user);
        modelAndView.setViewName("redirect:/user");
    }

    @Transactional
    public void deleteUser(Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal);
        userRepository.delete(user);
        modelAndView.setViewName("redirect:/");
    }

    private User getUser(Principal principal) {
        return userRepository.findByLogin(principal
                        .getName())
                .findFirst()
                .get();
    }
}
