package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Album;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long> {

    List<Album> findByTitle(String title);

}
