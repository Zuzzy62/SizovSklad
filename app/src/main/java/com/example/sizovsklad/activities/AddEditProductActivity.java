package com.example.sizovsklad.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sizovsklad.R;
import com.example.sizovsklad.database.DatabaseHelper;
import com.example.sizovsklad.models.Product;

public class AddEditProductActivity extends AppCompatActivity {

    private EditText etArticle, etName, etBrand, etPrice, etQuantity, etCell, etBarcode;
    private DatabaseHelper dbHelper;
    private Product product; // если редактирование, то не null

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        dbHelper = new DatabaseHelper(this);

        etArticle = findViewById(R.id.etArticle);
        etName = findViewById(R.id.etName);
        etBrand = findViewById(R.id.etBrand);
        etPrice = findViewById(R.id.etPrice);
        etQuantity = findViewById(R.id.etQuantity);
        etCell = findViewById(R.id.etCell);
        etBarcode = findViewById(R.id.etBarcode);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        // Если передан id товара для редактирования
        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            // загрузить товар из БД по id (опустим для краткости)
            // и заполнить поля
        }

        btnSave.setOnClickListener(v -> saveProduct());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveProduct() {
        String article = etArticle.getText().toString().trim();
        String name = etName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show();
            return;
        }

        Product p = new Product();
        p.setArticle(article);
        p.setName(name);
        p.setBrand(etBrand.getText().toString().trim());
        try {
            p.setPrice(Double.parseDouble(etPrice.getText().toString()));
        } catch (NumberFormatException e) {
            p.setPrice(0);
        }
        try {
            p.setQuantity(Integer.parseInt(etQuantity.getText().toString()));
        } catch (NumberFormatException e) {
            p.setQuantity(0);
        }
        p.setCell(etCell.getText().toString().trim());
        p.setBarcode(etBarcode.getText().toString().trim());

        if (product == null) {
            dbHelper.addProduct(p);
            Toast.makeText(this, "Товар добавлен", Toast.LENGTH_SHORT).show();
        } else {
            p.setId(product.getId());
            dbHelper.updateProduct(p);
            Toast.makeText(this, "Товар обновлён", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}