package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByTitle(String title);
    List<Song> findAllByArtistsId(Set<Long> artistIds);
    List<Song> findAllByAlbumId(Long albumId);
}
