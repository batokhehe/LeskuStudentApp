package id.co.lesku.views.fragments.history;

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
import id.co.lesku.databinding.FragmentHistoryBinding;
import id.co.lesku.model.History;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.HistoryListViewModel;
import id.co.lesku.views.adapters.history.HistoryAdapter;
import id.co.lesku.views.fragments.BaseFragment;
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
