package com.example.sizovsklad.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sizovsklad.R;

public class ConfirmOperationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_operations);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Здесь можно вывести список черновиков из БД
        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            Toast.makeText(this, "Операция подтверждена (демо)", Toast.LENGTH_SHORT).show();
        });
    }
}