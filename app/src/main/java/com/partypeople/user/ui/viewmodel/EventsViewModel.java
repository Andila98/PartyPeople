package com.partypeople.user.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.partypeople.user.data.Result;
import com.partypeople.user.data.repository.EventRepository;
import com.partypeople.user.models.Event;
import com.partypeople.user.models.EventCategory;
import com.partypeople.user.ui.UiState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Feeds event lists to the UI as a single {@link UiState} stream. Survives
 * rotation; the underlying Firestore listener detaches whenever the screen
 * stops observing and reattaches on return.
 */
@HiltViewModel
public class EventsViewModel extends ViewModel {

    static final int TRENDING_LIMIT = 20;

    private final EventRepository repository;
    private final MediatorLiveData<UiState<List<Event>>> events = new MediatorLiveData<>();
    private LiveData<Result<List<Event>>> currentSource;
    private Runnable lastQuery;

    @Inject
    public EventsViewModel(EventRepository repository) {
        this.repository = repository;
        showTrending();
    }

    @NonNull
    public LiveData<UiState<List<Event>>> getEvents() {
        return events;
    }

    public void showTrending() {
        lastQuery = this::showTrending;
        setSource(repository.observeTrendingEvents(TRENDING_LIMIT));
    }

    public void showAll() {
        lastQuery = this::showAll;
        setSource(repository.observeAllEvents());
    }

    public void showCategory(@NonNull EventCategory category) {
        lastQuery = () -> showCategory(category);
        setSource(repository.observeEventsByCategory(category));
    }

    /** Re-runs whatever query is currently on screen (e.g. after an error). */
    public void retry() {
        if (lastQuery != null) {
            lastQuery.run();
        }
    }

    @NonNull
    public Task<Void> rsvpToEvent(@NonNull String eventId) {
        return repository.rsvpToEvent(eventId);
    }

    private void setSource(@NonNull LiveData<Result<List<Event>>> source) {
        if (currentSource != null) {
            events.removeSource(currentSource);
        }
        currentSource = source;
        events.setValue(UiState.loading());
        events.addSource(source, result -> {
            if (!result.isSuccess()) {
                events.setValue(UiState.error(result.getError()));
            } else if (result.getData() == null || result.getData().isEmpty()) {
                events.setValue(UiState.empty());
            } else {
                events.setValue(UiState.success(result.getData()));
            }
        });
    }
}
