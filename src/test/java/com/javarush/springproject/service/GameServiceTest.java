package com.javarush.springproject.service;

import com.javarush.springproject.dto.CharacterClass;
import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.dto.UserResponseTo;
import com.javarush.springproject.entity.Character;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {

    @MockBean
    private CharacterService characterService;

    @MockBean
    private UserService userService;

    @MockBean
    private QuestService questService;

    @Autowired
    private GameService gameService;

    private Principal principal;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(gameService, "level", 1);
    }

    @Test
    void gameStatisticsTest() {
        ModelAndView view = new ModelAndView();

        ModelAndView result = gameService.gameStatistics(principal, view);

        //check correct view address and call getAllUserCharacters in method
        verify(userService, times(1)).getAllUserCharacters(principal);
        assertEquals("statistic", result.getViewName());
    }

    @Test
    void nextQuestStageIsLose() {
        String nextStage = "lose";
        ModelAndView view = new ModelAndView();
        long level = 1;
        HttpSession session = mock(HttpSession.class);

        ModelAndView modelAndView = gameService.mainGame(nextStage, view, session);
        //check correct view address and call setAttribute in method
        assertEquals("gameOver", modelAndView.getViewName());
        verify(questService, times(1)).setAttributeQuest(view, level);
    }

    @Test
    void nextQuestStageIsWin() {
        String nextStage = "win";
        ModelAndView view = new ModelAndView();
        int level = 6;
        HttpSession session = mock(HttpSession.class);
        UserResponseTo userResponseTo = UserResponseTo.builder()
                .id(1L)
                .login("randomName")
                .build();
        CharacterResponseTo characterResponseTo = CharacterResponseTo.builder()
                .id(1L)
                .name("randomName")
                .characterClass(CharacterClass.PALADIN)
                .gameQuantity(2)
                .winQuantity(1)
                .userResponse(userResponseTo)
                .build();

        ReflectionTestUtils.setField(gameService, "level", level);

        when(questService.getLengthQuest()).thenReturn(((long) level));
        when(session.getAttribute("character")).thenReturn(characterResponseTo);

        ModelAndView modelAndView = gameService.mainGame(nextStage, view, session);
        //check correct view address
        assertEquals("gameWin", modelAndView.getViewName());

        ArgumentCaptor<Character> captor = ArgumentCaptor.forClass(Character.class);
        //check call saveGameStatistic in method
        verify(characterService, times(1)).saveGameStatistic(captor.capture());

        Character capturedCharacter = captor.getValue();
        //check correct mapping and increased winQuantity
        assertEquals("randomName", capturedCharacter.getName());
        assertEquals(CharacterClass.PALADIN, capturedCharacter.getCharacterClass());
        assertEquals(2, capturedCharacter.getGameQuantity());
        assertEquals(2, capturedCharacter.getWinQuantity());
    }

    @Test
    void nextQuestStageIsContinues() {
        String nextStage = "continue";
        ModelAndView view = new ModelAndView();
        int level = 1;
        HttpSession session = mock(HttpSession.class);

        ModelAndView modelAndView = gameService.mainGame(nextStage, view, session);

        //the value of the variable level is initially 1, so this error is false, there is no point in wrapping it in try-catch
        int updatedLevel = (int) ReflectionTestUtils.getField(gameService, "level");
        //check correct view address and level increased
        assertEquals("game", modelAndView.getViewName());
        assertEquals(level + 1, updatedLevel);
        //check call method
        verify(questService, times(1)).setAttributeQuest(view, level + 1);
    }

    @Test
    void startGameTest() {
        ModelAndView view = new ModelAndView();
        int level = 1;

        ModelAndView modelAndView = gameService.startGame(view);
        //check correct view address
        assertEquals("game", modelAndView.getViewName());
        //check call method
        verify(questService, times(1)).setAttributeQuest(view, level);
    }
}