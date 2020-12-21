package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Playlist;
import com.killiann.springMusic.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByName(String name);

    // this actually expect an array w. length = 1.
    Set<Playlist> findFirstBySongsInAndName(Set<Song> songs, String name);
}
