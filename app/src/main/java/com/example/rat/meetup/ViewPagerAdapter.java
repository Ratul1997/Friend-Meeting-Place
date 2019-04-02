package com.example.rat.meetup;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> frangmentListTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return frangmentListTitle.get(position);
    }

    @Override
    public int getCount() {
        return frangmentListTitle.size();
    }

    public void AddFragment(Fragment fragment, String Title){
        fragmentList.add(fragment);
        frangmentListTitle.add(Title);
    }
}
