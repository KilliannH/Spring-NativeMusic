package com.killiann.springMusic.controllers;

import com.killiann.springMusic.exceptions.ArtistNotFoundException;
import com.killiann.springMusic.exceptions.SongNotFoundException;
import com.killiann.springMusic.models.Artist;
import com.killiann.springMusic.models.Song;
import com.killiann.springMusic.repositories.ArtistRepository;
import com.killiann.springMusic.repositories.SongRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SongController {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    SongController(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    // Aggregate root

    @GetMapping("/songs")
    List<Song> all() {
        return songRepository.findAll();
    }

    @PostMapping("/songs")
    Song newSong(@RequestBody Song newSong) {
        return songRepository.save(newSong);
    }

    @PostMapping("/songs/{song_id}/artists/{id}")
    Song newSongArtist(@PathVariable Long song_id, @PathVariable Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
        return songRepository.findById(song_id).map(song -> {

            // as this is a Set and not a List,
            // Set doesn't allow duplicates.
            // Witch is what we want for this kind of manipulation.
            song.getArtists().add(artist);
            return songRepository.save(song);
        }).orElseThrow(() -> new SongNotFoundException(id));
    }


    // Single item

    @GetMapping("/songs/{id}")
    Song one(@PathVariable Long id) {

        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException(id));
    }

    @PutMapping("/songs/{id}")
    Song replaceSong(@RequestBody Song newSong, @PathVariable Long id) {

        return songRepository.findById(id)
                .map(song -> {
                    song.setTitle(newSong.getTitle());
                    song.setFilename(newSong.getFilename());
                    return songRepository.save(song);
                })
                // not sure about this lines
                .orElseThrow(() -> new SongNotFoundException(id));
    }

    @DeleteMapping("/songs/{id}")
    void deleteSong(@PathVariable Long id) {
        songRepository.deleteById(id);
    }

    @PostMapping("/songs/{song_id}/artists/{id}")
    Song deleteSongArtist(@PathVariable Long song_id, @PathVariable Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
        return songRepository.findById(song_id).map(song -> {

            // as this is a Set and not a List,
            // Set doesn't allow duplicates.
            // Witch is what we want for this kind of manipulation.
            song.getArtists().remove(artist);
            return songRepository.save(song);
        }).orElseThrow(() -> new SongNotFoundException(id));
    }
}
