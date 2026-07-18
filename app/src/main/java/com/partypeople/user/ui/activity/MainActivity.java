package com.partypeople.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.partypeople.user.R;
import com.partypeople.user.activitys.SplashActivity;
import com.partypeople.user.databinding.ActivityHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Single-activity home. Navigation Component drives the Trending/Popular/
 * Explore feeds and the event-details screen; replaces the legacy
 * BaseActivity + ViewPager setup.
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavHostFragment navHost = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host);
        navController = navHost.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.trendingFragment, R.id.popularFragment, R.id.exploreFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        BottomNavigationView bottomNav = binding.bottomNav;
        NavigationUI.setupWithNavController(bottomNav, navController);
        // Hide the bottom bar on detail screens.
        navController.addOnDestinationChangedListener((controller, destination, args) ->
                bottomNav.setVisibility(
                        appBarConfiguration.getTopLevelDestinations()
                                .contains(destination.getId())
                                ? android.view.View.VISIBLE
                                : android.view.View.GONE));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SplashActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
