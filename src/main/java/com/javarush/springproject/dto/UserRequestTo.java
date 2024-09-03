package com.javarush.springproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class UserRequestTo {
    @Length(min = 4, max = 32)
    @NotBlank
    @NotNull
    private String login;
    @Length(min = 8, max = 64)
    @NotBlank
    @NotNull
    private String password;
}
