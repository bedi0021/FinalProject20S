package com.example.finalproject20s;

import java.io.Serializable;

public class CompetitionModel implements Serializable {

    private String name;

    private Integer id;

    private String url;



    public CompetitionModel(String name, Integer id, String url) {
        this.name = name;
        this.id = id;
        this.url = url;
    }

    /**
     * get Name of the competition
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set name of competition
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get Id of the competition
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * set Id of the competition
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * It gets url of the competition
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * it sets url of the competition
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
