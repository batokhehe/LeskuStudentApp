package id.co.lesku.views.activities.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import id.co.lesku.R;
import id.co.lesku.views.activities.MainActivity;

public class InvoiceActivity extends AppCompatActivity {

    private Button btnGotoOrderPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        btnGotoOrderPage = (Button) findViewById(R.id.btn_goto_order_page);

        btnGotoOrderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvoiceActivity.this, MainActivity.class);
                intent.putExtra("gotoOrder", 1);
                startActivity(intent);
                finish();
            }
        });
    }
}
