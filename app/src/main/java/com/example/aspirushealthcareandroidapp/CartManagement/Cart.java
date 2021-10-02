package com.example.aspirushealthcareandroidapp.CartManagement;

public class Cart {

    private String ItemKey, productName, image;
    private double price;
    private int quantity;

    public Cart() {
    }

    public Cart(String itemKey, String productName, Double price, int quantity, String image) {
        ItemKey = itemKey;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemKey() {

        return ItemKey;
    }

    public void setItemKey(String itemKey) {

        ItemKey = itemKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Cart(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
