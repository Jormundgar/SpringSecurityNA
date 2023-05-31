package com.volkov.springsecurityapp.services;

import com.volkov.springsecurityapp.models.User;
import com.volkov.springsecurityapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsersService {

    private final UserRepository userRepository;

    public Optional<User> findOneByName(String name) {
        return userRepository.findByUsername(name).stream().findAny();
    }

}
