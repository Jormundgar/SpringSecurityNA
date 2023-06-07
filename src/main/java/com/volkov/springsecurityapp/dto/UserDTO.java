package com.volkov.springsecurityapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserDTO {
    @NotEmpty(message = "Username shouldn`t be empty")
    private String username;
    @Min(value = 1900, message = "Year of birth should be greater than 1900")
    private int yearOfBirth;
    private String password;
}
