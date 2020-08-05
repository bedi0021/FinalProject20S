package com.example.finalproject20s;

/**
 * The city details about individual city
 */
public class City {

    /**
     * ID of the city
     */
    private long id;
    /**
     * City Name
     */
    private String city;
    /**
     * Country Name
     */
    private String country;
    /**
     * Region of the city
     */
    private String region;
    /**
     * Currency of the city
     */
    private String currency;
    /**
     * Longitude of the city
     */
    private String longitude;
    /**
     * Latitude of the city
     */
    private String latitude;

    public City(String city, String country, String region, String currency, String longitude, String latitude) {
        this.city = city;
        this.country = country;
        this.region = region;
        this.currency = currency;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
