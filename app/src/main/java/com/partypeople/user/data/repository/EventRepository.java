package com.partypeople.user.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.partypeople.user.data.Result;
import com.partypeople.user.models.Event;
import com.partypeople.user.models.EventCategory;

import java.util.List;

/**
 * Data-access contract for events. Observe* methods return real-time
 * streams backed by Firestore snapshot listeners; listeners attach while
 * the LiveData is active and detach automatically when it is not.
 */
public interface EventRepository {

    /** All events, soonest first. */
    @NonNull
    LiveData<Result<List<Event>>> observeAllEvents();

    /** Most-RSVPed events, capped at {@code limit}. */
    @NonNull
    LiveData<Result<List<Event>>> observeTrendingEvents(int limit);

    /** Events in a single category, soonest first. */
    @NonNull
    LiveData<Result<List<Event>>> observeEventsByCategory(@NonNull EventCategory category);

    /** A single event document, updating in real time. */
    @NonNull
    LiveData<Result<Event>> observeEvent(@NonNull String eventId);

    /** Creates the event; the task resolves to the new document id. */
    @NonNull
    Task<String> createEvent(@NonNull Event event);

    /** Atomically increments the event's RSVP counter. */
    @NonNull
    Task<Void> rsvpToEvent(@NonNull String eventId);
}
