package com.javarush.springproject.service;

import com.javarush.springproject.dto.CharacterClass;
import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.dto.UserResponseTo;
import com.javarush.springproject.entity.Character;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class IndexServiceTest {

    @MockBean
    CharacterService characterService;

    @Autowired
    IndexService indexService;

    @Test
    void backPageTest() {
        HttpSession httpSession = mock(HttpSession.class);
        ModelAndView modelAndView = new ModelAndView();

        UserResponseTo userResponseTo = UserResponseTo.builder()
                .id(1L)
                .login("randomName")
                .build();
        CharacterResponseTo characterResponseTo = CharacterResponseTo.builder()
                .id(1L)
                .name("indexName")
                .characterClass(CharacterClass.DRUID)
                .gameQuantity(4)
                .winQuantity(2)
                .userResponse(userResponseTo)
                .build();


        when(httpSession.getAttribute("character")).thenReturn(characterResponseTo);
        ModelAndView view = indexService.backPage(modelAndView, httpSession);
        //check correct view
        assertEquals("redirect:/characters", view.getViewName());

        ArgumentCaptor<Character> captor = ArgumentCaptor.forClass(Character.class);
        //check call saveGameStatistic in method
        verify(characterService, times(1)).saveGameStatistic(captor.capture());

        Character capturedCharacter = captor.getValue();
        //check correct mapping and increased winQuantity
        assertEquals("indexName", capturedCharacter.getName());
        assertEquals(CharacterClass.DRUID, capturedCharacter.getCharacterClass());
        assertEquals(3, capturedCharacter.getGameQuantity());
        assertEquals(2, capturedCharacter.getWinQuantity());
    }
}