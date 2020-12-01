package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByRoleName(String roleName);
}
