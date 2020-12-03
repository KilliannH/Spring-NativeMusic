package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.RoleNotFoundException;
import com.killiann.springMusic.models.Role;
import com.killiann.springMusic.repositories.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private final RoleRepository repository;

    // Autowired by constructor...
    RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/roles")
    List<Role> all() {
        return repository.findAll();
    }

    // Single item

    @GetMapping("/roles/{id}")
    Role one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    // Roles would be exclusively in this format : "ROLE_" + rolename in majs ex. ROLE_USER
    // this is because we need to match the UserDetails Object provided by Spring Security.
    @PostMapping("/roles")
    Role newRole(@RequestBody Role newRole) {
        return repository.save(newRole);
    }

    @PutMapping("/roles/{id}")
    Role replaceRole(@RequestBody Role newRole, @PathVariable Long id) {

        return repository.findById(id)
                .map(role -> {
                    role.setRoleName(newRole.getRoleName());
                    return repository.save(role);
                })
                .orElseThrow(() -> new RoleNotFoundException(id));
    }



    @DeleteMapping("/roles/{id}")
    void deleteRole(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
