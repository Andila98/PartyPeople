package com.partypeople.user.activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.partypeople.user.R;
import com.partypeople.user.adaptors.Recycler.ExploreAdapter;
import com.partypeople.user.adaptors.Recycler.PopularAdapter;
import com.partypeople.user.adaptors.Recycler.TrendingAdapter;
import com.partypeople.user.adaptors.viewpager.BaseViewpagerAdapter;
import com.partypeople.user.fragments.ExploreFragment;
import com.partypeople.user.fragments.PopularFragment;
import com.partypeople.user.fragments.TrendingFragment;
import com.partypeople.user.models.Popular;
import com.partypeople.user.models.Trending;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private TabLayout base_tablayout;
    private ViewPager base_viewpager;
    private List<Trending> eventList = new ArrayList<>();
    private List<Popular>popularList =new ArrayList<>();
    private RecyclerView recyclerView;
    private PopularAdapter PAdapter;
    private TrendingAdapter TAdapter;
    private ExploreAdapter EAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView =findViewById(R.id.recycler_view);

        base_tablayout = findViewById(R.id.base_tablayout);
        base_viewpager = findViewById(R.id.base_viewpager);
        base_viewpager.setAdapter(new BaseViewpagerAdapter(getSupportFragmentManager(), getfragments(), getTitles()));
        base_tablayout.setupWithViewPager(base_viewpager, true);


        PAdapter= new PopularAdapter(popularList);
        TAdapter = new TrendingAdapter(this,eventList);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setAdapter(PAdapter);
        recyclerView.setAdapter(TAdapter);
        recyclerView.setAdapter(EAdapter);

        preparetrendData();

        try {
            Glide.with(this).load(R.drawable.aw2k65).into((ImageView) findViewById(R.id.event_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,SplashActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private List<Fragment> getfragments() {
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

    private void preparetrendData() {


        Trending trend = new Trending("", "Action & Adventure", "2015", "Mad Max: Fury Road", "Inside Out", "Inside Out", "Inside Out", "Inside Out", "", "","" );
        eventList.add(trend);

        trend = new Trending("", "Animation, Kids & Family", "2015", "Inside Out", "Inside Out", "Inside Out", "Inside Out", "Inside Out", "Inside Out", "","");
        eventList.add(trend);

        TAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
