package com.example.agregadorinvestimentos.service;

import com.example.agregadorinvestimentos.controller.CreateUserDto;
import com.example.agregadorinvestimentos.controller.UpdateUserDto;
import com.example.agregadorinvestimentos.entity.User;
import com.example.agregadorinvestimentos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public UUID createUser(CreateUserDto createUserDto) {
        // DTO >> ENTITY
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserID();
    }

    public Optional<User> getUserById(String userId) {
       return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
       return userRepository.findAll();
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {

        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if(userEntity.isPresent()) {
            var user = userEntity.get();

            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }
}
