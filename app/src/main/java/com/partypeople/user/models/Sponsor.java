package com.partypeople.user.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Objects;

public class Sponsor {

    @DocumentId
    private String id;
    private String name;

    public Sponsor() {
        // Required by Firestore.
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sponsor)) return false;
        Sponsor sponsor = (Sponsor) o;
        return Objects.equals(id, sponsor.id) && Objects.equals(name, sponsor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
