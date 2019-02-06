package id.co.lesku.views.activities.orders;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import id.co.lesku.R;
import id.co.lesku.views.fragments.transaction.TeacherOrderFragment;

public class TeacherOrderActivity extends AppCompatActivity {

    private String subject, selectedSchedule;
    private int position, subjectId;
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
                selectedSchedule = null;
            } else {
                subject = extras.getString("subject");
                subjectId = extras.getInt("subjectId");
                position = extras.getInt("position");
                selectedSchedule = extras.getString("selectedSchedule");
            }
        } else {
            subject = (String) savedInstanceState.getSerializable("subject");
            subjectId = (int) savedInstanceState.getSerializable("subjectId");
            position = (int) savedInstanceState.getSerializable("position");
            selectedSchedule = (String) savedInstanceState.getSerializable("selectedSchedule");
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
                bundle.putString("selectedSchedule", selectedSchedule);
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
