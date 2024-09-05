package com.javarush.springproject.service;

import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityContextLogoutHandler logoutHandler;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Инициализация моков
    }

    @Test
    void testSaveUserWhenUserNotExist() {
        UserRequestTo userRequestTo = UserRequestTo.builder()
                .login("userNotExist")
                .password("testPassword")
                .build();

        when(userRepository.findByLogin(userRequestTo.getLogin())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        userService.saveUser(userRequestTo);

        verify(passwordEncoder, times(1)).encode("testPassword");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSaveUserWhenUserExist() {
        UserRequestTo userRequestTo = UserRequestTo.builder()
                .login("userExist")
                .password("testPassword")
                .build();

        when(userRepository.findByLogin(userRequestTo.getLogin())).thenReturn(Optional.of(new User()));

        userService.saveUser(userRequestTo);

        verify(passwordEncoder, times(0)).encode("testPassword");

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void updateUserLogin() {
        String login = "userNotExist";
        Principal principal = mock(Principal.class);
        ModelAndView modelAndView = new ModelAndView();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = mock(User.class);

        when(principal.getName()).thenReturn("existingUser");

        when(userRepository.findByLogin("existingUser")).thenReturn(Optional.of(user));

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        userService.updateUserLogin(login, principal, modelAndView, request, response);

        verify(user).setLogin(login);
        verify(userRepository, times(1)).save(user);
        verify(logoutHandler, times(1)).logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        assertEquals("redirect:/login?logout", modelAndView.getViewName());
    }

    @Test
    void updateUserPassword() {
        String password = "randomPassword";
        Principal principal = mock(Principal.class);
        ModelAndView modelAndView = new ModelAndView();
        User user = mock(User.class);

        when(principal.getName()).thenReturn("");

        when(userRepository.findByLogin("")).thenReturn(Optional.of(user));

        when(passwordEncoder.encode(password)).thenReturn("randomPassword");

        userService.updateUserPassword(password, principal, modelAndView);

        verify(user).setPassword(password);
        verify(userRepository, times(1)).save(user);
        assertEquals("redirect:/users", modelAndView.getViewName());
    }

    @Test
    void deleteUser() {
        Principal principal = mock(Principal.class);
        ModelAndView modelAndView = new ModelAndView();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = mock(User.class);

        when(principal.getName()).thenReturn("");

        when(userRepository.findByLogin("")).thenReturn(Optional.of(user));

        when(user.getCharacters()).thenReturn(new ArrayList<>());

        userService.deleteUser(principal, modelAndView, request, response);

        verify(userRepository, times(1)).delete(user);
        verify(logoutHandler, times(1)).logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        assertEquals("redirect:/login?logout", modelAndView.getViewName());
    }

    @Test
    void testGetAllUserCharacters() {
        Principal principal = mock(Principal.class);

        when(principal.getName()).thenReturn("");

        when(userRepository.findByLogin("")).thenReturn(Optional.of(new User()));

        userService.getAllUserCharacters(principal);

        verify(userRepository, times(1)).findByLogin(principal.getName());
    }

    @Test
    void testGetUser() {
        String login = "userNotExist";

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(new User()));

        userService.getUser(login);

        verify(userRepository, times(1)).findByLogin(login);
    }
}