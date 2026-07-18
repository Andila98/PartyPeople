package com.partypeople.user.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.partypeople.user.R;
import com.partypeople.user.databinding.ItemEventBinding;
import com.partypeople.user.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Event list adapter. ListAdapter + DiffUtil diffs submitted lists on a
 * background thread and animates only the rows that changed — no
 * notifyDataSetChanged full reloads. Replaces the legacy Trending/Popular/
 * Explore adapters.
 */
public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    /** Row interactions, forwarded to the hosting fragment. */
    public interface Listener {
        void onEventClick(@NonNull Event event);

        void onRsvpClick(@NonNull Event event);

        void onShareClick(@NonNull Event event);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getId() != null && oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final Listener listener;

    public EventAdapter(@NonNull Listener listener) {
        super(DIFF);
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new EventViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        private final DateFormat dateFormat =
                new SimpleDateFormat("EEE, d MMM, h:mm a", Locale.getDefault());
        private final ItemEventBinding binding;
        private Event boundEvent;

        EventViewHolder(@NonNull ItemEventBinding binding, @NonNull Listener listener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.eventCard.setOnClickListener(v -> {
                if (boundEvent != null) listener.onEventClick(boundEvent);
            });
            binding.btnRsvp.setOnClickListener(v -> {
                if (boundEvent != null) listener.onRsvpClick(boundEvent);
            });
            binding.btnShare.setOnClickListener(v -> {
                if (boundEvent != null) listener.onShareClick(boundEvent);
            });
        }

        void bind(@NonNull Event event) {
            boundEvent = event;

            binding.eventName.setText(event.getName());
            binding.eventVenue.setText(event.getVenue());
            binding.eventPrice.setText(event.getFormattedPrice());
            binding.eventDate.setText(event.getStartTime() == null
                    ? ""
                    : dateFormat.format(event.getStartTime()));
            binding.eventRsvpCount.setText(itemView.getContext()
                    .getString(R.string.event_rsvp_count, event.getRsvpCount()));

            Glide.with(binding.eventImage)
                    .load(event.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_error_image)
                    .centerCrop()
                    .into(binding.eventImage);
        }
    }
}
