package com.example.sizovsklad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sizovsklad.R;
import com.example.sizovsklad.database.DatabaseHelper;
import com.example.sizovsklad.models.User;
import com.example.sizovsklad.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private TextView tvError;
    private DatabaseHelper dbHelper;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        session = new SessionManager(this);

        // Если уже залогинен, переходим сразу
        if (session.isLoggedIn()) {
            redirectToRole(session.getUser().getRole());
            return;
        }

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        tvError = findViewById(R.id.tvError);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            tvError.setText("Заполните все поля");
            tvError.setVisibility(View.VISIBLE);
            return;
        }

        User user = dbHelper.getUser(email, password);
        if (user == null) {
            tvError.setText("Неверный email или пароль");
            tvError.setVisibility(View.VISIBLE);
            editPassword.setText("");
            return;
        }

        session.saveUser(user);
        redirectToRole(user.getRole());
    }

    private void redirectToRole(String role) {
        Intent intent;
        switch (role) {
            case "junior":
                intent = new Intent(this, JuniorWorkerActivity.class);
                break;
            case "senior":
                intent = new Intent(this, SeniorWorkerActivity.class);
                break;
            case "manager":
                intent = new Intent(this, ManagerActivity.class);
                break;
            case "director":
                intent = new Intent(this, DirectorActivity.class);
                break;
            default:
                intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}