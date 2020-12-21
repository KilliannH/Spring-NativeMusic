package com.killiann.springMusic.exceptions;

public class PlaylistNotFoundException extends RuntimeException {

    public PlaylistNotFoundException(Long id) {
        super("Could not find playlist with id: " + id);
    }
}
