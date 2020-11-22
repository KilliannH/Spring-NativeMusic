package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Song;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> findByTitle(String title);

}
