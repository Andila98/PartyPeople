package com.partypeople.user.models;

public class Trending  {
    private String image, name, details, venue, date, timefrom, timeto, ticketprice, artist1, artist2, artist3;

    public Trending(){

    }

    public Trending(String image, String name, String details, String venue, String date, String timefrom, String timeto, String ticketprice, String artist1, String artist2, String artist3) {
        this.image = image;
        this.name = name;
        this.details = details;
        this.venue = venue;
        this.date = date;
        this.timefrom = timefrom;
        this.timeto = timeto;
        this.ticketprice = ticketprice;
        this.artist1 = artist1;
        this.artist2 = artist2;
        this.artist3 = artist3;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimefrom() {
        return timefrom;
    }

    public void setTimefrom(String timefrom) {
        this.timefrom = timefrom;
    }

    public String getTimeto() {
        return timeto;
    }

    public void setTimeto(String timeto) {
        this.timeto = timeto;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(String ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getArtist1() {
        return artist1;
    }

    public void setArtist1(String artist1) {
        this.artist1 = artist1;
    }

    public String getArtist2() {
        return artist2;
    }

    public void setArtist2(String artist2) {
        this.artist2 = artist2;
    }

    public String getArtist3() {
        return artist3;
    }

    public void setArtist3(String artist3) {
        this.artist3 = artist3;
    }
}
