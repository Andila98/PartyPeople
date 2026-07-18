package com.partypeople.user.ui.fragments;

import androidx.annotation.NonNull;

import com.partypeople.user.ui.viewmodel.EventsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/** All events, soonest first. */
@AndroidEntryPoint
public class PopularFragment extends EventsListFragment {

    @Override
    protected void loadFeed(@NonNull EventsViewModel viewModel) {
        viewModel.showAll();
    }
}
