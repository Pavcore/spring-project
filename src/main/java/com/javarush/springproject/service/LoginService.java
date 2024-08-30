package com.javarush.springproject.service;

import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.RegistrationException;
import com.javarush.springproject.mapper.MapperRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ModelAndView register(UserRequestTo userRequestTo, ModelAndView modelAndView) {
        Optional<User> dbUser = userRepository.findByLogin(userRequestTo.getLogin()).findFirst();
        if (dbUser.isEmpty()) {
            userRequestTo.setPassword(passwordEncoder.encode(userRequestTo.getPassword()));
            User user = MapperRequest.map(userRequestTo);
            userRepository.save(user);
            modelAndView.setViewName("redirect:/");
        } else throw new RegistrationException("Login already in use");
        return modelAndView;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).map(u ->
                        org.springframework.security.core.userdetails.User
                                .withUsername(username)
                                .password(u.getPassword())
                                .build())
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("login not found: " + username));
    }
}
