package com.javarush.springproject.service;

import com.javarush.springproject.dbo.CharacterRepo;
import com.javarush.springproject.dbo.UserRepo;
import com.javarush.springproject.dto.CharacterClass;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean save(HttpServletRequest req, HttpSession session) {
        User user = getUserFromSession(session);
        String classOfCharacter = req.getParameter("characterClass");
        String nameOfCharacter = req.getParameter("characterName");
        Character byName = characterRepository.findByName(nameOfCharacter)
                .findFirst()
                .orElse(null);
        if (byName == null) {
            Character character = Character.builder()
                    .name(nameOfCharacter)
                    .characterClass(CharacterClass.getClass(classOfCharacter))
                    .user(user)
                    .build();
            characterRepository.save(character);
            return true;
        } else return false;
    }

    @Transactional
    public boolean haveCharacterCreated(HttpServletRequest req, HttpSession session) {
        String createCharacter = req.getParameter("createCharacter");
        Character character = characterRepository.findByName(createCharacter)
                .findFirst()
                .orElse(null);
        User user = getUserFromSession(session);
        List<Character> characterList = user.getCharacters();
        return characterList.contains(character);
    }

    private User getUserFromSession(HttpSession session) {
        String login = session.getAttribute("login").toString();
        return userRepository.findByLogin(login)
                .findFirst()
                .orElse(null);
    }
}
