package com.killiann.springMusic.exceptions;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(Long id) {
        super("Could not find song with id: " + id);
    }
}
