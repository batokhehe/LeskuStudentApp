package id.co.lesku.views.fragments.schedule;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentUpcomingScheduleBinding;
import id.co.lesku.model.UpcomingSchedule;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.UpcomingScheduleListViewModel;
import id.co.lesku.views.adapters.schedule.UpcomingScheduleAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UpcomingScheduleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentUpcomingScheduleBinding mBinding;
    List<UpcomingSchedule> mUpcomingSchedule;
    private OnFragmentInteractionListener mListener;
    UpcomingScheduleAdapter adapter;
    private boolean allowRefresh;

    public UpcomingScheduleFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mUpcomingSchedule = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming_schedule, container, false);
        mBinding.setSchedules(new UpcomingScheduleListViewModel());

        adapter = new UpcomingScheduleAdapter(mUpcomingSchedule, getContext());

        mBinding.rvUpcomingSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvUpcomingSchedule.setAdapter(adapter);

        adapter.setOnClickListener(new UpcomingScheduleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UpcomingSchedule UpcomingSchedule) {
                allowRefresh = true;
            }
        });

        // SwipeRefreshLayout
        mBinding.swipeUpcomingSchedule.setOnRefreshListener(this);
        mBinding.swipeUpcomingSchedule.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipeUpcomingSchedule.post(new Runnable() {
                @Override
                public void run() {

                    mBinding.swipeUpcomingSchedule.setRefreshing(true);

                    // Fetching data from server
                    loadRecyclerViewData();
                }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh)
        {
            allowRefresh = false;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadRecyclerViewData();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadRecyclerViewData()
    {
        // Showing refresh animation before making http call
        mBinding.swipeUpcomingSchedule.setRefreshing(true);
        DataManager.can().getUpcomingScheduleList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<UpcomingSchedule>())
                .subscribe(new Consumer<List<UpcomingSchedule>>()
                {
                    @Override
                    public void accept (List<UpcomingSchedule> UpcomingSchedules) throws Exception
                    {
                        mUpcomingSchedule.clear();
                        mUpcomingSchedule.addAll(UpcomingSchedules);

                        for (int i = 0; i<mUpcomingSchedule.size(); i++){
                            Log.i("Upcoming Schedule: ", mUpcomingSchedule.get(i).getSubjectName());
                        }
                        mBinding.rvUpcomingSchedule.getAdapter().notifyDataSetChanged();
                        if (mUpcomingSchedule.size() == 0)
                        {
                            mBinding.llUpcomingList.showEmptyView(true);
                        } else {
                            mBinding.llUpcomingList.showEmptyView(false);
                        }
                        mBinding.swipeUpcomingSchedule.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipeUpcomingSchedule.setRefreshing(false);
                    }
                });

    }

}
