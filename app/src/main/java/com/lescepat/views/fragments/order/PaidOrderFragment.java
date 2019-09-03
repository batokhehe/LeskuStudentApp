package com.lescepat.views.fragments.order;

import android.content.Intent;
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
import com.lescepat.databinding.FragmentPaidOrderBinding;
import com.lescepat.model.PaidOrder;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.viewmodels.PaidOrderListViewModel;
import com.lescepat.views.activities.order.OrderDetailsActivity;
import com.lescepat.views.adapters.order.PaidOrderAdapter;
import com.lescepat.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class PaidOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener  {

    FragmentPaidOrderBinding mBinding;
    List<PaidOrder> mPaidOrder;
    private OnFragmentInteractionListener mListener;
    PaidOrderAdapter adapter;
    private boolean allowRefresh;

    public PaidOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mPaidOrder = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_paid_order, container, false);
        mBinding.setOrders(new PaidOrderListViewModel());

        adapter = new PaidOrderAdapter(mPaidOrder, getContext());

        mBinding.rvPaidOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvPaidOrder.setAdapter(adapter);

//        mBinding.llPaidList.showLoading(true, "Loading User List...");

//        loadRecyclerViewData();

        adapter.setOnClickListener(new PaidOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PaidOrder unpaidOrders) {
//                Toast.makeText(getContext(), "Order ID : " + PaidOrder.getId(), Toast.LENGTH_SHORT).show();
                allowRefresh = true;
                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                intent.putExtra("id", String.valueOf(unpaidOrders.getId()));
                intent.putExtra("status", String.valueOf(unpaidOrders.getStatus()));
                startActivity(intent);
            }
        });

        // SwipeRefreshLayout
        mBinding.swipePaidOrder.setOnRefreshListener(this);
        mBinding.swipePaidOrder.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipePaidOrder.post(new Runnable() {
            @Override
            public void run() {
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

    private void loadRecyclerViewData()
    {
        // Showing refresh animation before making http call
        mBinding.swipePaidOrder.setRefreshing(true);
        DataManager.can().getPaidOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<PaidOrder>())
                .subscribe(new Consumer<List<PaidOrder>>()
                {
                    @Override
                    public void accept (List<PaidOrder> paidOrders) throws Exception
                    {
                        Log.d("Paid Order Fragment", "accept: Data Refreshed");
                        mPaidOrder.clear();
                        mPaidOrder.addAll(paidOrders);
                        mBinding.rvPaidOrder.getAdapter().notifyDataSetChanged();
                        if (mPaidOrder.size() == 0)
                        {
                            mBinding.llPaidList.showEmptyView(true);
                        } else {
                            mBinding.llPaidList.showEmptyView(false);
                        }
                        mBinding.swipePaidOrder.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipePaidOrder.setRefreshing(false);
                    }
                });

    }
}
