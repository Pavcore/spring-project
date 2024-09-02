package com.javarush.springproject.service;

import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.exception.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.userdetails.User;

@Service
public class LoginService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public ModelAndView register(UserRequestTo userRequestTo, ModelAndView modelAndView) {
        if (userService.getUser(userRequestTo.getLogin()) == null) {
            userService.saveUser(userRequestTo);
            modelAndView.setViewName("redirect:/");
        } else throw new RegistrationException("Login already in use");
        return modelAndView;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.builder()
                .username(username)
                .password(userService.getUser(username).getPassword())
                .build();
    }
}
