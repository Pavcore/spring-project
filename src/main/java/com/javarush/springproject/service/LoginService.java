package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    private final UserRepo userRepository;

    @Autowired
    public LoginService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean login(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login).findFirst();
        if (optionalUser.isEmpty()) {
            return false;
        }
        User dbUser = optionalUser.get();
        return dbUser.getLogin().equals(login) && dbUser.getPassword().equals(password);
    }

    @Transactional
    public ModelAndView register(String login, String password, ModelAndView modelAndView) {
        User user = User.builder()
                .login(login)
                .password(password)
                .build();
        Optional<User> dbUser = userRepository.findByLogin(login).findFirst();
        if (dbUser.isEmpty()) {
            userRepository.save(user);
            modelAndView.setViewName("redirect:/");
        } else {
            String incorrectData = "this login is busy";
            modelAndView.addObject("incorrectData", incorrectData);
            modelAndView.addObject("path", "/registration");
            modelAndView.setViewName("dataError");
        }
        return modelAndView;
    }
}
