package com.killiann.springMusic.component;

import com.killiann.springMusic.models.Role;
import com.killiann.springMusic.models.User;
import com.killiann.springMusic.repositories.RoleRepository;
import com.killiann.springMusic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Component
class UserCreator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        User user = new User("user",  passwordEncoder.encode("user"), true);

        Role roleUser = new Role("ROLE_USER");

        roleRepository.save(roleUser);

        HashSet<Role> roles = new HashSet<>();

        roles.add(roleUser);
        user.setRoles(roles);

        userRepository.save(user);
    }
}