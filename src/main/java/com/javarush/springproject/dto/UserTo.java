package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserTo {
    Long id;
    String login;
    String password;
    List<CharacterTo> characters;
}
