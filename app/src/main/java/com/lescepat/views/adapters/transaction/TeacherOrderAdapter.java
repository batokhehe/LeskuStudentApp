package com.lescepat.views.adapters.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.RvItemTeacherOrderBinding;
import com.lescepat.model.TeacherOrder;
import com.lescepat.viewmodels.TeacherOrderViewModel;

import java.util.List;

public class TeacherOrderAdapter extends RecyclerView.Adapter<TeacherOrderAdapter.ListViewHolder> {

    private RvItemTeacherOrderBinding teacherOrderItemBinding;
    private List<TeacherOrder>   mTeacherOrder;
    private String TAG = "TeacherOrderListAdapter";
    private Context context;
    private TeacherOrderAdapter.OnItemClickListener listener;

    public TeacherOrderAdapter(List<TeacherOrder> products, Context ctx)
    {
        mTeacherOrder = products;
        context = ctx;
    }

    @Override
    public TeacherOrderAdapter.ListViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        teacherOrderItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item_teacher_order, parent, false);

        TeacherOrderAdapter.ListViewHolder vh = new TeacherOrderAdapter.ListViewHolder(teacherOrderItemBinding.getRoot());
        vh.setBinding(teacherOrderItemBinding);

        return vh;
    }

    @Override
    public void onBindViewHolder (TeacherOrderAdapter.ListViewHolder holder, int position)
    {
        holder.setTeacher(mTeacherOrder.get(position));
    }

    @Override
    public int getItemCount ()
    {
        return (mTeacherOrder == null) ? 0 : mTeacherOrder.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder
    {
        RvItemTeacherOrderBinding teacherOrderItemBinding;

        public ListViewHolder (View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mTeacherOrder.get(position));
                    }
                }
            });
        }

        void setBinding (RvItemTeacherOrderBinding binding)
        {
            teacherOrderItemBinding = binding;
        }

        public void setTeacher (TeacherOrder teacherOrder)
        {
            if (teacherOrderItemBinding.getTeacherOrder() == null)
            {
                teacherOrderItemBinding.setTeacherOrder(new TeacherOrderViewModel(teacherOrder));
            }
            else
            {
                teacherOrderItemBinding.getTeacherOrder().setTeacherOrder(teacherOrder);
            }

        }
    }

    public interface OnItemClickListener{
        void onItemClick(TeacherOrder teacherOrder);
    }

    public void setOnClickListener(TeacherOrderAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
