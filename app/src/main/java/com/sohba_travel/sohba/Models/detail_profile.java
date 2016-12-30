package com.sohba_travel.sohba.Models;

/**
 * Created by hazem on 12/29/2016.
 */

public class detail_profile {
    private String word;
    private int icon;

    public detail_profile(int icon, String word) {
        this.icon = icon;
        this.word = word;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
