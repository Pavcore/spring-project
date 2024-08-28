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
import java.util.stream.Stream;

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
        Character character = Character.builder()
                .name(nameOfCharacter)
                .characterClass(CharacterClass.getClass(classOfCharacter))
                .user(user)
                .build();
        Character byName = findByName(nameOfCharacter);
        if (byName == null) {
            characterRepository.save(character);
            return true;
        } else return false;
    }

    @Transactional
    public boolean haveCharacterCreated(HttpServletRequest req, HttpSession session) {
        Character character = findByName(req.getParameter("createCharacter"));
        User user = getUserFromSession(session);
        List<Character> characterList = user.getCharacters();
        return characterList.contains(character);
    }

    private Character findByName(String name) {
        Character build = Character.builder()
                .name(name)
                .build();
        Stream<Character> characterStream = characterRepository.findByName(build.getName());
        return characterStream.findFirst().orElse(null);
    }

    private User getUserFromSession(HttpSession session) {
        String login = session.getAttribute("login").toString();
        User build = User.builder()
                .login(login)
                .build();
        return userRepository.findByLogin(build.getLogin())
                .findFirst()
                .orElse(null);
    }
}
