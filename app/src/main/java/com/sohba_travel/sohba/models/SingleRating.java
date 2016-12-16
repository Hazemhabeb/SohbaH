package com.sohba_travel.sohba.Models;

import java.io.Serializable;

/**
 * Created by M on 12/8/2016.
 */

public class SingleRating implements Serializable {
    public SingleRating() {
    }

    public SingleRating(String Value, String userId, String Time) {
        this.Value = Value;
        this.userId = userId;
        this.Time = Time;
    }


    String Value;
    String userId;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    String Time;
}
