package com.partypeople.user.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Objects;

/**
 * A ticket for an event. Status is a type-safe enum instead of the legacy
 * string flags (ticket_sold / ticket_reserve).
 */
public class Ticket {

    public enum Status {
        AVAILABLE,
        RESERVED,
        SOLD,
        CANCELLED
    }

    @DocumentId
    private String id;
    private String eventId;
    private String ownerId;
    private String code;
    private long priceCents;
    private Status status = Status.AVAILABLE;

    public Ticket() {
        // Required by Firestore.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(long priceCents) {
        this.priceCents = priceCents;
    }

    public Status getStatus() {
        return status == null ? Status.AVAILABLE : status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return priceCents == ticket.priceCents
                && Objects.equals(id, ticket.id)
                && Objects.equals(eventId, ticket.eventId)
                && Objects.equals(ownerId, ticket.ownerId)
                && Objects.equals(code, ticket.code)
                && getStatus() == ticket.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, ownerId, code, priceCents, getStatus());
    }
}
