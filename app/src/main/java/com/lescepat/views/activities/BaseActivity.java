package com.lescepat.views.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lescepat.LesCepatApplication;

import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    LesCepatApplication app = LesCepatApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (app.mGoogleApiClient != null) {
            app.mGoogleApiClient.connect(); //try to reconnect on new activity
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //check for location permission
        if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            app.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(app.mGoogleApiClient);
        }

    }

    @Override
    public void onConnectionSuspended (int i)
    {
        // TODO: add warning on connection suspended
    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult result)
    {
        // TODO: add error message on connection failed
    }

    public void initLocationDetection ()
    {
        if (app.mGoogleApiClient == null)
        {
            //check for location permission
            if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION))
            {
                app.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
        }
        else
        {
            app.mGoogleApiClient.connect();
        }
    }
}
