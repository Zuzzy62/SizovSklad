package com.example.sizovsklad.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sizovsklad.R;

public class ReceiptActivity extends AppCompatActivity {

    private EditText editQuantity;
    private Button btnSaveDraft;
    private TextView tvProductInfo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        tvProductInfo = findViewById(R.id.tvProductInfo);
        editQuantity = findViewById(R.id.editQuantity);
        btnSaveDraft = findViewById(R.id.btnSaveDraft);
        Button btnScan = findViewById(R.id.btnScan);
        Button btnBack = findViewById(R.id.btnBack);

        btnScan.setOnClickListener(v -> {
            // Имитация сканирования
            tvProductInfo.setText("Товар: Фильтр масляный (OEM-001)");
            btnSaveDraft.setEnabled(true);
        });

        btnSaveDraft.setOnClickListener(v -> {
            String qtyStr = editQuantity.getText().toString();
            if (qtyStr.isEmpty()) {
                Toast.makeText(this, "Введите количество", Toast.LENGTH_SHORT).show();
                return;
            }
            // Здесь сохранение черновика в БД
            Toast.makeText(this, "Черновик сохранён (ожидает подтверждения)", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnBack.setOnClickListener(v -> finish());
    }
}