package com.lescepat.viewmodels;

import com.lescepat.model.User;
import com.lescepat.viewmodels.inputs.UserViewModelInputs;
import com.lescepat.viewmodels.outputs.UserViewModelOutputs;

public class UserViewModel extends BaseViewModel implements UserViewModelInputs, UserViewModelOutputs
{
    private User mUser;

    public UserViewModel (User user)
    {
        mUser = user;
        notifyChange();
    }

    @Override
    public void setUser (User user)
    {
        mUser = user;
        notifyChange();
    }

    @Override
    public String getToken ()
    {
        return String.valueOf(mUser.getToken());
    }

    @Override
    public String getName ()
    {
        return String.valueOf(mUser.getFirstName());
    }

    @Override
    public String getEmail ()
    {
        return String.valueOf(mUser.getEmail());
    }


}
