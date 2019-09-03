package com.lescepat.views.fragments.order;

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
import com.lescepat.databinding.FragmentUnpaidOrderBinding;
import com.lescepat.model.UnpaidOrder;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.viewmodels.UnpaidOrderListViewModel;
import com.lescepat.views.adapters.order.UnpaidOrderAdapter;
import com.lescepat.views.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UnpaidOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener  {

    FragmentUnpaidOrderBinding mBinding;
    List<UnpaidOrder> mUnpaidOrder;
    private OnFragmentInteractionListener mListener;
    UnpaidOrderAdapter adapter;
    private boolean allowRefresh;
//    SwipeRefreshLayout mSwipeRefreshLayout;

    public UnpaidOrderFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mUnpaidOrder = new ArrayList<>();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_unpaid_order, container, false);
        mBinding.setOrders(new UnpaidOrderListViewModel());

        adapter = new UnpaidOrderAdapter(mUnpaidOrder, getContext());

        mBinding.rvUnpaidOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvUnpaidOrder.setAdapter(adapter);

//        mBinding.llUnpaidList.showLoading(true, "Loading User List...");

//        loadRecyclerViewData();

//        adapter.setOnClickListener(new UnpaidOrderAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(UnpaidOrder unpaidOrder) {
////                Toast.makeText(getContext(), "Order ID : " + unpaidOrder.getId(), Toast.LENGTH_SHORT).show();
//                allowRefresh = true;
//                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
//                intent.putExtra("id", String.valueOf(unpaidOrder.getId()));
//                intent.putExtra("status", String.valueOf(unpaidOrder.getStatus()));
//                startActivity(intent);
//            }
//        });

        // SwipeRefreshLayout
        mBinding.swipeUnpaidOrder.setOnRefreshListener(this);
        mBinding.swipeUnpaidOrder.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mBinding.swipeUnpaidOrder.post(new Runnable() {
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
        mBinding.swipeUnpaidOrder.setRefreshing(true);
        DataManager.can().getUnpaidOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<UnpaidOrder>())
                .subscribe(new Consumer<List<UnpaidOrder>>()
                {
                    @Override
                    public void accept (List<UnpaidOrder> unpaidOrders) throws Exception
                    {
                        mUnpaidOrder.clear();
                        mUnpaidOrder.addAll(unpaidOrders);
                        for (UnpaidOrder anArray : mUnpaidOrder) {
                            Log.d("Unpaid", "ID: " + anArray.getId() + " Product : " + anArray.getProductName() + " Status : " + anArray.getStatus() + " Created At : " + anArray.getCreatedAt());
                        }
                        mBinding.rvUnpaidOrder.getAdapter().notifyDataSetChanged();
                        if (mUnpaidOrder.size() == 0)
                        {
                            mBinding.llUnpaidList.showEmptyView(true);
                        } else {
                            mBinding.llUnpaidList.showEmptyView(false);
                        }
                        mBinding.swipeUnpaidOrder.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.swipeUnpaidOrder.setRefreshing(false);
                    }
                });

    }
}
