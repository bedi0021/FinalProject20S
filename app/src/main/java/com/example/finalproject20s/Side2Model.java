package com.example.finalproject20s;

import java.io.Serializable;

/**
 * this model rerturns the side1 of the model which displays the name of the opponent team aad the url as well
 */
public class Side2Model implements Serializable {

    private String name;

    private String url;

    public Side2Model(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
