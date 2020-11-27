package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.ArtistNotFoundException;
import com.killiann.springMusic.models.Artist;
import com.killiann.springMusic.repositories.ArtistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {

    private final ArtistRepository repository;

    ArtistController(ArtistRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/artists")
    List<Artist> all() {
        return repository.findAll();
    }

    @PostMapping("/artists")
    Artist newArtist(@RequestBody Artist newArtist) {
        return repository.save(newArtist);
    }

    // Single item

    @GetMapping("/artists/{id}")
    Artist one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(id));
    }

    @PutMapping("/artists/{id}")
    Artist replaceArtist(@RequestBody Artist newArtist, @PathVariable Long id) {

        return repository.findById(id)
                .map(artist -> {
                    artist.setName(newArtist.getName());
                    artist.setImageUrl(newArtist.getImageUrl());
                    return repository.save(artist);
                })
                // not sure about this lines
                .orElseThrow(() -> new ArtistNotFoundException(id));
    }

    @DeleteMapping("/artists/{id}")
    void deleteArtist(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
