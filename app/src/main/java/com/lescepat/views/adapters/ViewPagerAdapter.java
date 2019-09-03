package com.lescepat.views.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bukhoriaqid on 11/27/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments;

    public ViewPagerAdapter (FragmentManager fm)
    {
        super(fm);
        mFragments = new ArrayList<>();
    }

    public ViewPagerAdapter (FragmentManager fm, List<Fragment> fragments)
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
        // TODO: implement your own page title.
        return mFragments.get(position).getClass().getSimpleName();
    }
}
