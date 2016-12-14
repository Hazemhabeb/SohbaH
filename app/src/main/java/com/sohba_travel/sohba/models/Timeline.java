package com.sohba_travel.sohba.Models;

/**
 * Created by M on 12/8/2016.
 */

public class Timeline {
    public Timeline() {
    }

    public Timeline(String From, String To, String Description) {
        this.From = From;
        this.To = To;
        this.Description = Description;
    }

    String From;
    String To;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    String Description;
}
