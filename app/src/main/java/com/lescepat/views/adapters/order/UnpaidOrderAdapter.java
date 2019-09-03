package com.lescepat.views.adapters.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.RvItemUnpaidOrderBinding;
import com.lescepat.model.UnpaidOrder;
import com.lescepat.viewmodels.UnpaidOrderViewModel;
import com.lescepat.views.activities.order.InvoiceActivity;
import com.lescepat.views.activities.order.OrderDetailsActivity;

import java.util.List;

public class UnpaidOrderAdapter extends RecyclerView.Adapter<UnpaidOrderAdapter.ListViewHolder> {

    private RvItemUnpaidOrderBinding unpaidItemBinding;
    private List<UnpaidOrder> mUnpaidOrder;
    private Context mContext;
    private OnItemClickListener listener;
    public UnpaidOrder unpaidOrders;

    public UnpaidOrderAdapter(List<UnpaidOrder> unpaidOrder, Context context) {
        mUnpaidOrder = unpaidOrder;
        mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        unpaidItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_unpaid_order, parent, false);

        UnpaidOrderAdapter.ListViewHolder vh = new UnpaidOrderAdapter.ListViewHolder(unpaidItemBinding.getRoot());
        vh.setBinding(unpaidItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UnpaidOrderAdapter.ListViewHolder holder, int position) {
        holder.setOrder(mUnpaidOrder.get(position));
        unpaidOrders = mUnpaidOrder.get(position);
    }

    @Override
    public int getItemCount() {
        return (mUnpaidOrder == null) ? 0 : mUnpaidOrder.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public Button btnDetail, btnInvoice;

        public ListViewHolder(View itemView) {
            super(itemView);
            btnDetail = (Button) itemView.findViewById(R.id.btn_detail);
            btnInvoice = (Button) itemView.findViewById(R.id.btn_invoice);
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    intent.putExtra("id", String.valueOf(unpaidOrders.getId()));
                    intent.putExtra("status", String.valueOf(unpaidOrders.getStatus()));
                    mContext.startActivity(intent);
                }
            });

            btnInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, InvoiceActivity.class);
                    intent.putExtra("price", String.valueOf(unpaidOrders.getPrice()));
                    intent.putExtra("ordered_assembly", String.valueOf(unpaidOrders.getOrderedAssembly()));
                    intent.putExtra("ordered_subject", String.valueOf(unpaidOrders.getOrderedSubject()));
                    intent.putExtra("created_at", String.valueOf(unpaidOrders.getCreatedAt()));
                    mContext.startActivity(intent);
                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION){
//                        listener.onItemClick(mUnpaidOrder.get(position));
//                    } else {
//                        //do nothing
//                    }
//                }
//            });
        }

        void setBinding (RvItemUnpaidOrderBinding binding)
        {
            unpaidItemBinding = binding;
        }

        public void setOrder (UnpaidOrder unpaidOrder)
        {
            if (unpaidItemBinding.getUnpaidOrder() == null)
            {
                unpaidItemBinding.setUnpaidOrder(new UnpaidOrderViewModel(unpaidOrder));
            }
            else
            {
                unpaidItemBinding.getUnpaidOrder().setOrder(unpaidOrder);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(UnpaidOrder unpaidOrder);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
