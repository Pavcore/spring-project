package com.javarush.springproject.controller;

import com.javarush.springproject.dto.CharacterRequestTo;
import com.javarush.springproject.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters")
    public ModelAndView getPage(Principal principal, ModelAndView modelAndView) {
        return characterService.outputCharacterList(principal, modelAndView);
    }

    @PostMapping("/characters/load")
    public ModelAndView load(@RequestParam String characterName,
                             ModelAndView modelAndView) {
        return characterService.load(characterName, modelAndView);
    }

    @PostMapping("/characters/create_character")
    public ModelAndView createCharacter(Principal principal,
                                        @ModelAttribute CharacterRequestTo characterRequestTo,
                                        ModelAndView modelAndView) {
        return characterService.save(principal, characterRequestTo, modelAndView);
    }

    @PutMapping("/characters/edit_character")
    public ModelAndView editCharacterName(@RequestParam String characterName,
                                          @RequestParam String newCharacterName,
                                          ModelAndView modelAndView) {
        return characterService.updateCharacterName(characterName, newCharacterName, modelAndView);
    }

    @DeleteMapping("/characters/delete_character")
    public ModelAndView deleteCharacter(@RequestParam String characterName,
                                        ModelAndView modelAndView) {
        return characterService.deleteCharacter(characterName, modelAndView);
    }
}
