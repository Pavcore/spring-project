package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponseTo {
    private Long id;
    private String login;
    private List<CharacterResponseTo> characterResponseToList;
}
