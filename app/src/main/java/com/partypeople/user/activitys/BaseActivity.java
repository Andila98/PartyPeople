package com.partypeople.user.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.partypeople.user.R;
import com.partypeople.user.adaptors.viewpager.BaseViewpagerAdapter;
import com.partypeople.user.ui.fragments.ExploreFragment;
import com.partypeople.user.ui.fragments.PopularFragment;
import com.partypeople.user.ui.fragments.TrendingFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/** Home screen: Trending / Popular / Explore feeds in tabs. */
@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.base_tablayout);
        ViewPager viewPager = findViewById(R.id.base_viewpager);
        viewPager.setAdapter(new BaseViewpagerAdapter(
                getSupportFragmentManager(), getFragments(), getTitles()));
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TrendingFragment());
        fragments.add(new PopularFragment());
        fragments.add(new ExploreFragment());
        return fragments;
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("Trending");
        titles.add("Popular");
        titles.add("Explore");
        return titles;
    }
}
