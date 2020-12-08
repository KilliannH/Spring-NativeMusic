package com.killiann.springMusic.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiann.springMusic.exceptions.AlbumNotFoundException;
import com.killiann.springMusic.exceptions.ArtistNotFoundException;
import com.killiann.springMusic.exceptions.SongNotFoundException;
import com.killiann.springMusic.models.Album;
import com.killiann.springMusic.models.Artist;
import com.killiann.springMusic.models.Song;
import com.killiann.springMusic.repositories.AlbumRepository;
import com.killiann.springMusic.repositories.ArtistRepository;
import com.killiann.springMusic.repositories.SongRepository;
import com.killiann.springMusic.util.DownloadUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    private final boolean isWindows = System.getProperty("os.name").contains("Windows");

    SongController(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    // Aggregate root

    @GetMapping("/songs")
    List<Song> all() {
        return songRepository.findAll();
    }

    @PostMapping("/songs")
    Song newSong(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // serialize String to Json as ytUrl is not part of Song model.
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String ytUrl = jsonNode.get("ytUrl").textValue();
        Song newSong = objectMapper.readValue(jsonNode.get("song").toString(), Song.class);

        // all data has been retrieved now download the song
        if(!isWindows) {
            DownloadUtil downloadUtil = new DownloadUtil(newSong.getFilename(), ytUrl);
            try {
                int process = downloadUtil.runYtDownload();
                if(process == 0) {
                    return songRepository.save(newSong);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // and then if all goes well, save the newly created song
        return songRepository.save(newSong);
    }

    // create relationships

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

    @PostMapping("/songs/{song_id}/albums/{id}")
    Song newSongAlbum(@PathVariable Long song_id, @PathVariable Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
        return songRepository.findById(song_id).map(song -> {

            // as this is a Set and not a List,
            // Set doesn't allow duplicates.
            // Witch is what we want for this kind of manipulation.
            song.getAlbums().add(album);
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

    // remove relationships

    @DeleteMapping("/songs/{song_id}/artists/{id}")
    Song deleteSongArtist(@PathVariable Long song_id, @PathVariable Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
        return songRepository.findById(song_id).map(song -> {
            song.getArtists().remove(artist);
            return songRepository.save(song);
        }).orElseThrow(() -> new SongNotFoundException(id));
    }

    @DeleteMapping("/songs/{song_id}/albums/{id}")
    Song deleteSongAlbum(@PathVariable Long song_id, @PathVariable Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
        return songRepository.findById(song_id).map(song -> {
            song.getAlbums().remove(album);
            return songRepository.save(song);
        }).orElseThrow(() -> new SongNotFoundException(id));
    }
}
