package com.killiann.springMusic.exceptions;

public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException(Long id) {
        super("Could not find album with id: " + id);
    }
}
