package com.killiann.springMusic.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    // by default Spring will create a new column named "image_url"
    private String imageUrl;

    @JsonIgnoreProperties({"artists", "album"})
    @OneToMany(mappedBy = "album")
    private Set<Song> songs = new HashSet<>();

    @JsonIgnoreProperties({"songs", "albums"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "albums_artists",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id",
                    referencedColumnName = "id"))
    private Set<Artist> artists = new HashSet<>();

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
    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }
}
