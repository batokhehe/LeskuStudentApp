package com.lescepat.views.activities.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lescepat.R;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

public class IpActivity extends AppCompatActivity {
    private EditText etIp;
    private Button btnSubmitIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        etIp = (EditText) findViewById(R.id.et_ip);
        btnSubmitIp = (Button) findViewById(R.id.btn_submit_ip);

        btnSubmitIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etIp.getText().toString();
                Hawk.put(K.IP, ip);
                Toast.makeText(IpActivity.this, "" + Hawk.get(K.IP), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IpActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });
    }
}
