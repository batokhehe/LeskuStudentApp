package com.lescepat.views.adapters.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.RvItemWaitingOrderBinding;
import com.lescepat.model.WaitingOrder;
import com.lescepat.viewmodels.WaitingOrderViewModel;

import java.util.List;

public class WaitingOrderAdapter extends RecyclerView.Adapter<WaitingOrderAdapter.ListViewHolder> {

    private RvItemWaitingOrderBinding waitingItemBinding;
    private List<WaitingOrder> mWaitingOrder;
    private Context mContext;
    private OnItemClickListener listener;

    public WaitingOrderAdapter(List<WaitingOrder> waitingOrders, Context context) {
        mWaitingOrder = waitingOrders;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        waitingItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_waiting_order, parent, false);

        WaitingOrderAdapter.ListViewHolder vh = new WaitingOrderAdapter.ListViewHolder(waitingItemBinding.getRoot());
        vh.setBinding(waitingItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingOrderAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mWaitingOrder.get(position));
    }

    @Override
    public int getItemCount() {
        return (mWaitingOrder == null) ? 0 : mWaitingOrder.size();
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
                        listener.onItemClick(mWaitingOrder.get(position));
                    } else {
                        //do nothing
                    }
                }
            });
        }

        void setBinding (RvItemWaitingOrderBinding binding)
        {
            waitingItemBinding = binding;
        }

        public void setOrder (WaitingOrder WaitingOrder)
        {
            if (waitingItemBinding.getWaitingOrder() == null)
            {
                waitingItemBinding.setWaitingOrder(new WaitingOrderViewModel(WaitingOrder));
            }
            else
            {
                waitingItemBinding.getWaitingOrder().setOrder(WaitingOrder);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(WaitingOrder waitingItemBinding);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
