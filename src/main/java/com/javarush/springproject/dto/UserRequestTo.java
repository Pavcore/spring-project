package com.javarush.springproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestTo {
    private String login;
    private String password;
}
