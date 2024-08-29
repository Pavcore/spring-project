package com.javarush.springproject.controller;

import com.javarush.springproject.service.CharacterService;
import com.javarush.springproject.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("/index")
public class IndexController {

    private final CharacterService characterService;
    private final GameService gameService;

    @Autowired
    public IndexController(CharacterService characterService, GameService gameService) {
        this.characterService = characterService;
        this.gameService = gameService;
    }

    @GetMapping("/index")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/index/create")
    public ModelAndView create(HttpServletRequest req, ModelAndView modelAndView) {
        HttpSession session = req.getSession(true);
        if (characterService.save(req, session)) {
            gameService.increaseGameAmount();
            modelAndView.setViewName("redirect:/game");
        } else {
            String incorrectData = "this name is already taken";
            modelAndView.addObject("incorrectData", incorrectData);
            modelAndView.addObject("path", "/index");
            modelAndView.setViewName("dataError");
        }
        return modelAndView;
    }

    @PostMapping("/index/load")
    public ModelAndView load(HttpServletRequest req, ModelAndView modelAndView) {
        HttpSession session = req.getSession(true);
        if (characterService.haveCharacterCreated(req, session)) {
            gameService.increaseGameAmount();
            modelAndView.setViewName("redirect:/game");
        } else {
            String incorrectData = "you didn't create this character";
            modelAndView.addObject("incorrectData", incorrectData);
            modelAndView.addObject("path", "/index");
            modelAndView.setViewName("dataError");
        }
        return modelAndView;
    }
}
