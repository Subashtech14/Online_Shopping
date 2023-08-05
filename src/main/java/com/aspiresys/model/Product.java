package com.aspiresys.model;

public class Product{
        String ProductName, Brand, Model, ProductDescription,Owner;
        int Price, Rating, No;
        public  Product(String ProductName, String Brand, String Model, String ProductDescription, int Price, int Rating, int No, String Owner) {

                this.ProductName = ProductName;
                this.Brand = Brand;
                this.Model = Model;
                this.ProductDescription = ProductDescription;
                this.Price = Price;
                this.Rating = Rating;
                this.No = No;
                this.Owner = Owner;
        }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductName='" + ProductName + '\'' +
                ", Brand='" + Brand + '\'' +
                ", Model='" + Model + '\'' +
                ", ProductDescription='" + ProductDescription + '\'' +
                ", Owner='" + Owner + '\'' +
                ", Price=" + Price +
                ", Rating=" + Rating +
                ", No=" + No +
                '}';
    }
}

