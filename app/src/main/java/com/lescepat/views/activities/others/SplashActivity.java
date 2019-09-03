package com.lescepat.views.activities.others;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lescepat.R;
import com.lescepat.data.DataManager;
import com.lescepat.manager.HawkManager;
import com.lescepat.model.StudyLevel;
import com.lescepat.model.Subject;
import com.lescepat.utils.RetrofitErrorAdapter;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {

    HawkManager hawkManager;
    List<Subject> mSubject;
    List<StudyLevel> mStudyLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hawkManager = new HawkManager();
        mSubject = new ArrayList<>();
        mStudyLevel = new ArrayList<>();
        startGetData();
    }

    private void startGetData(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if(hawkManager.getAppUserToken() != null && Hawk.get(K.STUDY_LEVEL_LIST) == null){
                DataManager.can().getStudyLevelList().observeOn(AndroidSchedulers.mainThread())
                        .defaultIfEmpty(new ArrayList<StudyLevel>())
                        .subscribe(new Consumer<List<StudyLevel>>()
                        {
                            @Override
                            public void accept (List<StudyLevel> studyLevels) throws Exception
                            {
                                if (mStudyLevel != null) { mStudyLevel.clear(); }
                                mStudyLevel.addAll(studyLevels);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept (Throwable throwable) throws Exception
                            {
                                RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                                Toast.makeText(SplashActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
//                DataManager.can().getSubject().observeOn(AndroidSchedulers.mainThread())
//                        .defaultIfEmpty(new ArrayList<Subject>())
//                        .subscribe(new Consumer<List<Subject>>()
//                        {
//                            @Override
//                            public void accept (List<Subject> subjects) throws Exception
//                            {
//                                if (mSubject != null) { mSubject.clear(); }
//                                mSubject.addAll(subjects);
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept (Throwable throwable) throws Exception
//                            {
//                                RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
//                                Toast.makeText(SplashActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
            }
            //some heavy processing resulting in a Data String
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Subject";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent i = new Intent(SplashActivity.this, IntroSliderActivity.class);
            startActivity(i);
            finish();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
