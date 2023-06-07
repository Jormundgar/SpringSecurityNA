package com.volkov.springsecurityapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {
    @NotEmpty(message = "Username shouldn`t be empty")
    private String username;
    private String password;
}
