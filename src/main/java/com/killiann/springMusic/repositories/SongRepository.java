package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByTitle(String title);

}
