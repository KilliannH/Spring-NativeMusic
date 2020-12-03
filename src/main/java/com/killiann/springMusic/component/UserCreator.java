package com.killiann.springMusic.component;

import com.killiann.springMusic.models.Role;
import com.killiann.springMusic.models.User;
import com.killiann.springMusic.repositories.RoleRepository;
import com.killiann.springMusic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        User admin = new User("admin", passwordEncoder.encode("admin"), true);

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        HashSet<Role> roles = new HashSet<>();

        roles.add(roleUser);
        user.setRoles(roles);

        userRepository.save(user);

        roles.remove(roleUser);
        roles.add(roleAdmin);

        userRepository.save(admin);

        // etc
    }
}
