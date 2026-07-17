package com.partypeople.user.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * App user profile. Auth credentials live in Firebase Auth only — this
 * class intentionally has no password field (the legacy model stored one).
 */
public class User {

    @DocumentId
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String location;
    private Double latitude;
    private Double longitude;
    private List<String> friendIds = new ArrayList<>();
    private String gender;
    private Integer age;
    private long eventPoints;
    private String walletId;

    public User() {
        // Required by Firestore.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public List<String> getFriendIds() {
        return friendIds == null ? Collections.emptyList() : friendIds;
    }

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public long getEventPoints() {
        return eventPoints;
    }

    public void setEventPoints(long eventPoints) {
        this.eventPoints = eventPoints;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return eventPoints == user.eventPoints
                && Objects.equals(id, user.id)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(username, user.username)
                && Objects.equals(email, user.email)
                && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(location, user.location)
                && Objects.equals(latitude, user.latitude)
                && Objects.equals(longitude, user.longitude)
                && Objects.equals(getFriendIds(), user.getFriendIds())
                && Objects.equals(gender, user.gender)
                && Objects.equals(age, user.age)
                && Objects.equals(walletId, user.walletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, email, phoneNumber,
                location, latitude, longitude, getFriendIds(), gender, age,
                eventPoints, walletId);
    }
}
