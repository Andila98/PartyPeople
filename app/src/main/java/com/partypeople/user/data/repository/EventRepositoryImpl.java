package com.partypeople.user.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.partypeople.user.data.Result;
import com.partypeople.user.models.Event;
import com.partypeople.user.models.EventCategory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Firestore-backed {@link EventRepository}.
 *
 * Collection schema: /events/{eventId} with fields matching {@link Event}.
 * Note: observeTrendingEvents and observeEventsByCategory rely on Firestore
 * composite indexes; the console link in the logged error creates them on
 * first use.
 */
@Singleton
public class EventRepositoryImpl implements EventRepository {

    private static final String COLLECTION_EVENTS = "events";
    private static final String FIELD_START_TIME = "startTime";
    private static final String FIELD_RSVP_COUNT = "rsvpCount";
    private static final String FIELD_CATEGORY = "category";

    private final FirebaseFirestore firestore;

    @Inject
    public EventRepositoryImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @NonNull
    @Override
    public LiveData<Result<List<Event>>> observeAllEvents() {
        Query query = firestore.collection(COLLECTION_EVENTS)
                .orderBy(FIELD_START_TIME, Query.Direction.ASCENDING);
        return new QueryLiveData(query);
    }

    @NonNull
    @Override
    public LiveData<Result<List<Event>>> observeTrendingEvents(int limit) {
        Query query = firestore.collection(COLLECTION_EVENTS)
                .orderBy(FIELD_RSVP_COUNT, Query.Direction.DESCENDING)
                .limit(limit);
        return new QueryLiveData(query);
    }

    @NonNull
    @Override
    public LiveData<Result<List<Event>>> observeEventsByCategory(@NonNull EventCategory category) {
        Query query = firestore.collection(COLLECTION_EVENTS)
                .whereEqualTo(FIELD_CATEGORY, category.name())
                .orderBy(FIELD_START_TIME, Query.Direction.ASCENDING);
        return new QueryLiveData(query);
    }

    @NonNull
    @Override
    public LiveData<Result<Event>> observeEvent(@NonNull String eventId) {
        return new DocumentLiveData(firestore.collection(COLLECTION_EVENTS).document(eventId));
    }

    @NonNull
    @Override
    public Task<String> createEvent(@NonNull Event event) {
        DocumentReference ref = firestore.collection(COLLECTION_EVENTS).document();
        return ref.set(event).continueWith(task -> {
            if (!task.isSuccessful() && task.getException() != null) {
                throw task.getException();
            }
            return ref.getId();
        });
    }

    @NonNull
    @Override
    public Task<Void> rsvpToEvent(@NonNull String eventId) {
        return firestore.collection(COLLECTION_EVENTS)
                .document(eventId)
                .update(FIELD_RSVP_COUNT, FieldValue.increment(1));
    }

    /** Real-time query stream; the snapshot listener lives only while observed. */
    private static class QueryLiveData extends LiveData<Result<List<Event>>> {

        private final Query query;
        private ListenerRegistration registration;

        QueryLiveData(Query query) {
            this.query = query;
        }

        @Override
        protected void onActive() {
            registration = query.addSnapshotListener((snapshot, error) -> {
                if (error != null) {
                    Timber.e(error, "Event query failed");
                    setValue(Result.error(error));
                } else if (snapshot != null) {
                    setValue(Result.success(snapshot.toObjects(Event.class)));
                }
            });
        }

        @Override
        protected void onInactive() {
            if (registration != null) {
                registration.remove();
                registration = null;
            }
        }
    }

    /** Real-time single-document stream. */
    private static class DocumentLiveData extends LiveData<Result<Event>> {

        private final DocumentReference document;
        private ListenerRegistration registration;

        DocumentLiveData(DocumentReference document) {
            this.document = document;
        }

        @Override
        protected void onActive() {
            registration = document.addSnapshotListener((snapshot, error) -> {
                if (error != null) {
                    Timber.e(error, "Event %s listener failed", document.getId());
                    setValue(Result.error(error));
                } else if (snapshot != null && snapshot.exists()) {
                    Event event = snapshot.toObject(Event.class);
                    if (event != null) {
                        setValue(Result.success(event));
                    }
                } else {
                    setValue(Result.error(
                            new IllegalStateException("Event not found: " + document.getId())));
                }
            });
        }

        @Override
        protected void onInactive() {
            if (registration != null) {
                registration.remove();
                registration = null;
            }
        }
    }
}
