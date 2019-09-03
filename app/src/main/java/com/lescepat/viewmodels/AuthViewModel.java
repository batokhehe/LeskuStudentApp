package com.lescepat.viewmodels;

import android.util.Log;

import com.lescepat.data.DataManager;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class AuthViewModel extends BaseViewModel
{
    String email, password, regid;

    public void login ()
    {
        DataManager.can().login(email, password, regid)
                   .observeOn(AndroidSchedulers.mainThread())
                   .doOnSuccess(new Consumer<JsonObject>()
                   {
                       @Override
                       public void accept (JsonObject object) throws Exception
                       {
                           // do on success
                           Log.d("login", "do on success");
                       }
                   })
                   .doOnError(new Consumer<Throwable>()
                   {
                       @Override
                       public void accept (Throwable throwable) throws Exception
                       {
                           // do on error
                           Log.d("login", "do on error");
                       }
                   })
                   .subscribe(new Consumer<JsonObject>()
                   {
                       @Override
                       public void accept (JsonObject object) throws Exception
                       {
                           // on success
                           Log.d("login", "on success");
                       }
                   }, new Consumer<Throwable>()
                   {
                       @Override
                       public void accept (Throwable throwable) throws Exception
                       {
                           // on error
                           Log.d("login", "on error");
                       }
                   });
    }
}
