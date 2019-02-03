package id.co.lesku;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.hawk.Hawk;

import id.co.lesku.data.remote.retrofit.LeskuAPIService;
import id.co.lesku.data.remote.retrofit.RetrofitServiceFactory;
import id.co.lesku.manager.PrefManager;

public class LeskuApplication extends Application {
    private static LeskuApplication   sApp;
    public LeskuAPIService mAPIService;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    private String token = null;
    PrefManager prefManager;
    private String TAG = this.getClass().getSimpleName();

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

    public boolean isNetworkAvailable ()
    {
        ConnectivityManager lConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo lNetworkInfo         = lConnectivityManager.getActiveNetworkInfo();
        return lNetworkInfo != null && lNetworkInfo.isConnected();
    }



    public void updateService(){
        mAPIService = RetrofitServiceFactory.createService(LeskuAPIService.class, LeskuApplication.this);
    }
}
