package com.javarush.springproject.mapper;

import com.javarush.springproject.dto.CharacterRequestTo;
import com.javarush.springproject.dto.UserRequestTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;

public class MapperRequest {

    public static UserRequestTo map(User user) {
        return UserRequestTo.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }

    public static User map(UserRequestTo userRequestTo) {
        return User.builder()
                .login(userRequestTo.getLogin())
                .password(userRequestTo.getPassword())
                .build();
    }

    public static Character map(CharacterRequestTo characterRequestTo) {
        return Character.builder()
                .name(characterRequestTo.getName())
                .characterClass(characterRequestTo.getCharacterClass())
                .build();
    }

    public static CharacterRequestTo map(Character character) {
        return CharacterRequestTo.builder()
                .name(character.getName())
                .characterClass(character.getCharacterClass())
                .build();
    }
}
