package com.lescepat.views.fragments.schedule;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lescepat.R;
import com.lescepat.views.adapters.schedule.ScheduleAdapter;
import com.lescepat.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    List<Fragment> mFragments;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initEvent() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragments = new ArrayList<>();
        mFragments.add(new UpcomingScheduleFragment());
        mFragments.add(new UpcomingScheduleFragment());
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.vp_schedule_fragments_container);
        mViewPager.setAdapter(new ScheduleAdapter(getChildFragmentManager(), mFragments));

        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tl_schedule_tabs_container);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
