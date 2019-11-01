package com.partypeople.user.adaptors.viewpager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class BaseViewpagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;
    List<String> titles;

    public BaseViewpagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles){
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
