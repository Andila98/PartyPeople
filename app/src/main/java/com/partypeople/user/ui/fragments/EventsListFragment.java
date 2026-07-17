package com.partypeople.user.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.partypeople.user.R;
import com.partypeople.user.databinding.FragmentEventsListBinding;
import com.partypeople.user.models.Event;
import com.partypeople.user.ui.UiState;
import com.partypeople.user.ui.adapter.EventAdapter;
import com.partypeople.user.ui.viewmodel.EventDetailsViewModel;
import com.partypeople.user.ui.viewmodel.EventsViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Base for all event feeds (Trending/Popular/Explore). Owns the full
 * list lifecycle — loading, error, empty and success states, swipe to
 * refresh, RSVP and share — subclasses only pick the query.
 */
public abstract class EventsListFragment extends Fragment implements EventAdapter.Listener {

    private FragmentEventsListBinding binding;
    private EventsViewModel viewModel;
    private EventAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEventsListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new EventAdapter(this);
        binding.eventsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.eventsRecycler.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);
        loadFeed(viewModel);

        binding.swipeRefresh.setOnRefreshListener(viewModel::retry);
        binding.btnRetry.setOnClickListener(v -> viewModel.retry());

        viewModel.getEvents().observe(getViewLifecycleOwner(), this::render);
    }

    @Override
    public void onDestroyView() {
        binding.eventsRecycler.setAdapter(null);
        binding = null;
        super.onDestroyView();
    }

    /** Called once after the ViewModel exists; pick the feed to display. */
    protected abstract void loadFeed(@NonNull EventsViewModel viewModel);

    private void render(@NonNull UiState<List<Event>> state) {
        boolean loading = state.getStatus() == UiState.Status.LOADING;
        boolean hasRows = adapter.getItemCount() > 0;

        if (!loading) {
            binding.swipeRefresh.setRefreshing(false);
        }
        // Full-screen spinner only on first load; refreshes keep the list up.
        binding.progress.setVisibility(loading && !hasRows ? View.VISIBLE : View.GONE);
        binding.emptyView.setVisibility(
                state.getStatus() == UiState.Status.EMPTY ? View.VISIBLE : View.GONE);
        binding.errorView.setVisibility(
                state.getStatus() == UiState.Status.ERROR && !hasRows ? View.VISIBLE : View.GONE);

        switch (state.getStatus()) {
            case SUCCESS:
                adapter.submitList(state.getData());
                break;
            case EMPTY:
                adapter.submitList(Collections.emptyList());
                break;
            case ERROR:
                if (hasRows) {
                    Snackbar.make(binding.getRoot(), R.string.events_error_generic,
                            Snackbar.LENGTH_LONG).show();
                }
                break;
            case LOADING:
            default:
                break;
        }
    }

    @Override
    public void onEventClick(@NonNull Event event) {
        if (event.getId() == null) {
            return;
        }
        Bundle args = new Bundle();
        args.putString(EventDetailsViewModel.ARG_EVENT_ID, event.getId());
        NavHostFragment.findNavController(this).navigate(R.id.eventDetailsFragment, args);
    }

    @Override
    public void onRsvpClick(@NonNull Event event) {
        if (event.getId() == null) {
            return;
        }
        viewModel.rsvpToEvent(event.getId())
                .addOnSuccessListener(unused -> {
                    if (binding != null) {
                        Snackbar.make(binding.getRoot(), R.string.rsvp_success,
                                Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    if (binding != null) {
                        Snackbar.make(binding.getRoot(), R.string.rsvp_failed,
                                Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onShareClick(@NonNull Event event) {
        Intent share = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.share_event_text, event.getName(), event.getVenue()));
        startActivity(Intent.createChooser(share, getString(R.string.share_event)));
    }
}
