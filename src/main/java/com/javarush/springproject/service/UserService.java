package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.dto.UserResponseTo;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.mapper.MapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Service
public class UserService {
    private final UserRepo userRepository;

    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseTo updateUserLogin(String editLogin, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal);
        user.setLogin(editLogin);
        userRepository.save(user);
        return MapperResponse.map(user);
    }

    @Transactional
    public UserResponseTo updateUserPassword(String editPassword, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal);
        user.setPassword(editPassword);
        userRepository.save(user);
        return MapperResponse.map(user);
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
