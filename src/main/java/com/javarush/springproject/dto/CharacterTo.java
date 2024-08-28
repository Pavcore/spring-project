package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterTo {
    Long id;
    String name;
    CharacterClass characterClass;
    UserTo userTo;
}
