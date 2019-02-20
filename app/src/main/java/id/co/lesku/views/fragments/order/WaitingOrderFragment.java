package id.co.lesku.views.fragments.order;

import android.content.Intent;
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
import id.co.lesku.databinding.FragmentWaitingOrderBinding;
import id.co.lesku.model.WaitingOrder;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.WaitingOrderListViewModel;
import id.co.lesku.views.activities.order.OrderDetailsActivity;
import id.co.lesku.views.adapters.order.WaitingOrderAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class WaitingOrderFragment  extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    FragmentWaitingOrderBinding mBinding;
    List<WaitingOrder> mWaitingOrder;
    private OnFragmentInteractionListener mListener;
    WaitingOrderAdapter adapter;
    private boolean allowRefresh;

    public WaitingOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mWaitingOrder = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_waiting_order, container, false);
        mBinding.setOrders(new WaitingOrderListViewModel());

        adapter = new WaitingOrderAdapter(mWaitingOrder, getContext());

        mBinding.rvWaitingOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvWaitingOrder.setAdapter(adapter);

        adapter.setOnClickListener(new WaitingOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WaitingOrder waitingOrder) {
                allowRefresh = true;
                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                intent.putExtra("id", String.valueOf(waitingOrder.getId()));
                intent.putExtra("status", String.valueOf(waitingOrder.getStatus()));
                startActivity(intent);
            }
        });

        // SwipeRefreshLayout
        mBinding.swipeWaitingOrder.setOnRefreshListener(this);
        mBinding.swipeWaitingOrder.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipeWaitingOrder.post(new Runnable() {
            @Override
            public void run() {
                // Fetching data from server
                loadRecyclerViewData();
            }
        });

        return mBinding.getRoot();
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

    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh)
        {
            allowRefresh = false;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadRecyclerViewData()
    {
        // Showing refresh animation before making http call
        mBinding.swipeWaitingOrder.setRefreshing(true);
        DataManager.can().getWaitingOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<WaitingOrder>())
                .subscribe(new Consumer<List<WaitingOrder>>()
                {
                    @Override
                    public void accept (List<WaitingOrder> waitingOrders) throws Exception
                    {
                        Log.d("Unpaid Order Fragment", "accept: Data Refreshed");
                        mWaitingOrder.clear();
                        mWaitingOrder.addAll(waitingOrders);
                        mBinding.rvWaitingOrder.getAdapter().notifyDataSetChanged();
                        if (mWaitingOrder.size() == 0)
                        {
                            mBinding.llWaitingList.showEmptyView(true);
                        } else {
                            mBinding.llWaitingList.showEmptyView(false);
                        }
                        mBinding.swipeWaitingOrder.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipeWaitingOrder.setRefreshing(false);
                    }
                });

    }
}
