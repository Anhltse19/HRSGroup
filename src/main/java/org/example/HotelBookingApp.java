package org.example;

import io.javalin.Javalin;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HotelBookingApp {

    private static List<Hotel> hotels = new ArrayList<>();
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("/hotel/search", ctx -> {
            String destination = ctx.queryParam("destination");
            List<Hotel> matchingHotels = searchHotels(destination);
            ctx.json(matchingHotels);
        });

        app.post("/hotel/create", ctx -> {
            Hotel newBooking = gson.fromJson(ctx.body(), Hotel.class);
            newBooking.setId(UUID.randomUUID().toString());
            createBooking(newBooking);
            ctx.status(201);
        });

        app.get("/hotel/details/{bookingId}", ctx -> {
            String bookingId = ctx.pathParam("bookingId");
            Hotel booking = getBookingDetails(bookingId);
            if (booking != null) {
                ctx.json(booking);
            } else {
                ctx.status(404).result("Booking not found");
            }
        });

        app.put("/hotel/edit/{bookingId}", ctx -> {
            String bookingId = ctx.pathParam("bookingId");
            Hotel existingBooking = getBookingDetails(bookingId);

            if (existingBooking != null) {
                Hotel updatedBooking = gson.fromJson(ctx.body(), Hotel.class);
                existingBooking.updateBooking(updatedBooking);
                ctx.status(200);
            } else {
                ctx.status(404).result("Booking not found");
            }
        });

        app.delete("/hotel/delete/{bookingId}", ctx -> {
            String bookingId = ctx.pathParam("bookingId");
            boolean removed = hotels.removeIf(hotel -> hotel.getId().equals(bookingId));
            if (removed) {
                ctx.status(204);
            } else {
                ctx.status(404).result("Booking not found");
            }
        });
    }

    private static List<Hotel> searchHotels(String destination) {
        List<Hotel> matchingHotels = new ArrayList<>();
        for (Hotel hotel : hotels) {
            if (hotel.matchesDestination(destination)) {
                matchingHotels.add(hotel);
            }
        }
        return matchingHotels;
    }

    private static void createBooking(Hotel newBooking) {
        hotels.add(newBooking);
    }

    private static Hotel getBookingDetails(String bookingId) {
        for (Hotel hotel : hotels) {
            if (hotel.getId().equals(bookingId)) {
                return hotel;
            }
        }
        return null;
    }
}
