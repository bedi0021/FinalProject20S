package com.example.finalproject20s;

public class Song {
    private int id;
    private String artist, songname, lyrics;

    public Song(int id, String artist, String songname,String lyrics){
        this.id=id;
        this.artist=artist;
        this.songname=songname;
        this.lyrics=lyrics;
    }

    public Song(String artist, String songname){
        this.artist=artist;
        this.songname=songname;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongname() {
        return songname;
    }

    public String getLyrics() {
        return lyrics;
    }

    public int getId() { return id; }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setId(int id) { this.id = id; }
}
