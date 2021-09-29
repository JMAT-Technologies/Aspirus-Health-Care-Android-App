package com.example.aspirushealthcareandroidapp.PharmacyManagement;

public class ProductModel
{
    private String productName,price;
    private String image;

    public ProductModel(String productName, String price, String image) {
        this.productName = productName;
        this.price = price;
        this.image = image;
    }

    public ProductModel() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
