package com.lescepat.views.fragments.history;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.databinding.FragmentHistoryBinding;
import com.lescepat.model.History;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.viewmodels.HistoryListViewModel;
import com.lescepat.views.adapters.history.HistoryAdapter;
import com.lescepat.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class HistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentHistoryBinding mBinding;
    List<History> mHistory;
    private OnFragmentInteractionListener mListener;
    HistoryAdapter adapter;
    private boolean allowRefresh;

    public HistoryFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mHistory = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        mBinding.setHistory(new HistoryListViewModel());

        adapter = new HistoryAdapter(mHistory, getContext());

        mBinding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvHistory.setAdapter(adapter);

        adapter.setOnClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(History History) {
                allowRefresh = true;
            }
        });

        // SwipeRefreshLayout
        mBinding.swipeHistory.setOnRefreshListener(this);
        mBinding.swipeHistory.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipeHistory.post(new Runnable() {
                @Override
                public void run() {

                    mBinding.swipeHistory.setRefreshing(true);

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
        mBinding.swipeHistory.setRefreshing(true);
        DataManager.can().getHistoryList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<History>())
                .subscribe(new Consumer<List<History>>()
                {
                    @Override
                    public void accept (List<History> Historys) throws Exception
                    {
                        mHistory.clear();
                        mHistory.addAll(Historys);

                        for (int i = 0; i<mHistory.size(); i++){
                            Log.i("Upcoming Schedule: ", mHistory.get(i).getSubjectName());
                        }
                        mBinding.rvHistory.getAdapter().notifyDataSetChanged();
                        if (mHistory.size() == 0)
                        {
                            mBinding.llHistoryList.showEmptyView(true);
                        } else {
                            mBinding.llHistoryList.showEmptyView(false);
                        }
                        mBinding.swipeHistory.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipeHistory.setRefreshing(false);
                    }
                });

    }

}
