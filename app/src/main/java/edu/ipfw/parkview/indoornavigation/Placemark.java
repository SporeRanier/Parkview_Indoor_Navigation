package edu.ipfw.parkview.indoornavigation;

import java.io.InputStream;

public class Placemark {

    private String name;
    private String description;
    private String imageURL;



    public void buildPlacemarkArray(){

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
