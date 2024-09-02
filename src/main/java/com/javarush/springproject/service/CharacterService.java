package com.javarush.springproject.service;

import com.javarush.springproject.dbo.CharacterRepo;
import com.javarush.springproject.dto.CharacterRequestTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.CreateCharacterException;
import com.javarush.springproject.exception.UpdateCharacterNameException;
import com.javarush.springproject.mapper.MapperRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepo characterRepository;
    private final UserService userService;

    @Autowired
    public CharacterService(CharacterRepo characterRepository, UserService userService) {
        this.characterRepository = characterRepository;
        this.userService = userService;
    }

    @Transactional
    public ModelAndView save(Principal principal,
                     CharacterRequestTo characterRequestTo,
                     ModelAndView modelAndView) {
        Character byName = getCharacter(characterRequestTo.getName());
        if (byName == null) {
            User user = userService.getUser(principal.getName());
            Character newCharacter = MapperRequest.map(characterRequestTo);
            newCharacter.setUser(user);
            characterRepository.save(newCharacter);
            modelAndView.setViewName("redirect:/game");
            return modelAndView;
        } else throw new CreateCharacterException("This name already exists");
    }

    @Transactional
    public ModelAndView load(String name, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/game");
        return modelAndView;
    }

    @Transactional
    public ModelAndView updateCharacterName(String oldName,
                                            String newName,
                                            ModelAndView modelAndView) {
        if (getCharacter(newName) == null) {
            Character character = getCharacter(oldName);
            character.setName(newName);
            characterRepository.save(character);
            modelAndView.setViewName("redirect:/characters");
            return modelAndView;
        } else throw new UpdateCharacterNameException("This name is already taken");
    }

    @Transactional
    public ModelAndView deleteCharacter(String characterName,
                                        ModelAndView modelAndView) {
        characterRepository.delete(getCharacter(characterName));
        modelAndView.setViewName("redirect:/characters");
        return modelAndView;
    }

    @Transactional
    public ModelAndView outputCharacterList(Principal principal,
                                            ModelAndView modelAndView) {
        User user = userService.getUser(principal.getName());
        List<Character> characterList = user.getCharacters();
        modelAndView.addObject("characters", characterList);
        modelAndView.setViewName("characters");
        return modelAndView;
    }

    private Character getCharacter(String name) {
        return characterRepository.findByName(name)
                .findFirst()
                .orElse(null);
    }
}
