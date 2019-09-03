package com.lescepat.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lescepat.R;
import com.lescepat.databinding.FragmentListItemBinding;
import com.lescepat.model.User;
import com.lescepat.viewmodels.UserViewModel;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>
{

    private FragmentListItemBinding mBinding;
    private List<User>              mUsers;

    public ListAdapter (List<User> users)
    {
        mUsers = users;
    }

    @Override
    public ListViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        mBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_list_item, parent, false);
        ListViewHolder vh = new ListViewHolder(mBinding.getRoot());
        vh.setBinding(mBinding);
        return vh;
    }

    @Override
    public void onBindViewHolder (ListViewHolder holder, int position)
    {
        holder.setUser(mUsers.get(position));
    }

    @Override
    public int getItemCount ()
    {
        return mUsers.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder
    {
        FragmentListItemBinding mBinding;

        ListViewHolder (View itemView)
        {
            super(itemView);
        }

        void setBinding (FragmentListItemBinding binding)
        {
            mBinding = binding;
        }

        public void setUser (User user)
        {
            if (mBinding.getUser() == null)
            {
                mBinding.setUser(new UserViewModel(user));
            }
            else
            {
                mBinding.getUser().setUser(user);
            }

        }
    }
}

