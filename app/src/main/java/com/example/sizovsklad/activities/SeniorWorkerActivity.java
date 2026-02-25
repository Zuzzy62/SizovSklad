package com.example.sizovsklad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sizovsklad.R;
import com.example.sizovsklad.utils.SessionManager;

public class SeniorWorkerActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_worker);

        session = new SessionManager(this);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText(String.format(getString(R.string.welcome), session.getUser().getFullName()));

        Button btnReceipt = findViewById(R.id.btnReceipt);
        Button btnConfirm = findViewById(R.id.btnConfirm);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnReceipt.setOnClickListener(v -> startActivity(new Intent(this, ReceiptActivity.class)));
        btnConfirm.setOnClickListener(v -> startActivity(new Intent(this, ConfirmOperationsActivity.class)));
        btnLogout.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}