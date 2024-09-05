package com.javarush.springproject.service;

import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.RegistrationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoginServiceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Test
    public void testRegisterSuccessfulRegistration() {
        UserRequestTo newUser = UserRequestTo.builder()
                .login("newLogin")
                .password("password")
                .build();
        ModelAndView modelAndView = new ModelAndView();

        when(userService.getUser(eq("newLogin"))).thenReturn(null);

        ModelAndView result = loginService.register(newUser, modelAndView);

        //check correct view address and call saveUser in method
        verify(userService, times(1)).saveUser(any(UserRequestTo.class));
        assertEquals("redirect:/", result.getViewName());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        UserRequestTo existingUser = UserRequestTo.builder()
                .login("existingLogin")
                .password("password")
                .build();
        ModelAndView modelAndView = new ModelAndView();

        when(userService.getUser(existingUser.getLogin())).thenReturn(new User());

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
            loginService.register(existingUser, modelAndView));


        //check correct view address and throw exception in method
        assertEquals("Login already in use", exception.getMessage());
        verify(userService, never()).saveUser(existingUser);
    }
}