package com.lescepat.views.adapters.schedule;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public ScheduleAdapter(FragmentManager fm)
    {
        super(fm);
        mFragments = new ArrayList<>();
    }

    public ScheduleAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem (int position)
    {
        return mFragments.get(position);
    }

    @Override
    public int getCount ()
    {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle (int position)
    {
        String[] titleList = {"Upcoming", "Calendar"};
        // TODO: implement your own page title.
//        return mFragments.get(position).getClass().getSimpleName();
        return titleList[position];
    }
}
