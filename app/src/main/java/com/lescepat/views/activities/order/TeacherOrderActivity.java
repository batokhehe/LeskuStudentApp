package com.lescepat.views.activities.order;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lescepat.R;
import com.lescepat.views.fragments.transaction.TeacherOrderFragment;

public class TeacherOrderActivity extends AppCompatActivity {

    private String subject, schedule;
    private int position, subjectId, studyLevel;
    private Toolbar toolbar;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_order);

        toolbar = (Toolbar) findViewById(R.id.toolbar_teacher_order);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                subject= null;
                position = 0;
                subjectId = 0;
                schedule = null;
            } else {
                subject = extras.getString("subject");
                subjectId = extras.getInt("subjectId");
                studyLevel = extras.getInt("studyLevel");
                position = extras.getInt("position");
                schedule = extras.getString("schedule");
            }
        } else {
            subject = (String) savedInstanceState.getSerializable("subject");
            subjectId = (int) savedInstanceState.getSerializable("subjectId");
            studyLevel = (int) savedInstanceState.getSerializable("studyLevel");
            position = (int) savedInstanceState.getSerializable("position");
            schedule = (String) savedInstanceState.getSerializable("schedule");
        }

        if (savedInstanceState == null) {
            loadFragment();
        }
    }

    private void loadFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = new TeacherOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("subject", subject);
                bundle.putInt("subjectId", subjectId);
                bundle.putInt("position", position);
                bundle.putInt("studyLevel", studyLevel);
                bundle.putString("schedule", schedule);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.teacherOrderFrame, fragment);
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
}
