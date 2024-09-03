package com.javarush.springproject.mapper;

import com.javarush.springproject.dto.CharacterResponseTo;
import com.javarush.springproject.dto.UserResponseTo;
import com.javarush.springproject.entity.Character;
import com.javarush.springproject.entity.User;

public class MapperResponse {
    public static UserResponseTo map(User user) {
        return UserResponseTo.builder()
                .id(user.getId())
                .login(user.getLogin())
                .build();
    }

    public static User map(UserResponseTo userResponseTo) {
        return User.builder()
                .id(userResponseTo.getId())
                .login(userResponseTo.getLogin())
                .build();
    }

    public static Character map(CharacterResponseTo characterResponseTo) {
        return Character.builder()
                .id(characterResponseTo.getId())
                .name(characterResponseTo.getName())
                .characterClass(characterResponseTo.getCharacterClass())
                .gameQuantity(characterResponseTo.getGameQuantity())
                .winQuantity(characterResponseTo.getWinQuantity())
                .user(map(characterResponseTo.getUserResponse()))
                .build();
    }

    public static CharacterResponseTo map(Character character) {
        return CharacterResponseTo.builder()
                .id(character.getId())
                .name(character.getName())
                .characterClass(character.getCharacterClass())
                .gameQuantity(character.getGameQuantity())
                .winQuantity(character.getWinQuantity())
                .userResponse(map(character.getUser()))
                .build();
    }
}
