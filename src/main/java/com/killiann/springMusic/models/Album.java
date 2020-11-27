package com.killiann.springMusic.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // by default Spring will create a new column named as "image_url"
    private String imageUrl;

    @JsonIgnoreProperties({"albums"})
    @ManyToMany(mappedBy = "albums")
    private Set<Song> songs = new HashSet<>();

    @JsonIgnoreProperties({"albums"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "album_artist",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id",
                    referencedColumnName = "id"))
    private final Set<Artist> artists = new HashSet<>();

    public Album() {}

    public Album(String title, String imageUrl) {
        this.title = title; this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", songs='" + songs + '\'' +
                ", artists='" + artists + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Artist> getArtists() {
        return artists;
    }
}
