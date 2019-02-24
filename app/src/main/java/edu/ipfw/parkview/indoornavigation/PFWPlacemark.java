package edu.ipfw.parkview.indoornavigation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.InputStream;

public class PFWPlacemark {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageURL")
    private String imageURL;
    private boolean photosphere;
    private String photospherelink;

    public void buildPlacemarkArray(){

    }

    public boolean isPhotosphere() {
        return photosphere;
    }

    public void setPhotosphere(boolean photosphere) {
        this.photosphere = photosphere;
    }

    public String getPhotospherelink() {
        return photospherelink;
    }

    public void setPhotospherelink(String photospherelink) {
        this.photospherelink = photospherelink;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
