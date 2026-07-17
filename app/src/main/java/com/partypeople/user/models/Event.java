package com.partypeople.user.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Unified event model (replaces the legacy Events/Trending/Popular/Explore
 * quadruplicate). Deserialized by Firestore via the no-arg constructor and
 * setters; app code should construct instances through {@link Builder} so
 * validation always runs.
 */
public class Event {

    @DocumentId
    private String id;
    private String name;
    private String venue;
    private String description;
    private String imageUrl;
    private EventCategory category = EventCategory.OTHER;
    private String sponsorId;
    private String sponsorName;
    private Date startTime;
    private Date endTime;
    @ServerTimestamp
    private Date createdAt;
    private List<String> artists = new ArrayList<>();
    private long ticketPriceCents;
    private long rsvpCount;

    public Event() {
        // Required by Firestore.
    }

    private Event(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.venue = builder.venue;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.category = builder.category;
        this.sponsorId = builder.sponsorId;
        this.sponsorName = builder.sponsorName;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.artists = builder.artists;
        this.ticketPriceCents = builder.ticketPriceCents;
        this.rsvpCount = builder.rsvpCount;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public EventCategory getCategory() {
        return category == null ? EventCategory.OTHER : category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public List<String> getArtists() {
        return artists == null ? Collections.emptyList() : artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public long getTicketPriceCents() {
        return ticketPriceCents;
    }

    public void setTicketPriceCents(long ticketPriceCents) {
        this.ticketPriceCents = ticketPriceCents;
    }

    public long getRsvpCount() {
        return rsvpCount;
    }

    public void setRsvpCount(long rsvpCount) {
        this.rsvpCount = rsvpCount;
    }

    @Exclude
    public boolean isFree() {
        return ticketPriceCents == 0;
    }

    /** Price as a display string, e.g. "1,500.00", or "Free". */
    @Exclude
    @NonNull
    public String getFormattedPrice() {
        if (isFree()) {
            return "Free";
        }
        return String.format(Locale.getDefault(), "%,.2f", ticketPriceCents / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return ticketPriceCents == event.ticketPriceCents
                && rsvpCount == event.rsvpCount
                && Objects.equals(id, event.id)
                && Objects.equals(name, event.name)
                && Objects.equals(venue, event.venue)
                && Objects.equals(description, event.description)
                && Objects.equals(imageUrl, event.imageUrl)
                && getCategory() == event.getCategory()
                && Objects.equals(sponsorId, event.sponsorId)
                && Objects.equals(sponsorName, event.sponsorName)
                && Objects.equals(startTime, event.startTime)
                && Objects.equals(endTime, event.endTime)
                && Objects.equals(getArtists(), event.getArtists());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, venue, description, imageUrl, getCategory(),
                sponsorId, sponsorName, startTime, endTime, getArtists(),
                ticketPriceCents, rsvpCount);
    }

    @Override
    @NonNull
    public String toString() {
        return "Event{id='" + id + "', name='" + name + "'}";
    }

    public static class Builder {
        private String id;
        private String name;
        private String venue;
        private String description;
        private String imageUrl;
        private EventCategory category = EventCategory.OTHER;
        private String sponsorId;
        private String sponsorName;
        private Date startTime;
        private Date endTime;
        private List<String> artists = new ArrayList<>();
        private long ticketPriceCents;
        private long rsvpCount;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder venue(String venue) {
            this.venue = venue;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder category(@Nullable EventCategory category) {
            this.category = category == null ? EventCategory.OTHER : category;
            return this;
        }

        public Builder sponsor(String sponsorId, String sponsorName) {
            this.sponsorId = sponsorId;
            this.sponsorName = sponsorName;
            return this;
        }

        public Builder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder artists(@Nullable List<String> artists) {
            this.artists = artists == null ? new ArrayList<>() : new ArrayList<>(artists);
            return this;
        }

        public Builder ticketPriceCents(long ticketPriceCents) {
            this.ticketPriceCents = ticketPriceCents;
            return this;
        }

        public Builder rsvpCount(long rsvpCount) {
            this.rsvpCount = rsvpCount;
            return this;
        }

        @NonNull
        public Event build() {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalStateException("Event name is required");
            }
            if (venue == null || venue.trim().isEmpty()) {
                throw new IllegalStateException("Event venue is required");
            }
            if (ticketPriceCents < 0) {
                throw new IllegalStateException("Ticket price cannot be negative");
            }
            if (startTime != null && endTime != null && endTime.before(startTime)) {
                throw new IllegalStateException("Event cannot end before it starts");
            }
            return new Event(this);
        }
    }
}
