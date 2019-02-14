package id.co.lesku.views.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import id.co.lesku.R;
import id.co.lesku.manager.HawkManager;
import id.co.lesku.views.activities.MainActivity;

public class InvoiceActivity extends AppCompatActivity {

    private Button btnGotoOrderPage;
    private String price, ordered_assembly, ordered_subject;
    private String created_at;
    private HawkManager hawkManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                price= null;
                ordered_assembly = null;
                ordered_subject = null;
                created_at = null;
            } else {
                price = extras.getString("price");
                ordered_assembly = extras.getString("ordered_assembly");
                ordered_subject = extras.getString("ordered_subject");
                created_at = extras.getString("created_at");
            }
        } else {
            price = (String) savedInstanceState.getSerializable("price");
            ordered_assembly = (String) savedInstanceState.getSerializable("ordered_assembly");
            ordered_subject = (String) savedInstanceState.getSerializable("ordered_subject");
            created_at = (String) savedInstanceState.getSerializable("created_at");
        }

        TextView tvAccount = (TextView) findViewById(R.id.tv_invoice_account);
        TextView tvCreatedAt = (TextView) findViewById(R.id.tv_invoice_created_at);
        TextView tvOrderedAssembly = (TextView) findViewById(R.id.tv_invoice_ordered_assembly);
        TextView tvOrderedSubject = (TextView) findViewById(R.id.tv_invoice_ordered_subject);
        TextView tvPrice = (TextView) findViewById(R.id.tv_invoice_price);

        hawkManager = new HawkManager();
        NumberFormat formatter = new DecimalFormat("#.###");

        tvAccount.setText(hawkManager.getAppUserName());
        tvCreatedAt.setText(created_at);
        tvOrderedAssembly.setText(ordered_assembly);
        tvOrderedSubject.setText(ordered_subject);
        tvPrice.setText("Rp. " + formatter.format(price));

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
