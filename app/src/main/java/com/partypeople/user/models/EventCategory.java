package com.partypeople.user.models;

import androidx.annotation.NonNull;

/**
 * Type-safe event categories. Firestore serializes enum values by name
 * (e.g. "NIGHTLIFE"), so renaming a constant is a schema migration.
 */
public enum EventCategory {
    MUSIC("Music"),
    NIGHTLIFE("Nightlife"),
    FESTIVAL("Festival"),
    CONCERT("Concert"),
    CULTURE("Culture"),
    SPORTS("Sports"),
    FOOD_AND_DRINK("Food & Drink"),
    OTHER("Other");

    private final String displayName;

    EventCategory(String displayName) {
        this.displayName = displayName;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Lenient lookup for values coming from user input or legacy data.
     * Falls back to {@link #OTHER} instead of throwing.
     */
    @NonNull
    public static EventCategory fromName(String name) {
        if (name == null) {
            return OTHER;
        }
        for (EventCategory category : values()) {
            if (category.name().equalsIgnoreCase(name.trim())
                    || category.displayName.equalsIgnoreCase(name.trim())) {
                return category;
            }
        }
        return OTHER;
    }
}
