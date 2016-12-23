package com.sohba_travel.sohba.Models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by M on 12/8/2016.
 */

public class Trip implements Serializable {
    public String tripId;
    public String userId;
    public String tripDescription;
    public String tripName;
    public String tripPlace;
    public String tripType;
    public String tripPrice;
    public String tripRate;
    public String tripImage;

    public HashMap<String, Timeline> timelineHashMap = new HashMap<String, Timeline>();
    public HashMap<String, SingleRating> RatingHashMap = new HashMap<String, SingleRating>();



    //to know if user vertifed or not
    public String userVertifed;
    public Trip() {
    }

    public Trip(String tripId, String userId,String userVertifed,
                String tripDescription, String tripName,
                String tripPlace, String tripType,
                String tripPrice, String tripRate,
                String tripImage, HashMap<String, Timeline> timelineHashMap
            , HashMap<String, SingleRating> RatingHashMap) {
        this.tripId = tripId;
        this.userId = userId;
        this.tripDescription = tripDescription;
        this.tripName = tripName;
        this.tripPlace = tripPlace;
        this.tripType = tripType;
        this.tripPrice = tripPrice;
        this.tripRate = tripRate;
        this.tripImage = tripImage;
        this.timelineHashMap = timelineHashMap;
        this.RatingHashMap = RatingHashMap;
        this.userVertifed=userVertifed;
    }


}
