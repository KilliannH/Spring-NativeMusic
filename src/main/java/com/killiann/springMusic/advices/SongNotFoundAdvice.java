package com.killiann.springMusic.advices;

import com.killiann.springMusic.exceptions.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SongNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String artistNotFoundHandler(SongNotFoundException ex) {
        return ex.getMessage();
    }
}
