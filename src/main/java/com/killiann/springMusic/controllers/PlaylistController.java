package com.killiann.springMusic.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiann.springMusic.exceptions.AlbumNotFoundException;
import com.killiann.springMusic.exceptions.ArtistNotFoundException;
import com.killiann.springMusic.exceptions.PlaylistNotFoundException;
import com.killiann.springMusic.exceptions.SongNotFoundException;
import com.killiann.springMusic.models.Album;
import com.killiann.springMusic.models.Artist;
import com.killiann.springMusic.models.Playlist;
import com.killiann.springMusic.models.Song;
import com.killiann.springMusic.repositories.PlaylistRepository;
import com.killiann.springMusic.repositories.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    PlaylistController(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    // Aggregate root

    @GetMapping("/playlists/limit")
    Page<Playlist> limit(@RequestParam Integer start, @RequestParam Integer end) {
        return playlistRepository.findAll(
                PageRequest.of(start, end, Sort.by(Sort.Direction.ASC, "name")));
    }

    @PostMapping("/playlists/bySongAndName")
    Set<Playlist> bySongAndName(@RequestBody String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // must be a length of 1.
        Set<Song> songs = new HashSet<>();

        JsonNode jsonNode = objectMapper.readTree(jsonString).get("name");

        String name = jsonNode.asText();

        JsonNode arrNode = objectMapper.readTree(jsonString).get("songId");
        if (arrNode.isArray()) {
            for (final JsonNode objNode : arrNode) {
                Long songId = objNode.asLong();
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new SongNotFoundException(songId));
                songs.add(song);
            }
        }
        if(arrNode.size() > 1) {
            throw new RuntimeException("Size of songIds must be less or equal to 1.");
        }
        return playlistRepository.findFirstBySongsInAndName(songs, name);
    }

    @GetMapping("/playlists")
    List<Playlist> all() {
        return playlistRepository.findAll();
    }

    @PostMapping("/playlists")
    Playlist newPlaylist(@RequestBody Playlist newPlaylist) {
        return playlistRepository.save(newPlaylist);
    }

    @PostMapping("/playlists/{playlist_id}/songs/{id}")
    Playlist newPlaylistSong(@PathVariable Long playlist_id, @PathVariable Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        return playlistRepository.findById(playlist_id).map(playlist -> {
            playlist.getSongs().add(song);
            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new PlaylistNotFoundException(id));
    }

    // Single item

    @GetMapping("/playlists/{id}")
    Playlist one(@PathVariable Long id) {

        return playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException(id));
    }

    @PutMapping("/playlists/{id}")
    Playlist replacePlaylist(@RequestBody Playlist newPlaylist, @PathVariable Long id) {

        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlist.setName(newPlaylist.getName());
                    return playlistRepository.save(playlist);
                })
                // not sure about this lines
                .orElseThrow(() -> new PlaylistNotFoundException(id));
    }

    @DeleteMapping("/playlists/{id}")
    void deletePlaylist(@PathVariable Long id) {
        playlistRepository.deleteById(id);
    }

    // remove relationships

    @DeleteMapping("/playlists/{playlist_id}/songs/{id}")
    Playlist deletePlaylistSong(@PathVariable Long playlist_id, @PathVariable Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new SongNotFoundException(id));
        return playlistRepository.findById(playlist_id).map(playlist -> {
            playlist.getSongs().remove(song);
            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new PlaylistNotFoundException(id));
    }
}
