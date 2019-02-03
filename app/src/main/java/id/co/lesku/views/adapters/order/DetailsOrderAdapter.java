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
import id.co.lesku.databinding.RvItemDetailsOrderBinding;
import id.co.lesku.models.DetailsOrder;
import id.co.lesku.viewmodels.DetailsOrderViewModel;

public class DetailsOrderAdapter extends RecyclerView.Adapter<DetailsOrderAdapter.ListViewHolder> {

    private RvItemDetailsOrderBinding detailsOrderItemBinding;
    private List<DetailsOrder> mDetailsOrder;
    private String TAG = "DetailsOrderListAdapter";
    private Context context;

    public DetailsOrderAdapter(List<DetailsOrder> products, Context ctx)
    {
        mDetailsOrder = products;
        context = ctx;
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
