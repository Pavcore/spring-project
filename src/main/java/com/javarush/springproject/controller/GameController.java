package com.javarush.springproject.controller;

import com.javarush.springproject.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game")
    public ModelAndView getGame() {
        ModelAndView modelAndView = new ModelAndView("game");
        return gameService.getGame(modelAndView);
    }

    @GetMapping("/gameWin")
    public ModelAndView getGameWin() {
        return new ModelAndView("gameWin");
    }

    @PostMapping("/mainGame")
    public ModelAndView nextStage(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView("game");
        return gameService.mainGame(req, modelAndView);
    }

    @PostMapping("/statistic")
    public ModelAndView gameStatistic(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        return gameService.gameStatistics(req, modelAndView);
    }
}
