package id.co.lesku.views.adapters.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.co.lesku.R;
import id.co.lesku.databinding.RvItemUnpaidOrderBinding;
import id.co.lesku.model.UnpaidOrder;
import id.co.lesku.viewmodels.UnpaidOrderViewModel;

public class UnpaidOrderAdapter extends RecyclerView.Adapter<UnpaidOrderAdapter.ListViewHolder> {

    private RvItemUnpaidOrderBinding unpaidItemBinding;
    private List<UnpaidOrder> mUnpaidOrder;
    private Context mContext;
    private OnItemClickListener listener;

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
        public ListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mUnpaidOrder.get(position));
                    } else {
                        //do nothing
                    }
                }
            });
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
