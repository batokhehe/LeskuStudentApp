package com.lescepat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lescepat.R;
import com.lescepat.views.activities.auth.LoginActivity;
import com.lescepat.views.adapters.ViewPagerAdapter;
import com.lescepat.views.fragments.BlankFragment;
import com.lescepat.views.fragments.ListFragment;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends BaseActivity
{

    List<Fragment> mFragments;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        mFragments = new ArrayList<>();
        mFragments.add(new BlankFragment());
        mFragments.add(new ListFragment());

        // do we need to implement databinding on each layout? I don't think so. but feel free to bind the layout if you want to.
        mViewPager = (ViewPager) findViewById(R.id.vp_fragments_container);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));

        mTabLayout = (TabLayout) findViewById(R.id.tl_tabs_container);
        mTabLayout.setupWithViewPager(mViewPager);

        //proof of concept that location detection work
        if (app.mLastLocation != null)
        {
            Toast.makeText(this, "lat : " + app.mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "long : " + app.mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        MenuInflater lInflater = getMenuInflater();
        lInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                startActivity(new Intent(ViewPagerActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.action_about:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
