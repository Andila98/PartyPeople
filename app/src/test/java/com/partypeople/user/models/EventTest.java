package com.partypeople.user.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

public class EventTest {

    private Event.Builder validBuilder() {
        return new Event.Builder()
                .id("event-1")
                .name("Sunset Beach Party")
                .venue("Mombasa Beach")
                .category(EventCategory.NIGHTLIFE)
                .ticketPriceCents(150_000)
                .artists(Arrays.asList("DJ A", "DJ B"));
    }

    @Test
    public void build_withValidData_succeeds() {
        Event event = validBuilder().build();
        assertEquals("Sunset Beach Party", event.getName());
        assertEquals(EventCategory.NIGHTLIFE, event.getCategory());
        assertEquals(2, event.getArtists().size());
    }

    @Test
    public void build_withoutName_throws() {
        assertThrows(IllegalStateException.class,
                () -> validBuilder().name("  ").build());
    }

    @Test
    public void build_withoutVenue_throws() {
        assertThrows(IllegalStateException.class,
                () -> validBuilder().venue(null).build());
    }

    @Test
    public void build_withNegativePrice_throws() {
        assertThrows(IllegalStateException.class,
                () -> validBuilder().ticketPriceCents(-1).build());
    }

    @Test
    public void build_withEndBeforeStart_throws() {
        assertThrows(IllegalStateException.class,
                () -> validBuilder()
                        .startTime(new Date(2_000L))
                        .endTime(new Date(1_000L))
                        .build());
    }

    @Test
    public void isFree_zeroPrice_true() {
        assertTrue(validBuilder().ticketPriceCents(0).build().isFree());
        assertEquals("Free", validBuilder().ticketPriceCents(0).build().getFormattedPrice());
    }

    @Test
    public void equals_sameContent_equal() {
        assertEquals(validBuilder().build(), validBuilder().build());
        assertEquals(validBuilder().build().hashCode(), validBuilder().build().hashCode());
    }

    @Test
    public void equals_differentContent_notEqual() {
        assertNotEquals(validBuilder().build(), validBuilder().name("Other Party").build());
    }

    @Test
    public void category_nullFromFirestore_fallsBackToOther() {
        Event event = new Event();
        event.setCategory(null);
        assertEquals(EventCategory.OTHER, event.getCategory());
    }
}
