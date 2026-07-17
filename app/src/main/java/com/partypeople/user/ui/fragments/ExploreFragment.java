package com.partypeople.user.ui.fragments;

import androidx.annotation.NonNull;

import com.partypeople.user.ui.viewmodel.EventsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Browse all events. Category filter chips can plug into
 * {@link EventsViewModel#showCategory} when the design lands.
 */
@AndroidEntryPoint
public class ExploreFragment extends EventsListFragment {

    @Override
    protected void loadFeed(@NonNull EventsViewModel viewModel) {
        viewModel.showAll();
    }
}
