package com.javarush.springproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterRequestTo {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
    private CharacterClass characterClass;

}
