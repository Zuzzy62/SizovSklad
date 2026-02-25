package com.example.sizovsklad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sizovsklad.R;
import com.example.sizovsklad.adapters.ProductAdapter;
import com.example.sizovsklad.database.DatabaseHelper;
import com.example.sizovsklad.models.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ProductCatalogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProductCatalogActivity.this, AddEditProductActivity.class);
            startActivity(intent);
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadProducts() {
        List<Product> productList = dbHelper.getAllProducts();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts(); // обновляем список при возврате
    }
}