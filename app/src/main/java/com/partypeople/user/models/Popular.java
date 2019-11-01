package com.partypeople.user.models;

public class Popular {
    private String image, name, details, venue, date, timefrom, timeto, ticketprice, artist1, artist2, artist3;

    public Popular(){

    }

    public Popular(String image, String name, String details, String venue, String date, String timefrom, String timeto, String ticketprice, String artist1, String artist2, String artist3) {
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

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTimefrom() {
        return timefrom;
    }

    public String getTimeto() {
        return timeto;
    }

    public String getTicketprice() {
        return ticketprice;
    }

    public String getArtist1() {
        return artist1;
    }

    public String getArtist2() {
        return artist2;
    }

    public String getArtist3() {
        return artist3;
    }
}
