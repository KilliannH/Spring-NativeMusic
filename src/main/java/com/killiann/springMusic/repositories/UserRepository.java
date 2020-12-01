package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);
}
