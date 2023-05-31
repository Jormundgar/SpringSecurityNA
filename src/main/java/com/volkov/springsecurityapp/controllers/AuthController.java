package com.volkov.springsecurityapp.controllers;

import com.volkov.springsecurityapp.models.User;
import com.volkov.springsecurityapp.services.RegistrationService;
import com.volkov.springsecurityapp.util.UsersValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final UsersValidator usersValidator;
    private final RegistrationService registrationService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        usersValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        registrationService.register(user);
        return "redirect:/auth/login";
    }
}
