package com.lescepat.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.databinding.FragmentListBinding;
import com.lescepat.model.User;
import com.lescepat.utils.DummyDataFactory;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.viewmodels.UserListViewModel;
import com.lescepat.views.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by bukhoriaqid on 11/27/16.
 */

public class ListFragment extends BaseFragment
{
    FragmentListBinding mBinding;
    List<User>          mUsers;


    public ListFragment ()
    {
        setArguments(new Bundle());
        mUsers = DummyDataFactory.createDummyUsers();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        mBinding.setUsers(new UserListViewModel());

        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvContent.setAdapter(new ListAdapter(mUsers));

        mBinding.llUserList.showCustomLoading(true, "Loading User List...");

        DataManager.can().getUserList().observeOn(AndroidSchedulers.mainThread())
                   .defaultIfEmpty(new ArrayList<User>())
                   .subscribe(new Consumer<List<User>>()
                   {
                       @Override
                       public void accept (List<User> users) throws Exception
                       {
                           mUsers.clear();
                           mUsers.addAll(users);
                           mBinding.rvContent.getAdapter().notifyDataSetChanged();
                           mBinding.llUserList.showCustomLoading(false);
                           if (mUsers.size() == 0)
                           {
                               mBinding.llUserList.showEmptyView(true);
                           }
                       }
                   }, new Consumer<Throwable>() {
                       @Override
                       public void accept (Throwable throwable) throws Exception
                       {
                           RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                           Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                           mBinding.llUserList.showCustomLoading(false);
                       }
                   });

        return mBinding.getRoot();
    }

    @Override
    public void initUI()
    {

    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void onStart ()
    {
        super.onStart();
    }

    @Override
    public void onStop ()
    {
        super.onStop();
    }

}
