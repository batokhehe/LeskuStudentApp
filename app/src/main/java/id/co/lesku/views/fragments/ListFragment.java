package id.co.lesku.views.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentListBinding;
import id.co.lesku.model.User;
import id.co.lesku.utils.DummyDataFactory;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.UserListViewModel;
import id.co.lesku.views.adapters.ListAdapter;
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
