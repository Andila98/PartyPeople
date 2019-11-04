package com.partypeople.user.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.partypeople.user.R;
import com.partypeople.user.adaptors.viewpager.BaseViewpagerAdapter;
import com.partypeople.user.adaptors.viewpager.SplashViewpagerAdapter;
import com.partypeople.user.core.PartyApp;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private TextView splash_appname;
    private TabLayout splash_tablayout;
    private ViewPager splash_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_appname = findViewById(R.id.splash_appname);
        splash_tablayout = findViewById(R.id.splash_tablayout);
        splash_viewpager = findViewById(R.id.splash_viewpager);
        splash_viewpager.setAdapter(new SplashViewpagerAdapter(getSupportFragmentManager(), getfragments(), getTitles()));
        splash_tablayout.setupWithViewPager(splash_viewpager, true);

        if (((PartyApp)getApplication()).usersignedIn()){
            startActivity(new Intent(this,BaseActivity.class));
            finish();
        }

    }

    private List<Fragment> getfragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new SignInFragment());
        fragments.add(new SignUpFragment());
        return fragments;
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("Sign In");
        titles.add("Sign UP");
        return titles;
    }
}
