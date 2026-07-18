package com.partypeople.user.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Objects;

/**
 * User wallet. Balance is stored in cents (minor currency units) as a long —
 * never floating point — so arithmetic is exact.
 */
public class Wallet {

    @DocumentId
    private String id;
    private String userId;
    private long balanceCents;

    public Wallet() {
        // Required by Firestore.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getBalanceCents() {
        return balanceCents;
    }

    public void setBalanceCents(long balanceCents) {
        this.balanceCents = balanceCents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wallet)) return false;
        Wallet wallet = (Wallet) o;
        return balanceCents == wallet.balanceCents
                && Objects.equals(id, wallet.id)
                && Objects.equals(userId, wallet.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, balanceCents);
    }
}
