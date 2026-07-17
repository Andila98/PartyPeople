package com.partypeople.user.ui.fragments;

import androidx.annotation.NonNull;

import com.partypeople.user.ui.viewmodel.EventsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/** Most-RSVPed events. */
@AndroidEntryPoint
public class TrendingFragment extends EventsListFragment {

    @Override
    protected void loadFeed(@NonNull EventsViewModel viewModel) {
        viewModel.showTrending();
    }
}
