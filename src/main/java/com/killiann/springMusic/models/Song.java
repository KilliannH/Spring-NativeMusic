package com.killiann.springMusic.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String filename;

    @JsonIgnoreProperties({"songs", "albums"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "songs_artists",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id",
                    referencedColumnName = "id"))
    private Set<Artist> artists = new HashSet<>();

    @JsonIgnoreProperties({"songs", "artists"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "songs_albums",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "album_id",
                    referencedColumnName = "id"))
    private Set<Album> albums = new HashSet<>();

    public Song() {}

    public Song(String title, String filename) {
        this.title = title; this.filename = filename;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", filename='" + filename + '\'' +
                ", artists='" + artists + '\'' +
                ", albums='" + albums + '\'' +
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
