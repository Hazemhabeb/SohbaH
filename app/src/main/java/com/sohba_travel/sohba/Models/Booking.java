package com.sohba_travel.sohba.Models;

/**
 * Created by M on 12/16/2016.
 */

public class Booking {
    public String userBooking, BookingId, tripUserId, Timestamp, tripId;
    public Boolean Accept, Paid;

    public Booking() {

    }

    public Booking(String userBooking,
                   String BookingId,
                   String tripUserId,
                   String Timestamp,
                   Boolean Accept, Boolean Paid, String tripId) {
        this.userBooking = userBooking;
        this.BookingId = BookingId;
        this.tripUserId = tripUserId;
        this.Timestamp = Timestamp;
        this.Accept = Accept;
        this.Paid = Paid;
        this.tripId = tripId;

    }
}
