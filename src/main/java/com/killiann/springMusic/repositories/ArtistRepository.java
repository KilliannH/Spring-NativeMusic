package com.killiann.springMusic.repositories;

import com.killiann.springMusic.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByName(String name);
}
