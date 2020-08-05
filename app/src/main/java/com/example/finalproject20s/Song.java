package com.example.finalproject20s;

/**
 * This application helps you to find lyrics for the popular songs around he world
 * Along with doing a google search for the desired song
 *
 * @author Harmanpreet Bedi
 * @version 1.0
 * @since 2020-05-08
 *
 */

/**
 * This is a model class which uses four private fields in order to be used for the arraylist and databses that has generated to store
 * id, song name, lyrics and artist name.
 * @since 1.0
 */
public class Song {
    private long id;
    private String artist, songname, lyrics;

    public Song(long id, String artist, String songname,String lyrics){
        this.id=id;
        this.artist=artist;
        this.songname=songname;
        this.lyrics=lyrics;
    }

    public Song(String artist, String songname){
        this.artist=artist;
        this.songname=songname;
    }

    public Song(String artist, String songname,String lyrics){
        this.artist=artist;
        this.songname=songname;
        this.lyrics=lyrics;
    }

    /**
     * <p>This methods gets the artist name</p>
     * @return Artist's name has been returned
     * @since 1.0
     */
    public String getArtist() {
        return artist;
    }

    /**
     * <p>This methods gets the song name</p>
     * @return Song's name has been returned
     * @since 1.0
     */
    public String getSongname() {
        return songname;
    }

    /**
     * <p>This methods gets the lyrics</p>
     * @return Lyrics's name has been returned
     * @since 1.0
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     * <p>This methods gets the artist's Id </p>
     * @return Id(Long) name has been returned
     * @since 1.0
     */
    public long getId() { return id; }

    /**
     * <p>This sets the value for artist</p>
     * @param artist Passing the value for artist name saves it for future use.
     * @since 1.0
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * <p>This sets the value for song name</p>
     * @param songname Passing the value for song name saves it for future use.
     * @since 1.0
     */
    public void setSongname(String songname) {
        this.songname = songname;
    }

    /**
     * <p>This sets the value for lyrics of a particular song</p>
     * @param lyrics Passing the value for lyrics saves it for future use.
     * @since 1.0
     */
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     * <p>This sets the value for artist's ID</p>
     * @param id Passing the value for artist's saves it for future use.
     * @since 1.0
     */
    public void setId(long id) { this.id = id; }
}
