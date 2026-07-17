package com.partypeople.user.ui.viewmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;

import com.partypeople.user.data.Result;
import com.partypeople.user.data.repository.EventRepository;
import com.partypeople.user.models.Event;
import com.partypeople.user.ui.UiState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class EventDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();

    @Mock
    private EventRepository repository;

    private MutableLiveData<Result<Event>> source;
    private final Observer<UiState<Event>> observer = state -> { };

    @Before
    public void setUp() {
        source = new MutableLiveData<>();
    }

    private SavedStateHandle handleWithId(String id) {
        return new SavedStateHandle(
                Collections.singletonMap(EventDetailsViewModel.ARG_EVENT_ID, id));
    }

    @Test
    public void initialState_isLoading() {
        when(repository.observeEvent("e1")).thenReturn(source);
        EventDetailsViewModel viewModel =
                new EventDetailsViewModel(repository, handleWithId("e1"));
        viewModel.getEvent().observeForever(observer);

        assertEquals(UiState.Status.LOADING, viewModel.getEvent().getValue().getStatus());
    }

    @Test
    public void success_mapsToSuccessState() {
        when(repository.observeEvent("e1")).thenReturn(source);
        EventDetailsViewModel viewModel =
                new EventDetailsViewModel(repository, handleWithId("e1"));
        viewModel.getEvent().observeForever(observer);

        Event event = new Event.Builder().id("e1").name("Party").venue("Club").build();
        source.setValue(Result.success(event));

        assertEquals(UiState.Status.SUCCESS, viewModel.getEvent().getValue().getStatus());
        assertSame(event, viewModel.getEvent().getValue().getData());
    }

    @Test
    public void error_mapsToErrorState() {
        when(repository.observeEvent("e1")).thenReturn(source);
        EventDetailsViewModel viewModel =
                new EventDetailsViewModel(repository, handleWithId("e1"));
        viewModel.getEvent().observeForever(observer);

        source.setValue(Result.error(new RuntimeException("gone")));

        assertEquals(UiState.Status.ERROR, viewModel.getEvent().getValue().getStatus());
    }

    @Test
    public void missingEventId_isErrorImmediately() {
        EventDetailsViewModel viewModel =
                new EventDetailsViewModel(repository, new SavedStateHandle());
        viewModel.getEvent().observeForever(observer);

        assertEquals(UiState.Status.ERROR, viewModel.getEvent().getValue().getStatus());
    }
}
