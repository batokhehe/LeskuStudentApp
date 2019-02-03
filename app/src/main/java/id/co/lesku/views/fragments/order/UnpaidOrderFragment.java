package id.co.lesku.views.fragments.order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentUnpaidOrderBinding;
import id.co.lesku.models.UnpaidOrder;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.UnpaidOrderListViewModel;
import id.co.lesku.views.activities.orders.OrderDetailsActivity;
import id.co.lesku.views.adapters.order.UnpaidOrderAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class UnpaidOrderFragment extends BaseFragment {

    FragmentUnpaidOrderBinding mBinding;
    List<UnpaidOrder> mUnpaidOrder;
    private OnFragmentInteractionListener mListener;
    UnpaidOrderAdapter adapter;
    private boolean allowRefresh;

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

        mBinding.llUnpaidList.showCustomLoading(true, "Loading User List...");

        DataManager.can().getUnpaidOrderList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<UnpaidOrder>())
                .subscribe(new Consumer<List<UnpaidOrder>>()
                {
                    @Override
                    public void accept (List<UnpaidOrder> unpaidOrders) throws Exception
                    {
                        if (mUnpaidOrder != null) { mUnpaidOrder.clear(); }
                        mUnpaidOrder.addAll(unpaidOrders);
                        mBinding.rvUnpaidOrder.getAdapter().notifyDataSetChanged();
                        mBinding.llUnpaidList.showCustomLoading(false);
                        if (mUnpaidOrder.size() == 0)
                        {
                            mBinding.llUnpaidList.showEmptyView(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.llUnpaidList.showCustomLoading(false);
                    }
                });

        adapter.setOnClickListener(new UnpaidOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UnpaidOrder unpaidOrder) {
//                Toast.makeText(getContext(), "Order ID : " + unpaidOrder.getId(), Toast.LENGTH_SHORT).show();
                allowRefresh = true;
                Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
                intent.putExtra("id", String.valueOf(unpaidOrder.getId()));
                startActivity(intent);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
