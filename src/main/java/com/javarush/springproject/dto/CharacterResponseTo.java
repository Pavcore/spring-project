package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterResponseTo {

    private Long id;
    private String name;
    private CharacterClass characterClass;
    private UserResponseTo userResponse;

}
