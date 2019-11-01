package com.partypeople.user.models;

import java.util.List;

public class User {
    private String first_name;
    private String last_name;
    private String username;
    private String user_id;
    private String email;
    private String phone_number;
    private String password;
    private String location;
    private String latitude;
    private String longitude;
    private List<String> friends;
    private String gender;
    private String age;
    private String event_points;
    private String wallet_id;


    public User(){

    }

    public User(String first_name, String last_name, String username, String user_id, String email, String phone_number, String password, String location, String latitude, String longitude, List<String> friends, String gender, String age, String event_points, String wallet_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.user_id = user_id;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.friends = friends;
        this.gender = gender;
        this.age = age;
        this.event_points = event_points;
        this.wallet_id = wallet_id;
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public List<String> getFriends() {
        return friends;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getEvent_points() {
        return event_points;
    }

    public String getWallet_id() {
        return wallet_id;
    }


}

