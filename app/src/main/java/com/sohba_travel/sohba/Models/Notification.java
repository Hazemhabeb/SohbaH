package com.sohba_travel.sohba.Models;

/**
 * Created by M on 12/16/2016.
 */

public class Notification {
    public String BookingId, NotificationId, tripUserId, BookedUserId,tripId;

    public Notification() {
    }

    public Notification(String BookingId, String NotificationId, String tripUserId, String BookedUserId,String tripId) {
        this.BookingId = BookingId;
        this.NotificationId = NotificationId;
        this.tripUserId = tripUserId;
        this.BookedUserId = BookedUserId;
        this.tripId=tripId;
    }
}
