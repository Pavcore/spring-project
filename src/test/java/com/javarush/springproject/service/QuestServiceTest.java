package com.javarush.springproject.service;

import com.javarush.springproject.entity.Quest;
import com.javarush.springproject.repository.QuestRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class QuestServiceTest {

    @MockBean
    private QuestRepo questRepo;

    @Autowired
    private QuestService questService;

    @Test
    void setAttributeQuest() {
        ModelAndView modelAndView = new ModelAndView();
        long level = 1;

        Quest mockQuest = new Quest();
        mockQuest.setMainText("Test main text");
        mockQuest.setCorrectAnswer("Correct");
        mockQuest.setWrongAnswer("Wrong");
        mockQuest.setLooseText("Lose text");

        when(questRepo.findById(level)).thenReturn(Optional.of(mockQuest));

        questService.setAttributeQuest(modelAndView, level);

        //check use method
        verify(questRepo, times(1)).findById(level);

        //check add attributes
        assertEquals("Test main text", modelAndView.getModel().get("stage"));
        assertEquals("Correct", modelAndView.getModel().get("firstAnswer"));
        assertEquals("Wrong", modelAndView.getModel().get("secondAnswer"));
        assertEquals("Lose text", modelAndView.getModel().get("loseAttribute"));
    }

    @Test
    void getLengthQuest() {
        long level = 1;

        when(questService.getLengthQuest()).thenReturn(level);

        assertEquals(level, questService.getLengthQuest());
    }
}