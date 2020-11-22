package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.AlbumNotFoundException;
import com.killiann.springMusic.models.Album;
import com.killiann.springMusic.repositories.AlbumRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlbumController {

    private final AlbumRepository repository;

    AlbumController(AlbumRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/albums")
    List<Album> all() {
        return repository.findAll();
    }

    @PostMapping("/albums")
    Album newAlbum(@RequestBody Album newAlbum) {
        return repository.save(newAlbum);
    }

    // Single item

    @GetMapping("/albums/{id}")
    Album one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

    @PutMapping("/albums/{id}")
    Album replaceAlbum(@RequestBody Album newAlbum, @PathVariable Long id) {

        return repository.findById(id)
                .map(album -> {
                    album.setTitle(newAlbum.getTitle());
                    album.setImageUrl(newAlbum.getImageUrl());
                    return repository.save(album);
                })
                // not sure about this lines
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

    @DeleteMapping("/albums/{id}")
    void deleteAlbum(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
