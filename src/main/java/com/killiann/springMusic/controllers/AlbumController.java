package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.AlbumNotFoundException;
import com.killiann.springMusic.exceptions.ArtistNotFoundException;
import com.killiann.springMusic.models.Album;
import com.killiann.springMusic.models.Artist;
import com.killiann.springMusic.repositories.AlbumRepository;
import com.killiann.springMusic.repositories.ArtistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    AlbumController(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    // Aggregate root

    @GetMapping("/albums")
    List<Album> all() {
        return albumRepository.findAll();
    }

    @PostMapping("/albums")
    Album newAlbum(@RequestBody Album newAlbum) {
        return albumRepository.save(newAlbum);
    }

    // create relationships

    @PostMapping("/albums/{album_id}/artists/{id}")
    Album newAlbumArtist(@PathVariable Long album_id, @PathVariable Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
        return albumRepository.findById(album_id).map(album -> {

            // as this is a Set and not a List,
            // Set doesn't allow duplicates.
            // Witch is what we want for this kind of manipulation.
            album.getArtists().add(artist);
            return albumRepository.save(album);
        }).orElseThrow(() -> new AlbumNotFoundException(id));
    }
    // Single item

    @GetMapping("/albums/{id}")
    Album one(@PathVariable Long id) {

        return albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

    @PutMapping("/albums/{id}")
    Album replaceAlbum(@RequestBody Album newAlbum, @PathVariable Long id) {

        return albumRepository.findById(id)
                .map(album -> {
                    album.setTitle(newAlbum.getTitle());
                    album.setImageUrl(newAlbum.getImageUrl());
                    return albumRepository.save(album);
                })
                // not sure about this lines
                .orElseThrow(() -> new AlbumNotFoundException(id));
    }

    @DeleteMapping("/albums/{id}")
    void deleteAlbum(@PathVariable Long id) {
        albumRepository.deleteById(id);
    }

    // remove relationships

    @DeleteMapping("/albums/{album_id}/artists/{id}")
    Album deleteAlbumArtist(@PathVariable Long album_id, @PathVariable Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
        return albumRepository.findById(album_id).map(album -> {
            album.getArtists().remove(artist);
            return albumRepository.save(album);
        }).orElseThrow(() -> new AlbumNotFoundException(id));
    }
}
