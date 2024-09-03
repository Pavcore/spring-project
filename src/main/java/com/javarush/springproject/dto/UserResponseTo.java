package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseTo {
    private Long id;
    private String login;
}
