package com.partypeople.user.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.partypeople.user.data.repository.EventRepository;
import com.partypeople.user.models.Event;
import com.partypeople.user.ui.UiState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Streams one event in real time. The event id arrives through
 * SavedStateHandle from the navigation argument, so the ViewModel
 * survives process death with its identity intact.
 */
@HiltViewModel
public class EventDetailsViewModel extends ViewModel {

    public static final String ARG_EVENT_ID = "eventId";

    private final EventRepository repository;
    private final String eventId;
    private final MediatorLiveData<UiState<Event>> event = new MediatorLiveData<>();

    @Inject
    public EventDetailsViewModel(EventRepository repository, SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.eventId = savedStateHandle.get(ARG_EVENT_ID);

        event.setValue(UiState.loading());
        if (eventId == null || eventId.isEmpty()) {
            event.setValue(UiState.error(new IllegalArgumentException("Missing event id")));
        } else {
            event.addSource(repository.observeEvent(eventId), result -> {
                if (result.isSuccess() && result.getData() != null) {
                    event.setValue(UiState.success(result.getData()));
                } else {
                    event.setValue(UiState.error(result.getError()));
                }
            });
        }
    }

    @NonNull
    public LiveData<UiState<Event>> getEvent() {
        return event;
    }

    @NonNull
    public Task<Void> rsvp() {
        return repository.rsvpToEvent(eventId);
    }
}
