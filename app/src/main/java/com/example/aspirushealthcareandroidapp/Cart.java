package com.example.aspirushealthcareandroidapp;

public class Cart {

    private String ItemKey, productName, price, quantity, discount, image;

    public Cart() {
    }

    public Cart(String itemKey, String productName, String price, String quantity, String discount, String image) {
        ItemKey = itemKey;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
