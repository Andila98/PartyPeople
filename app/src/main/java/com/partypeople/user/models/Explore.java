package com.partypeople.user.models;

public class Explore {

    private String image, name, venue, ticketprice,date;

    public Explore (){

    }

    public Explore(String image, String name, String venue, String ticketprice, String date) {
        this.image = image;
        this.name = name;
        this.venue = venue;
        this.ticketprice = ticketprice;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
