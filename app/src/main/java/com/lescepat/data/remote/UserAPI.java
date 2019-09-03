package com.lescepat.data.remote;

import com.lescepat.model.User;
import com.lescepat.utils.constants.K;

import java.util.List;

import com.lescepat.data.remote.contracts.CRUDContract;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class UserAPI extends BaseAPI implements CRUDContract<User, Integer, String>
{
    @Override
    public Maybe<List<User>> getList ()
    {
        return app.mAPIService.getUsers().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<User>> findList(Integer id) {
        return null;
    }

    @Override
    public Maybe<List<User>> findListSelected(Integer id, Integer io, String s) {
        return null;
    }

    @Override
    public void create (User obj)
    {
        // TODO: implement your own code 
    }

    @Override
    public Maybe<User> read (Integer id)
    {
        return app.mAPIService.getUser(id.toString()).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());

    }

    @Override
    public void update (User obj, Integer id)
    {
        // TODO: implement your own code 
    }

    @Override
    public void delete (Integer id)
    {
        // TODO: implement your own code
    }
}
