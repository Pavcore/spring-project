package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterRequestTo {

    private String name;
    private CharacterClass characterClass;

}
