package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.DeleteUserWithCharacters;
import com.javarush.springproject.exception.LoginBusyException;
import com.javarush.springproject.mapper.MapperRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

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
            modelAndView.setViewName("redirect:/users");
        } else throw new LoginBusyException("This login is already taken");
    }

    @Transactional
    public void updateUserPassword(String editPassword, Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal.getName());
        user.setPassword(passwordEncoder.encode(editPassword));
        userRepository.save(user);
        modelAndView.setViewName("redirect:/users");
    }

    @Transactional
    public void deleteUser(Principal principal, ModelAndView modelAndView) {
        User user = getUser(principal.getName());
        if (user.getCharacters().isEmpty()) {
            userRepository.delete(user);
            modelAndView.setViewName("redirect:/login?logout");
        }
        throw new DeleteUserWithCharacters("You need delete your characters before deleting your profile");
    }

    @Transactional
    public List<Character> getAllUserCharacters(Principal principal) {
        return Objects.requireNonNull(userRepository
                .findByLogin(principal.getName())
                .findFirst()
                .orElse(null)).getCharacters();
    }

    @Transactional
    public User getUser(String name) {
        return userRepository.findByLogin(name)
                .findFirst()
                .orElse(null);
    }
}
