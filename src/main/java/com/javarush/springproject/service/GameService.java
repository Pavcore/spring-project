package com.javarush.springproject.service;

import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.mapper.MapperResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Service
public class GameService {

    private final UserService userService;
    private final QuestService questService;
    private final CharacterService characterService;
    private int level = 1;

    @Autowired
    public GameService(UserService userService, QuestService questService, CharacterService characterService) {
        this.userService = userService;
        this.questService = questService;
        this.characterService = characterService;
    }

    @Transactional
    public ModelAndView gameStatistics(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("characters", userService.getAllUserCharacters(principal));
        modelAndView.setViewName("statistic");
        return modelAndView;
    }

    public ModelAndView mainGame(String nextStage,
                                 ModelAndView modelAndView,
                                 HttpSession httpSession) {
        modelAndView.setViewName("game");
        if (nextStage != null && nextStage.equals("lose")) {
            questService.setAttributeQuest(modelAndView, level);
            level = 1;
            modelAndView.setViewName("gameOver");
        } else if (level == questService.getLengthQuest()) {
            level = 1;
            CharacterResponseTo characterResponseTo = (CharacterResponseTo) httpSession.getAttribute("character");
            Character character = MapperResponse.map(characterResponseTo);
            long gameQuantity = character.getWinQuantity() + 1L;
            character.setWinQuantity(gameQuantity);
            characterService.saveGameStatistic(character);
            modelAndView.setViewName("gameWin");
        } else {
            level++;
            questService.setAttributeQuest(modelAndView, level);
        }
        return modelAndView;
    }

    public ModelAndView startGame(ModelAndView modelAndView) {
        modelAndView.setViewName("game");
        questService.setAttributeQuest(modelAndView, level);
        return modelAndView;
    }
}
