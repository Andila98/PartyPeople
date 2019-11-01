package com.partypeople.user.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.partypeople.user.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingEvent extends Fragment {


    public UpcomingEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_event, container, false);
    }

}
