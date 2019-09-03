package com.lescepat.views.adapters.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.RvItemPaidOrderBinding;
import com.lescepat.model.PaidOrder;
import com.lescepat.viewmodels.PaidOrderViewModel;

import java.util.List;

public class PaidOrderAdapter extends RecyclerView.Adapter<PaidOrderAdapter.ListViewHolder> {

    private RvItemPaidOrderBinding paidItemBinding;
    private List<PaidOrder> mPaidOrder;
    private Context mContext;
    private OnItemClickListener listener;
    public PaidOrder paidOrders;

    public PaidOrderAdapter(List<PaidOrder> paidOrders, Context context) {
        mPaidOrder = paidOrders;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        paidItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_paid_order, parent, false);

        PaidOrderAdapter.ListViewHolder vh = new PaidOrderAdapter.ListViewHolder(paidItemBinding.getRoot());
        vh.setBinding(paidItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PaidOrderAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mPaidOrder.get(position));
        paidOrders = mPaidOrder.get(position);
    }

    @Override
    public int getItemCount() {
        return (mPaidOrder == null) ? 0 : mPaidOrder.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mPaidOrder.get(position));
                    } else {
                        //do nothing
                    }
                }
            });
        }

        void setBinding (RvItemPaidOrderBinding binding)
        {
            paidItemBinding = binding;
        }

        public void setOrder (PaidOrder paidOrder)
        {
            if (paidItemBinding.getPaidOrder() == null)
            {
                paidItemBinding.setPaidOrder(new PaidOrderViewModel(paidOrder));
            }
            else
            {
                paidItemBinding.getPaidOrder().setOrder(paidOrder);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(PaidOrder PaidOrder);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
