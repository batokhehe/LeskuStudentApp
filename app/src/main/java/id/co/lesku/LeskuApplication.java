package id.co.lesku;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.hawk.Hawk;

import id.co.lesku.data.remote.retrofit.LeskuAPIService;
import id.co.lesku.data.remote.retrofit.RetrofitServiceFactory;

public class LeskuApplication extends Application {
    private static LeskuApplication   sApp;
    public LeskuAPIService mAPIService;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;

    public static LeskuApplication getInstance ()
    {
        if (sApp == null)
        {
            sApp = new LeskuApplication();
        }

        return sApp;
    }

    @Override
    public void onCreate ()
    {
        super.onCreate();

        Hawk.init(getApplicationContext()).build();
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LeskuAPIService.class, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LeskuAPIService.class, LeskuApplication.this);
    }

    public void onLoggedIn(Context context){
        sApp = this;
        mAPIService = RetrofitServiceFactory.createService(LeskuAPIService.class, context);
    }

    public boolean isNetworkAvailable ()
    {
        ConnectivityManager lConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetworkInfo         = lConnectivityManager.getActiveNetworkInfo();
        return lNetworkInfo != null && lNetworkInfo.isConnected();
    }
}
