package com.example.sizovsklad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sizovsklad.R;
import com.example.sizovsklad.utils.SessionManager;

public class JuniorWorkerActivity extends AppCompatActivity {

    private SessionManager session;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junior_worker);

        session = new SessionManager(this);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText(String.format(getString(R.string.welcome), session.getUser().getFullName()));

        Button btnReceipt = findViewById(R.id.btnReceipt);
        Button btnShipment = findViewById(R.id.btnShipment);
        Button btnInventory = findViewById(R.id.btnInventory);
        Button btnTransfer = findViewById(R.id.btnTransfer);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnReceipt.setOnClickListener(v -> startActivity(new Intent(this, ReceiptActivity.class)));
        btnShipment.setOnClickListener(v -> {
            // упрощённо, пока открываем тот же экран приёмки
            startActivity(new Intent(this, ReceiptActivity.class));
        });
        btnInventory.setOnClickListener(v -> {
            // заглушка
        });
        btnTransfer.setOnClickListener(v -> {
            // заглушка
        });
        btnLogout.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}