package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
