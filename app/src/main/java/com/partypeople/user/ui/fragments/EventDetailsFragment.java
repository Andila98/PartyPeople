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

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.partypeople.user.R;
import com.partypeople.user.databinding.FragmentEventDetailsBinding;
import com.partypeople.user.models.Event;
import com.partypeople.user.ui.UiState;
import com.partypeople.user.ui.viewmodel.EventDetailsViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

/** Real-time detail view of a single event. */
@AndroidEntryPoint
public class EventDetailsFragment extends Fragment {

    private final DateFormat dateFormat =
            new SimpleDateFormat("EEE, d MMM, h:mm a", Locale.getDefault());
    private FragmentEventDetailsBinding binding;
    private EventDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(EventDetailsViewModel.class);
        viewModel.getEvent().observe(getViewLifecycleOwner(), this::render);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void render(@NonNull UiState<Event> state) {
        binding.progress.setVisibility(
                state.getStatus() == UiState.Status.LOADING ? View.VISIBLE : View.GONE);
        binding.errorText.setVisibility(
                state.getStatus() == UiState.Status.ERROR ? View.VISIBLE : View.GONE);
        binding.contentScroll.setVisibility(
                state.getStatus() == UiState.Status.SUCCESS ? View.VISIBLE : View.GONE);

        Event event = state.getData();
        if (state.getStatus() != UiState.Status.SUCCESS || event == null) {
            return;
        }

        binding.eventName.setText(event.getName());
        binding.eventVenue.setText(event.getVenue());
        binding.eventPrice.setText(event.getFormattedPrice());
        binding.eventDescription.setText(event.getDescription());
        binding.eventRsvpCount.setText(
                getString(R.string.event_rsvp_count, event.getRsvpCount()));
        binding.eventCategoryDate.setText(String.format("%s · %s",
                event.getCategory().getDisplayName(),
                event.getStartTime() == null ? "" : dateFormat.format(event.getStartTime())));

        boolean hasArtists = !event.getArtists().isEmpty();
        binding.artistsLabel.setVisibility(hasArtists ? View.VISIBLE : View.GONE);
        binding.eventArtists.setVisibility(hasArtists ? View.VISIBLE : View.GONE);
        if (hasArtists) {
            binding.eventArtists.setText(String.join(", ", event.getArtists()));
        }

        boolean hasSponsor = event.getSponsorName() != null && !event.getSponsorName().isEmpty();
        binding.eventSponsor.setVisibility(hasSponsor ? View.VISIBLE : View.GONE);
        if (hasSponsor) {
            binding.eventSponsor.setText(
                    getString(R.string.label_hosted_by, event.getSponsorName()));
        }

        Glide.with(binding.eventImage)
                .load(event.getImageUrl())
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_error_image)
                .centerCrop()
                .into(binding.eventImage);

        binding.btnRsvp.setOnClickListener(v -> viewModel.rsvp()
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
                }));

        binding.btnShare.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT,
                            getString(R.string.share_event_text,
                                    event.getName(), event.getVenue()));
            startActivity(Intent.createChooser(share, getString(R.string.share_event)));
        });
    }
}
