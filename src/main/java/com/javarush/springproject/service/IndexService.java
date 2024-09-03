package com.javarush.springproject.service;

import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.mapper.MapperResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class IndexService {

    private final CharacterService characterService;

    @Autowired
    public IndexService(CharacterService characterService) {
        this.characterService = characterService;
    }

    public ModelAndView backPage(ModelAndView modelAndView, HttpSession httpSession) {
        CharacterResponseTo characterResponseTo = (CharacterResponseTo) httpSession.getAttribute("character");
        Character character = MapperResponse.map(characterResponseTo);
        long gameQuantity = character.getGameQuantity() - 1;
        character.setGameQuantity(gameQuantity);
        characterService.saveGameStatistic(character);
        modelAndView.setViewName("redirect:/characters");
        return modelAndView;
    }

}
