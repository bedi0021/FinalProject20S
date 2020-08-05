package com.example.finalproject20s;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchModel implements Serializable {

   // @SerializedName("title")
    private String title;

  //  @SerializedName("embed")
    private String embed;

   // @SerializedName("url")
    private String url;

   // @SerializedName("thumbnail")
    private String thumbnail;

    private String date;

    private long id;

    private Side1Model side1;

    private Side2Model side2;

    private CompetitionModel competition;

    private ArrayList<VideoModel> videos;


    public MatchModel(String title, String embed, String url, String thumbnail, String date, Side1Model side1, Side2Model side2, CompetitionModel competition, ArrayList videos) {
        this.title = title;
        this.embed = embed;
        this.url = url;
        this.thumbnail = thumbnail;
        this.date=date;
        this.side1=side1;
        this.side2=side2;
        this.competition=competition;
        this.videos=videos;
    }

    public MatchModel() {
    }

    public MatchModel(long id, String title, String side1, String url, String image, String date) {
        this.title=title;
        this.date=date;
        this.thumbnail=image;
        this.url=url;
        //this.getSide1().setName(side1);
    }

    /**
     * it saves the video in ArrayList and returns the ArrayList
     * @return
     */
    public ArrayList<VideoModel> getVideos() {
        return videos;
    }

    /**
     * it set videos to ArrayList
     * @param videos
     */
    public void setVideos(ArrayList<VideoModel> videos) {
        this.videos = videos;
    }

    /**
     * it returns the Competition Model
     * @return
     */
    public CompetitionModel getCompetitionModel() {
        return competition;
    }

    /**
     * it sets the competition model
     * @param competitionModel
     */
    public void setCompetitionModel(CompetitionModel competitionModel) {
        this.competition = competitionModel;
    }

    /**
     * it returns the side2 of the model
     * @return
     */
    public Side2Model getSide2() {
        return side2;
    }

    /**
     * it sets the side2 of the model
     * @param side2
     */
    public void setSide2(Side2Model side2) {
        this.side2 = side2;
    }

    /**
     * it returns the side1 of the model
     * @return
     */
    public Side1Model getSide1() {
        return side1;
    }

    /**
     * it sets the side1 of the model
     * @param side1
     */
    public void setSide1(Side1Model side1) {
        this.side1 = side1;
    }

    /**
     * it return date the match was played
     */

    public String getDate() {
        return date;
    }

    /**`
     * it sets the date
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }
    /*8
    it returns the title of the match
     */
    public String getTitle() {
        return title;
    }

    /**
     * it sets the title of the match
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * it returns the embedeed view using style attribute
     * @return
     */
    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    /**
     * it returns the url of the match
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * it sets the Url of the match
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * it returns th image of hte match to be viewed
     * @return
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * it sets the image
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * it returns the id of the match being played
     * @return
     */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
