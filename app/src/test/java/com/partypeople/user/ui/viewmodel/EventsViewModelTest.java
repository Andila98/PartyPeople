package com.partypeople.user.ui.viewmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.partypeople.user.data.Result;
import com.partypeople.user.data.repository.EventRepository;
import com.partypeople.user.models.Event;
import com.partypeople.user.models.EventCategory;
import com.partypeople.user.ui.UiState;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EventsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();

    @Mock
    private EventRepository repository;

    private MutableLiveData<Result<List<Event>>> trendingSource;
    private final Observer<UiState<List<Event>>> observer = state -> { };

    @Before
    public void setUp() {
        trendingSource = new MutableLiveData<>();
        when(repository.observeTrendingEvents(EventsViewModel.TRENDING_LIMIT))
                .thenReturn(trendingSource);
    }

    private Event sampleEvent() {
        return new Event.Builder()
                .id("e1")
                .name("Rooftop Party")
                .venue("Skyline Lounge")
                .category(EventCategory.NIGHTLIFE)
                .build();
    }

    @Test
    public void initialState_isLoading_andObservesTrending() {
        EventsViewModel viewModel = new EventsViewModel(repository);
        viewModel.getEvents().observeForever(observer);

        assertEquals(UiState.Status.LOADING, viewModel.getEvents().getValue().getStatus());
        verify(repository).observeTrendingEvents(EventsViewModel.TRENDING_LIMIT);
    }

    @Test
    public void repositorySuccess_mapsToSuccessState() {
        EventsViewModel viewModel = new EventsViewModel(repository);
        viewModel.getEvents().observeForever(observer);

        List<Event> data = Collections.singletonList(sampleEvent());
        trendingSource.setValue(Result.success(data));

        assertEquals(UiState.Status.SUCCESS, viewModel.getEvents().getValue().getStatus());
        assertSame(data, viewModel.getEvents().getValue().getData());
    }

    @Test
    public void repositoryEmptyList_mapsToEmptyState() {
        EventsViewModel viewModel = new EventsViewModel(repository);
        viewModel.getEvents().observeForever(observer);

        trendingSource.setValue(Result.success(Collections.emptyList()));

        assertEquals(UiState.Status.EMPTY, viewModel.getEvents().getValue().getStatus());
    }

    @Test
    public void repositoryError_mapsToErrorState() {
        EventsViewModel viewModel = new EventsViewModel(repository);
        viewModel.getEvents().observeForever(observer);

        Throwable boom = new RuntimeException("firestore down");
        trendingSource.setValue(Result.error(boom));

        assertEquals(UiState.Status.ERROR, viewModel.getEvents().getValue().getStatus());
        assertSame(boom, viewModel.getEvents().getValue().getError());
    }

    @Test
    public void showCategory_switchesSource_andResetsToLoading() {
        MutableLiveData<Result<List<Event>>> categorySource = new MutableLiveData<>();
        when(repository.observeEventsByCategory(EventCategory.MUSIC)).thenReturn(categorySource);

        EventsViewModel viewModel = new EventsViewModel(repository);
        viewModel.getEvents().observeForever(observer);
        trendingSource.setValue(Result.success(Collections.singletonList(sampleEvent())));

        viewModel.showCategory(EventCategory.MUSIC);

        assertEquals(UiState.Status.LOADING, viewModel.getEvents().getValue().getStatus());
        verify(repository).observeEventsByCategory(EventCategory.MUSIC);

        // Updates from the abandoned trending source must be ignored now.
        trendingSource.setValue(Result.error(new RuntimeException("stale")));
        assertEquals(UiState.Status.LOADING, viewModel.getEvents().getValue().getStatus());
    }

    @Test
    public void retry_reRunsCurrentQuery() {
        EventsViewModel viewModel = new EventsViewModel(repository);
        viewModel.getEvents().observeForever(observer);
        trendingSource.setValue(Result.error(new RuntimeException("boom")));

        // The real repository builds a fresh stream per call; mirror that so
        // retry doesn't re-attach the LiveData still holding the old error.
        when(repository.observeTrendingEvents(EventsViewModel.TRENDING_LIMIT))
                .thenReturn(new MutableLiveData<>());
        viewModel.retry();

        assertEquals(UiState.Status.LOADING, viewModel.getEvents().getValue().getStatus());
        verify(repository, org.mockito.Mockito.times(2))
                .observeTrendingEvents(EventsViewModel.TRENDING_LIMIT);
    }
}
