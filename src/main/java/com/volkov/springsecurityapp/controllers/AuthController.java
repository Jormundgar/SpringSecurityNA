package com.volkov.springsecurityapp.controllers;

import com.volkov.springsecurityapp.dto.AuthenticationDTO;
import com.volkov.springsecurityapp.dto.UserDTO;
import com.volkov.springsecurityapp.models.User;
import com.volkov.springsecurityapp.security.JWTUtil;
import com.volkov.springsecurityapp.services.RegistrationService;
import com.volkov.springsecurityapp.util.UsersValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final UsersValidator usersValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login";
//    }

//    @GetMapping("/registration")
//    public String registration(@ModelAttribute("user") User user) {
//        return "auth/registration";
//    }

    @PostMapping("/registration")
    public Map<String, String> doRegistration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        var user = convertToUser(userDTO);
        usersValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            //TODO Exception Handler
            return Map.of("message", "Error");
        }
        registrationService.register(user);
        var token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt_token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        var authInputToken = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException exception) {
            return Map.of("message","Incorrect credentials");
        }
        var token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt_token", token);
    }

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
