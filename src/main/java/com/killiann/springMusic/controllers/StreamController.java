package com.killiann.springMusic.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ResourceBundle;

@Controller
public class StreamController {

    org.slf4j.Logger logger = LoggerFactory.getLogger(StreamController.class);

    StreamController() { }

    ResourceBundle rb = ResourceBundle.getBundle("config");
    private String path = rb.getString("song.base.dir");

    @GetMapping("/stream/{filename}")
    public ResponseEntity<StreamingResponseBody> stream(@PathVariable String filename, HttpServletResponse response) throws IOException {
        path += filename;
        final File file = new File(path);
        logger.info(path);

        if (file.exists()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            StreamingResponseBody stream = out -> {
                out.write(fileContent);
            };
            return new ResponseEntity<>(stream, HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
