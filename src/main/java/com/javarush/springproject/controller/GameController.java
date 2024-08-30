package com.javarush.springproject.controller;

import com.javarush.springproject.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game")
    public ModelAndView startGame(ModelAndView modelAndView) {
        return gameService.startGame(modelAndView);
    }

    @GetMapping("/gameWin")
    public ModelAndView getGameWin() {
        return new ModelAndView("gameWin");
    }

    @PostMapping("/mainGame")
    public ModelAndView mainStage(@RequestParam String nextStage, ModelAndView modelAndView) {
        return gameService.mainGame(nextStage, modelAndView);
    }

    @PostMapping("/statistic")
    public ModelAndView gameStatistic(Principal principal, ModelAndView modelAndView) {
        return gameService.gameStatistics(principal, modelAndView);
    }
}
