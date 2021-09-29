package com.example.aspirushealthcareandroidapp.PharmacyManagement;

public class AdminPharmacyModel {
    String productName,price,image,description;

    AdminPharmacyModel(){

    }

    public AdminPharmacyModel(String productName, String price, String image, String description) {
        this.productName = productName;
        this.price = price;
        this.image = image;
        this.description = description;


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
