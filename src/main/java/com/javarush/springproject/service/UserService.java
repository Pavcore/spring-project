package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.LoginBusyException;
import com.javarush.springproject.mapper.MapperRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

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
    public void saveUser(UserRequestTo userRequestTo) {
        if (getUser(userRequestTo.getLogin()) == null) {
            User user = MapperRequest.map(userRequestTo);
            user.setPassword(passwordEncoder.encode(userRequestTo.getPassword()));
            userRepository.save(user);
        }
    }

    @Transactional
    public void updateUserLogin(String editLogin, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal.getName());
        if (getUser(editLogin) == null) {
            user.setLogin(editLogin);
            userRepository.save(user);
            modelAndView.setViewName("redirect:/user");
        } else throw new LoginBusyException("This login is already taken");
    }

    @Transactional
    public void updateUserPassword(String editPassword, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal.getName());
        user.setPassword(passwordEncoder.encode(editPassword));
        userRepository.save(user);
        modelAndView.setViewName("redirect:/user");
    }

    @Transactional
    public void deleteUser(Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal.getName());
        userRepository.delete(user);
        modelAndView.setViewName("redirect:/");
    }

    @Transactional
    public List<Character> getAllUserCharacters(Principal principal) {
        User user = userRepository
                .findByLogin(principal.getName())
                .findFirst()
                .get();
        return user.getCharacters();
    }

    @Transactional
    public User getUser(String name) {
        return userRepository.findByLogin(name)
                .findFirst()
                .orElse(null);
    }
}
