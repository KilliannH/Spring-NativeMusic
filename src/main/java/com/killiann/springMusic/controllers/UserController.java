package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.*;
import com.killiann.springMusic.models.Role;
import com.killiann.springMusic.models.User;
import com.killiann.springMusic.repositories.RoleRepository;
import com.killiann.springMusic.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Aggregate root

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    // create relationships

    @PostMapping("/users/{user_id}/roles/{id}")
    User newUserRole(@PathVariable Long user_id, @PathVariable Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
        return userRepository.findById(user_id).map(user -> {

            user.getRoles().add(role);
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    // remove relationships

    @DeleteMapping("/users/{user_id}/roles/{id}")
    User deleteUserRoles(@PathVariable Long user_id, @PathVariable Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
        return userRepository.findById(user_id).map(user -> {
            user.getRoles().remove(role);
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }
}
