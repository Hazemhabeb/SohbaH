package com.sohba_travel.sohba.Models;

import java.util.HashMap;

/**
 * Created by M on 12/8/2016.
 */

public class Trip {
    String tripId;
    String userId;
    String Description;
    String tripName;
    String tripPlace;
    String tripType;
    String tripPrice;
    String tripRate;

    public String getTripImage() {
        return tripImage;
    }

    public void setTripImage(String tripImage) {
        this.tripImage = tripImage;
    }

    String tripImage;

    public HashMap<String, Timeline> getTimelineHashMap() {
        return timelineHashMap;
    }

    public void setTimelineHashMap(HashMap<String, Timeline> timelineHashMap) {
        this.timelineHashMap = timelineHashMap;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripPlace() {
        return tripPlace;
    }

    public void setTripPlace(String tripPlace) {
        this.tripPlace = tripPlace;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(String tripPrice) {
        this.tripPrice = tripPrice;
    }

    public String getTripRate() {
        return tripRate;
    }

    public void setTripRate(String tripRate) {
        this.tripRate = tripRate;
    }

    public HashMap<String, SingleRating> getRatingHashMap() {
        return RatingHashMap;
    }

    public void setRatingHashMap(HashMap<String, SingleRating> ratingHashMap) {
        RatingHashMap = ratingHashMap;
    }

    HashMap<String, Timeline> timelineHashMap = new HashMap<String, Timeline>();
    HashMap<String, SingleRating> RatingHashMap = new HashMap<String, SingleRating>();

    public Trip() {
    }

    public Trip(String tripId, String userId,
                String Description, String tripName,
                String tripPlace, String tripType,
                String tripPrice, String tripRate,
                String tripImage, HashMap<String, Timeline> timelineHashMap
            , HashMap<String, SingleRating> RatingHashMap) {
        this.tripId = tripId;
        this.userId = userId;
        this.Description = Description;
        this.tripName = tripName;
        this.tripPlace = tripPlace;
        this.tripType = tripType;
        this.tripPrice = tripPrice;
        this.tripRate = tripRate;
        this.tripImage = tripImage;
        this.timelineHashMap = timelineHashMap;
        this.RatingHashMap = RatingHashMap;
    }


}
