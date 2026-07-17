package com.partypeople.user.activitys;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.partypeople.user.R;
import com.partypeople.user.adaptors.viewpager.BaseViewpagerAdapter;
import com.partypeople.user.adaptors.viewpager.EventViewpagerAdapter;
import com.partypeople.user.fragments.PastEvents;
import com.partypeople.user.fragments.UpcomingEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    private TabLayout event_tablayout;
    private ViewPager event_viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        event_tablayout = findViewById(R.id.event_tablayout);
        event_viewpager = findViewById(R.id.event_viewpager);
        event_viewpager.setAdapter(new EventViewpagerAdapter(getSupportFragmentManager(), getfragments(), getTitles()));
        event_tablayout.setupWithViewPager(event_viewpager, true);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private List<Fragment> getfragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new UpcomingEvent());
        fragments.add(new PastEvents());
        return fragments;
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("UpcomingEvents");
        titles.add("PastEvents");
        return titles;
    }




}
