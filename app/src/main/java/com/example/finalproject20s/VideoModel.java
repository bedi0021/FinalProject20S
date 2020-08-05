package com.example.finalproject20s;

import java.io.Serializable;

/**
 * it returns the video of the wholw match
 */
public class VideoModel implements Serializable {

    private String title;

    private  String embed;

    public VideoModel(String title, String embed) {
        this.title = title;
        this.embed = embed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }
}
