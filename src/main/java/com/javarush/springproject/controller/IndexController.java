package com.javarush.springproject.controller;

import com.javarush.springproject.dto.CharacterRequestTo;
import com.javarush.springproject.exception.CreateCharacterException;
import com.javarush.springproject.exception.LoadCharacterException;
import com.javarush.springproject.service.CharacterService;
import com.javarush.springproject.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute CharacterRequestTo characterRequestTo,
                               ModelAndView modelAndView,
                               Principal principal) {
        if (characterService.save(principal, characterRequestTo)) {
            gameService.increaseGameAmount();
            modelAndView.setViewName("redirect:/game");
        } else throw new CreateCharacterException("This name already exists");
        return modelAndView;
    }

    @PostMapping("/load")
    public ModelAndView load(@RequestParam String createCharacter,
                             Principal principal,
                             ModelAndView modelAndView) {
        if (characterService.haveCharacterCreated(principal, createCharacter)) {
            gameService.increaseGameAmount();
            modelAndView.setViewName("redirect:/game");
        } else throw new LoadCharacterException("Character not found");
        return modelAndView;
    }
}
