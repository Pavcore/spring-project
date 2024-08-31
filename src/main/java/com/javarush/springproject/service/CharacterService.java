package com.javarush.springproject.service;

import com.javarush.springproject.dbo.CharacterRepo;
import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.dto.CharacterRequestTo;
import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import com.javarush.springproject.exception.DeleteCharacterException;
import com.javarush.springproject.mapper.MapperRequest;
import com.javarush.springproject.mapper.MapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepo characterRepository;

    private final UserRepo userRepository;

    @Autowired
    public CharacterService(CharacterRepo characterRepository, UserRepo userRepository) {
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean save(Principal principal, CharacterRequestTo characterRequestTo) {
        User user = getUserFromSession(principal);
        Character byName = getCharacter(characterRequestTo.getName());
        if (byName == null) {
            Character newCharacter = MapperRequest.map(characterRequestTo);
            newCharacter.setUser(user);
            characterRepository.save(newCharacter);
            return true;
        } else return false;
    }

    @Transactional
    public void delete(Principal principal, CharacterRequestTo characterRequestTo) {
        User user = getUserFromSession(principal);
        Character character = getCharacter(characterRequestTo.getName());
        if (user.getCharacters().contains(character)) {
            characterRepository.delete(character);
        } else throw new DeleteCharacterException("This character not your");
    }

    @Transactional
    public CharacterResponseTo updateCharacterName(String string) {
        Character character = getCharacter(string);
        character.setName(string);
        characterRepository.save(character);
        return MapperResponse.map(character);
    }

    @Transactional
    public boolean haveCharacterCreated(Principal principal, String name) {
        Character character = getCharacter(name);
        User user = getUserFromSession(principal);
        List<Character> characterList = user.getCharacters();
        return characterList.contains(character);
    }

    private Character getCharacter(String name) {
        return characterRepository.findByName(name)
                .findFirst()
                .orElse(null);
    }

    private User getUserFromSession(Principal principal) {
        return userRepository.findByLogin(principal.getName())
                .findFirst()
                .orElse(null);
    }
}
