package com.lescepat.views.activities.auth;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.lescepat.R;
import com.lescepat.manager.HawkManager;
import com.lescepat.utils.constants.I;
import com.lescepat.utils.constants.S;
import com.lescepat.views.activities.BaseActivity;
import com.lescepat.views.activities.MainActivity;
import com.lescepat.views.fragments.auth.ForgotPasswordFragment;
import com.lescepat.views.fragments.auth.LoginFragment;
import com.lescepat.views.fragments.auth.RegisterFragment;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements LoginFragment.OnLoginFragmentInteractionListener,
        ForgotPasswordFragment.OnForgotFragmentInteractionListener, EasyPermissions.PermissionCallbacks {

    FragmentManager fm = getSupportFragmentManager();
    ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
    RegisterFragment registerFragment = new RegisterFragment();
    FragmentTransaction ft = fm.beginTransaction();
    LoginFragment loginFragment = new LoginFragment();

    private HawkManager hawkManager;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hawkManager = new HawkManager();

        if(hawkManager.getAppUserToken() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
//            DataManager.can().removeUserToken();
        }

        //Firebase
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("Firebase ID", "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Firebase ID", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        hawkManager.storeFirebaseRegId(token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Firebase ID", hawkManager.getFirebaseId());
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        //request location permission early
        if (!EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION))
        {
            EasyPermissions
                    .requestPermissions(LoginActivity.this, S.location_permission_message, I.LOCATION_REQUEST_CODE,
                            Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // init first fragment
        ft = fm.beginTransaction();
        ft.add(R.id.fl_fragment_container, loginFragment);
        ft.commit();

    }

    @Override
    public void onForgotPasswordClick ()
    {
        showForgotPassword();
    }

    @Override
    public void onRegisterClick() {
        showRegister();
    }

    @Override
    public void onBackPressed ()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            finish();
        }
        else
        {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void showLoginForm ()
    {
        //clear backstack
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ft = fm.beginTransaction();
        ft.replace(R.id.fl_fragment_container, loginFragment);
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(I.LOCATION_REQUEST_CODE, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted (int requestCode, List<String> perms)
    {
        if (requestCode == I.LOCATION_REQUEST_CODE)
        {
            Toast.makeText(getApplicationContext(), R.string.permission_granted, Toast.LENGTH_LONG).show();
            super.initLocationDetection();
        }
    }

    @Override
    public void onPermissionsDenied (int requestCode, List<String> perms)
    {
        if (requestCode == I.LOCATION_REQUEST_CODE)
        {
            Toast.makeText(getApplicationContext(), R.string.permission_denied, Toast.LENGTH_LONG).show();
        }
    }

    public void showForgotPassword ()
    {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_fragment_container, forgotPasswordFragment);
        ft.addToBackStack("");
        ft.commit();
    }

    public void showRegister ()
    {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_fragment_container, registerFragment);
        ft.addToBackStack("");
        ft.commit();
    }
}
