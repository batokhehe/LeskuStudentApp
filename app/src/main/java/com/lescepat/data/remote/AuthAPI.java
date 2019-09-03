package com.lescepat.data.remote;

import com.lescepat.data.remote.contracts.Authentication;
import com.lescepat.utils.constants.K;
import com.google.gson.JsonObject;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class AuthAPI extends BaseAPI implements Authentication
{
    @Override
    public Maybe<JsonObject> login (String email, String password, String regid)
    {
        // TODO: define your own API URL
        return app.mAPIService.login(email, password, regid)
                              .retry(K.MAX_RETRIES)
                              .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }

    @Override
    public Maybe<JsonObject> updateAccount(String name, String email, String address, String phoneNumber, String encodedImage)
    {
        // TODO: define your own API URL
        return app.mAPIService.updateAccount(name, email, address, phoneNumber, encodedImage)
                .retry(K.MAX_RETRIES)
                .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }

    @Override
    public Maybe<JsonObject> register(String fname, String lname, String email, int studylevelid, String parent_name, String school_name, String address, String phone_number, String password, String cpassword)
    {
        return app.mAPIService.register(fname, lname, email, studylevelid, parent_name, school_name, address, phone_number,  password, cpassword)
                .retry(K.MAX_RETRIES)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void logout ()
    {
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id)
    {
        // TODO: define your own API URL
        return app.mAPIService.forgotPassword(id)
                              .retry(K.MAX_RETRIES)
                              .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }
}
