package com.sohba_travel.sohba.Models;

/**
 * Created by M on 12/31/2016.
 */

public class City {
    public City(int Image, String Name) {
        this.Image = Image;
        this.Name = Name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    int Image;
    String Name;
}
