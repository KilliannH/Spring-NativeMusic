package com.killiann.springMusic.advices;

import com.killiann.springMusic.exceptions.PlaylistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlaylistNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PlaylistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String artistNotFoundHandler(PlaylistNotFoundException ex) {
        return ex.getMessage();
    }
}
