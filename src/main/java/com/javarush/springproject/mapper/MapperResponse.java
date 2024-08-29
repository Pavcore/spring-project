package com.javarush.springproject.mapper;

import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.dto.UserResponseTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MapperResponse {
    public static UserResponseTo map(User user) {
        return UserResponseTo.builder()
                .id(user.getId())
                .login(user.getLogin())
                .characterResponseToList(listMapCharacter(user.getCharacters()))
                .build();
    }

    public static User map(UserResponseTo userResponseTo) {
        return User.builder()
                .id(userResponseTo.getId())
                .login(userResponseTo.getLogin())
                .characters(listMapCharacterTo(userResponseTo.getCharacterResponseToList()))
                .build();
    }

    public static Character map(CharacterResponseTo characterResponseTo) {
        return Character.builder()
                .id(characterResponseTo.getId())
                .name(characterResponseTo.getName())
                .characterClass(characterResponseTo.getCharacterClass())
                .user(map(characterResponseTo.getUserResponse()))
                .build();
    }

    public static CharacterResponseTo map(Character character) {
        return CharacterResponseTo.builder()
                .id(character.getId())
                .name(character.getName())
                .characterClass(character.getCharacterClass())
                .userResponse(map(character.getUser()))
                .build();
    }
    
    private static List<CharacterResponseTo> listMapCharacter(List<Character> characters){
        List<CharacterResponseTo> characterResponseToList = new ArrayList<>();
        for (Character character : characters) {
            characterResponseToList.add(map(character));
        }
        return characterResponseToList;
    }

    private static List<Character> listMapCharacterTo(List<CharacterResponseTo> characterResponseToList) {
        List<Character> characters = new ArrayList<>();
        for (CharacterResponseTo characterResponseTo : characterResponseToList) {
            characters.add(map(characterResponseTo));
        }
        return characters;
    }
}
