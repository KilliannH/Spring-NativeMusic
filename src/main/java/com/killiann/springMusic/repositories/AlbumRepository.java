package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findByTitle(String title);

}
