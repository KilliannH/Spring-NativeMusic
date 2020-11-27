package com.killiann.springMusic.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;


    @JsonIgnoreProperties({"artists"})
    @ManyToMany(mappedBy = "artists")
    private Set<Song> songs = new HashSet<>();

    @JsonIgnoreProperties({"artists"})
    @ManyToMany(mappedBy = "artists")
    private Set<Song> albums = new HashSet<>();

    public Artist() {}

    public Artist(String name, String imageUrl) {
        this.name = name; this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", songs='" + songs + '\'' +
                ", albums='" + albums + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
