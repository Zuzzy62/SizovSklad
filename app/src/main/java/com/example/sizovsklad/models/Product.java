package com.example.sizovsklad.models;

public class Product {
    private int id;
    private String article;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String cell;
    private String barcode;

    // геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getArticle() { return article; }
    public void setArticle(String article) { this.article = article; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getCell() { return cell; }
    public void setCell(String cell) { this.cell = cell; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
}