package com.lescepat.views.adapters.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.RvItemDetailsOrderBinding;
import com.lescepat.model.DetailsOrder;
import com.lescepat.viewmodels.DetailsOrderViewModel;

import java.util.List;

public class DetailsOrderAdapter extends RecyclerView.Adapter<DetailsOrderAdapter.ListViewHolder> {

    private RvItemDetailsOrderBinding detailsOrderItemBinding;
    private List<DetailsOrder> mDetailsOrder;
    private String TAG = "DetailsOrderListAdapter";
    private Context context;
    private String orderStatus;

    public DetailsOrderAdapter(List<DetailsOrder> products, Context ctx, String status)
    {
        mDetailsOrder = products;
        context = ctx;
        orderStatus = status;
    }

    @NonNull
    @Override
    public DetailsOrderAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        detailsOrderItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_details_order, parent, false);

        DetailsOrderAdapter.ListViewHolder vh = new DetailsOrderAdapter.ListViewHolder(detailsOrderItemBinding.getRoot());
        vh.setBinding(detailsOrderItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsOrderAdapter.ListViewHolder holder, int position) {
        holder.setDetails(mDetailsOrder.get(position));
    }

    @Override
    public int getItemCount() {
        return (mDetailsOrder == null) ? 0 : mDetailsOrder.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(View itemView) {
            super(itemView);
            TextView status = (TextView) itemView.findViewById(R.id.tv_detail_order_status);
            if(orderStatus.equals("0")){
                status.setVisibility(View.GONE);
            } else {
                status.setVisibility(View.VISIBLE);
            }
        }

        void setBinding (RvItemDetailsOrderBinding binding)
        {
            detailsOrderItemBinding = binding;
        }

        public void setDetails (DetailsOrder detailsOrder)
        {
            if (detailsOrderItemBinding.getDetailsOrder() == null)
            {
                detailsOrderItemBinding.setDetailsOrder(new DetailsOrderViewModel(detailsOrder));
            }
            else
            {
                detailsOrderItemBinding.getDetailsOrder().setDetailsOrder(detailsOrder);
            }

        }
    }
}
