package com.partypeople.user.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EventCategoryTest {

    @Test
    public void fromName_matchesEnumName_caseInsensitive() {
        assertEquals(EventCategory.NIGHTLIFE, EventCategory.fromName("nightlife"));
        assertEquals(EventCategory.MUSIC, EventCategory.fromName("MUSIC"));
    }

    @Test
    public void fromName_matchesDisplayName() {
        assertEquals(EventCategory.FOOD_AND_DRINK, EventCategory.fromName("Food & Drink"));
    }

    @Test
    public void fromName_trimsWhitespace() {
        assertEquals(EventCategory.FESTIVAL, EventCategory.fromName("  festival "));
    }

    @Test
    public void fromName_unknownOrNull_fallsBackToOther() {
        assertEquals(EventCategory.OTHER, EventCategory.fromName("karaoke"));
        assertEquals(EventCategory.OTHER, EventCategory.fromName(null));
    }
}
