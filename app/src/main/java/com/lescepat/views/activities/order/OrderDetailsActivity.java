package com.lescepat.views.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lescepat.R;
import com.lescepat.views.fragments.order.DetailsOrderFragment;


public class OrderDetailsActivity extends AppCompatActivity {

    private String id, status;
    private TextView tvId;
    private Handler mHandler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        toolbar = (Toolbar) findViewById(R.id.toolbar_details_order);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                id = "";
                status = "";
            } else {
                id = extras.getString("id");
                status = extras.getString("status");
            }
        } else {
            id = (String) savedInstanceState.getSerializable("id");
            status = (String) savedInstanceState.getSerializable("status");
        }

        Toast.makeText(this, "Order ID : " + id, Toast.LENGTH_SHORT).show();
        if (savedInstanceState == null) {
            loadFragment();
        }
    }

    private void loadFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = new DetailsOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("status", status);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.detailsOrderFrame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    public void finishActivity(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
