package id.co.lesku.views.activities.others;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.manager.HawkManager;
import id.co.lesku.model.Subject;
import id.co.lesku.utils.RetrofitErrorAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {

    HawkManager hawkManager;
    List<Subject> mSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hawkManager = new HawkManager();
        mSubject = new ArrayList<>();
        startGetData();
    }

    private void startGetData(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if(hawkManager.getAppUserToken() != null){
                DataManager.can().getSubject().observeOn(AndroidSchedulers.mainThread())
                        .defaultIfEmpty(new ArrayList<Subject>())
                        .subscribe(new Consumer<List<Subject>>()
                        {
                            @Override
                            public void accept (List<Subject> subjects) throws Exception
                            {
                                if (mSubject != null) { mSubject.clear(); }
                                mSubject.addAll(subjects);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept (Throwable throwable) throws Exception
                            {
                                RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                                Toast.makeText(SplashActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
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
