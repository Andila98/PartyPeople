package com.partypeople.user.models;

import java.util.List;

public class Events {
    private String name,id, venue, date, category, sponsername, sponserid, details,image,ticketprice,rsvp;

    public Events(){

    }

    public Events(String name, String id, String venue, String date, String category, String sponsername, String sponserid, String details, String image, String ticketprice, String rsvp, List<String> category1) {
        this.name = name;
        this.id = id;
        this.venue = venue;
        this.date = date;
        this.category = category;
        this.sponsername = sponsername;
        this.sponserid = sponserid;
        this.details = details;
        this.image = image;
        this.ticketprice = ticketprice;
        this.rsvp = rsvp;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getSponsername() {
        return sponsername;
    }

    public String getSponserid() {
        return sponserid;
    }

    public String getDetails() {
        return details;
    }

    public String getImage() {
        return image;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public String getRsvp() {
        return rsvp;
    }
}
