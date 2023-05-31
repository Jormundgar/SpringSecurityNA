package com.volkov.springsecurityapp.util;

import com.volkov.springsecurityapp.models.User;
import com.volkov.springsecurityapp.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsersValidator implements Validator {

    private final UsersService usersService;

    @Override
    public boolean supports(Class<?> sClass) {
        return User.class.equals(sClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var user = (User) target;
        if(usersService.findOneByName(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "This user already exist");
        }
    }
}
