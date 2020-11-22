package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.SongNotFoundException;
import com.killiann.springMusic.models.Song;
import com.killiann.springMusic.repositories.SongRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    private final SongRepository repository;

    SongController(SongRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/songs")
    List<Song> all() {
        return repository.findAll();
    }

    @PostMapping("/songs")
    Song newSong(@RequestBody Song newSong) {
        return repository.save(newSong);
    }

    // Single item

    @GetMapping("/songs/{id}")
    Song one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new SongNotFoundException(id));
    }

    @PutMapping("/songs/{id}")
    Song replaceSong(@RequestBody Song newSong, @PathVariable Long id) {

        return repository.findById(id)
                .map(song -> {
                    song.setTitle(newSong.getTitle());
                    song.setFilename(newSong.getFilename());
                    return repository.save(song);
                })
                // not sure about this lines
                .orElseThrow(() -> new SongNotFoundException(id));
    }

    @DeleteMapping("/songs/{id}")
    void deleteSong(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
