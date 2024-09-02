package com.javarush.springproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GameService {

    private final AtomicLong atomicLong = new AtomicLong();
    private final UserService userService;
    private final QuestService questService;
    private int level = 1;

    @Autowired
    public GameService(UserService userService, QuestService questService) {
        this.userService = userService;
        this.questService = questService;
    }

    public void increaseGameAmount() {
        atomicLong.getAndIncrement();
    }

    @Transactional
    public ModelAndView gameStatistics(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("gameQuantity", gameQuantity());
        modelAndView.addObject("characterList", userService.getAllUserCharacters(principal));
        modelAndView.setViewName("statistic");
        return modelAndView;
    }

    public ModelAndView mainGame(String nextStage, ModelAndView modelAndView) {
        modelAndView.setViewName("game");
        if (nextStage != null && nextStage.equals("lose")) {
            questService.setAttributeQuest(modelAndView, level);
            level = 1;
            modelAndView.setViewName("gameOver");
        } else if (level == questService.getLengthQuest()) {
            level = 1;
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

    private long gameQuantity() {
        return atomicLong.get();
    }
}
