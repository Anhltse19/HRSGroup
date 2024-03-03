package org.example;

import java.util.List;

public class Hotel {

    private String id;
    private String name;
    private String destination;
    private List<String> bookingDates;

    // getters, setters, constructors

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getBookingDates() {
        return bookingDates;
    }

    public void setBookingDates(List<String> bookingDates) {
        this.bookingDates = bookingDates;
    }

    public boolean matchesDestination(String destination) {
        return this.destination.equalsIgnoreCase(destination);
    }

    public void updateBooking(Hotel updatedBooking) {
        this.name = updatedBooking.name;
        this.destination = updatedBooking.destination;
        this.bookingDates = updatedBooking.bookingDates;
    }
}
