package com.javarush.springproject.service;

import com.javarush.springproject.dbo.CharacterRepo;
import com.javarush.springproject.dto.CharacterRequestTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.CreateCharacterException;
import com.javarush.springproject.exception.DeleteCharacterException;
import com.javarush.springproject.exception.LoadCharacterException;
import com.javarush.springproject.exception.UpdateCharacterNameException;
import com.javarush.springproject.mapper.MapperRequest;
import com.javarush.springproject.mapper.MapperResponse;
import jakarta.servlet.http.HttpSession;
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
                             ModelAndView modelAndView,
                             HttpSession httpSession) {
        Character byName = getCharacter(characterRequestTo.getName());
        if (byName == null) {
            User user = userService.getUser(principal.getName());
            Character newCharacter = MapperRequest.map(characterRequestTo);
            newCharacter.setUser(user);
            long gameQuantity = newCharacter.getGameQuantity() + 1L;
            newCharacter.setGameQuantity(gameQuantity);
            characterRepository.save(newCharacter);
            httpSession.setAttribute("character", MapperResponse.map(newCharacter));
            modelAndView.setViewName("redirect:/index");
            return modelAndView;
        } else throw new CreateCharacterException("This name already exists");
    }

    @Transactional
    public ModelAndView load(Principal principal,
                             String name,
                             ModelAndView modelAndView,
                             HttpSession httpSession) {
        User user = userService.getUser(principal.getName());
        Character character = getCharacter(name);
        if (user.getCharacters().contains(character)) {
            modelAndView.setViewName("redirect:/index");
            long gameQuantity = character.getGameQuantity() + 1L;
            character.setGameQuantity(gameQuantity);
            characterRepository.save(character);
            httpSession.setAttribute("character", MapperResponse.map(character));
            return modelAndView;
        } else throw new LoadCharacterException("You do not own this character");
    }

    @Transactional
    public ModelAndView updateCharacterName(String oldName,
                                            String newName,
                                            ModelAndView modelAndView) {
        if (getCharacter(newName) == null && newName != null && !newName.isEmpty()) {
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
        if (characterName != null && !characterName.isEmpty()) {
            characterRepository.delete(getCharacter(characterName));
            modelAndView.setViewName("redirect:/characters");
            return modelAndView;
        } else throw new DeleteCharacterException("I don't see a character");
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

    @Transactional
    public Character getCharacter(String name) {
        return characterRepository.findByName(name)
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void saveGameStatistic(Character character){
        characterRepository.save(character);
    }
}
