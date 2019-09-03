package com.lescepat.views.adapters.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.RvItemProductBinding;
import com.lescepat.model.Product;
import com.lescepat.viewmodels.ProductViewModel;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ListViewHolder> {

    private RvItemProductBinding productItemBinding;
    private List<Product>              mProduct;
    private String TAG = "ProductListAdapter";
    private Context context;
    private OnItemClickListener listener;

    public ProductAdapter(List<Product> products, Context ctx)
    {
        mProduct = products;
        context = ctx;
    }

    @Override
    public ListViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        productItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_product, parent, false);

        ProductAdapter.ListViewHolder vh = new ProductAdapter.ListViewHolder(productItemBinding.getRoot());
        vh.setBinding(productItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder (ListViewHolder holder, int position)
    {
        holder.setProduct(mProduct.get(position));
//        Product product = mProduct.get(position);
//        holder.productItemBinding.setProduct(product);
//        holder.productItemBinding.setItemClickListener(this);
    }

    @Override
    public int getItemCount ()
    {
        return (mProduct == null) ? 0 : mProduct.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder
    {
        RvItemProductBinding productItemBinding;

        public ListViewHolder (View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mProduct.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemProductBinding binding)
        {
            productItemBinding = binding;
        }

        public void setProduct (Product product)
        {
            if (productItemBinding.getProduct() == null)
            {
                productItemBinding.setProduct(new ProductViewModel(product));
            }
            else
            {
                productItemBinding.getProduct().setProduct(product);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Product product);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.product, popup.getMenu());
//        popup.setOnMenuItemClickListener(new ProductItemClickListener());
//        popup.show();
//    }
//
//    class ProductItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public ProductItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_detail:
//                    Toast.makeText(mContext, "Detail", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }
}
