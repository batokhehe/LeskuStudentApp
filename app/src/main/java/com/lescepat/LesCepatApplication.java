package com.lescepat;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lescepat.data.remote.retrofit.RetrofitServiceFactory;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.hawk.Hawk;

import com.lescepat.data.remote.retrofit.LesCepatAPIService;

public class LesCepatApplication extends Application {
    private static LesCepatApplication sApp;
    public LesCepatAPIService mAPIService;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;

    public static LesCepatApplication getInstance ()
    {
        if (sApp == null)
        {
            sApp = new LesCepatApplication();
        }

        return sApp;
    }

    @Override
    public void onCreate ()
    {
        super.onCreate();

        Hawk.init(getApplicationContext()).build();
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LesCepatAPIService.class, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LesCepatAPIService.class, LesCepatApplication.this);
    }

    public void onLoggedIn(Context context){
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LesCepatAPIService.class, context);
    }

    public boolean isNetworkAvailable ()
    {
        ConnectivityManager lConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetworkInfo         = lConnectivityManager.getActiveNetworkInfo();
        return lNetworkInfo != null && lNetworkInfo.isConnected();
    }
}
