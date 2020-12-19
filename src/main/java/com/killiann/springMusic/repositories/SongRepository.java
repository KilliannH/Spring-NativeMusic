package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Album;
import com.killiann.springMusic.models.Artist;
import com.killiann.springMusic.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByTitle(String title);
    Set<Song> findAllByArtistsIn(Set<Artist> artists);
    List<Song> findAllByAlbum(Album album);
}
