package com.example.sizovsklad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sizovsklad.models.User;
import com.example.sizovsklad.models.Product;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sizov_sklad.db";
    private static final int DATABASE_VERSION = 1;

    // Таблица users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_FULLNAME = "fullname";
    public static final String COLUMN_USER_ROLE = "role";

    // Таблица products
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_ARTICLE = "article";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_BRAND = "brand";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
    public static final String COLUMN_PRODUCT_CELL = "cell";
    public static final String COLUMN_PRODUCT_BARCODE = "barcode";

    // SQL создания таблиц
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_FULLNAME + " TEXT,"
            + COLUMN_USER_ROLE + " TEXT)";

    private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCT_ARTICLE + " TEXT,"
            + COLUMN_PRODUCT_NAME + " TEXT,"
            + COLUMN_PRODUCT_BRAND + " TEXT,"
            + COLUMN_PRODUCT_PRICE + " REAL,"
            + COLUMN_PRODUCT_QUANTITY + " INTEGER,"
            + COLUMN_PRODUCT_CELL + " TEXT,"
            + COLUMN_PRODUCT_BARCODE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        // Добавляем тестовых пользователей
        insertTestUsers(db);
        insertTestProducts(db);
    }

    private void insertTestUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, "ivanov@sklad.ru");
        values.put(COLUMN_USER_PASSWORD, "123");
        values.put(COLUMN_USER_FULLNAME, "Иванов Иван (мл.клад)");
        values.put(COLUMN_USER_ROLE, "junior");
        db.insert(TABLE_USERS, null, values);

        values.clear();
        values.put(COLUMN_USER_EMAIL, "petrov@sklad.ru");
        values.put(COLUMN_USER_PASSWORD, "123");
        values.put(COLUMN_USER_FULLNAME, "Петров Пётр (ст.клад)");
        values.put(COLUMN_USER_ROLE, "senior");
        db.insert(TABLE_USERS, null, values);

        values.clear();
        values.put(COLUMN_USER_EMAIL, "sidorov@sklad.ru");
        values.put(COLUMN_USER_PASSWORD, "123");
        values.put(COLUMN_USER_FULLNAME, "Сидоров Сидор (менеджер)");
        values.put(COLUMN_USER_ROLE, "manager");
        db.insert(TABLE_USERS, null, values);

        values.clear();
        values.put(COLUMN_USER_EMAIL, "sizov@sklad.ru");
        values.put(COLUMN_USER_PASSWORD, "123");
        values.put(COLUMN_USER_FULLNAME, "Сизов А.Н.");
        values.put(COLUMN_USER_ROLE, "director");
        db.insert(TABLE_USERS, null, values);
    }

    private void insertTestProducts(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ARTICLE, "OEM-001");
        values.put(COLUMN_PRODUCT_NAME, "Фильтр масляный");
        values.put(COLUMN_PRODUCT_BRAND, "MANN");
        values.put(COLUMN_PRODUCT_PRICE, 350.0);
        values.put(COLUMN_PRODUCT_QUANTITY, 20);
        values.put(COLUMN_PRODUCT_CELL, "A1");
        values.put(COLUMN_PRODUCT_BARCODE, "123456789");
        db.insert(TABLE_PRODUCTS, null, values);

        values.clear();
        values.put(COLUMN_PRODUCT_ARTICLE, "OEM-002");
        values.put(COLUMN_PRODUCT_NAME, "Тормозные колодки");
        values.put(COLUMN_PRODUCT_BRAND, "BOSCH");
        values.put(COLUMN_PRODUCT_PRICE, 1200.0);
        values.put(COLUMN_PRODUCT_QUANTITY, 10);
        values.put(COLUMN_PRODUCT_CELL, "B2");
        values.put(COLUMN_PRODUCT_BARCODE, "987654321");
        db.insert(TABLE_PRODUCTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Метод для авторизации
    public User getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_FULLNAME, COLUMN_USER_ROLE};
        String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] args = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, args, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setFullName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FULLNAME)));
            user.setRole(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE)));
        }
        cursor.close();
        db.close();
        return user;
    }

    // CRUD для продуктов
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_ID)));
                p.setArticle(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ARTICLE)));
                p.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)));
                p.setBrand(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_BRAND)));
                p.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE)));
                p.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY)));
                p.setCell(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_CELL)));
                p.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_BARCODE)));
                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ARTICLE, product.getArticle());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_BRAND, product.getBrand());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRODUCT_CELL, product.getCell());
        values.put(COLUMN_PRODUCT_BARCODE, product.getBarcode());
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ARTICLE, product.getArticle());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_BRAND, product.getBrand());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_QUANTITY, product.getQuantity());
        values.put(COLUMN_PRODUCT_CELL, product.getCell());
        values.put(COLUMN_PRODUCT_BARCODE, product.getBarcode());
        return db.update(TABLE_PRODUCTS, values, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Метод для получения списка пользователей (для управления доступом)
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                User u = new User();
                u.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                u.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                u.setFullName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FULLNAME)));
                u.setRole(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE)));
                list.add(u);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int updateUserRole(int userId, String newRole) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ROLE, newRole);
        return db.update(TABLE_USERS, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)});
    }
}