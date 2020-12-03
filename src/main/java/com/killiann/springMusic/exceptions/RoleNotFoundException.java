package com.killiann.springMusic.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Could not find role with id: " + id);
    }
}
