package com.aspiresys.model;

public class Product{
        String productName, brand, model, productDescription,owner;
        int price, rating, no;
        public  Product(String ProductName, String Brand, String Model, String ProductDescription, int Price, int Rating, int No, String Owner) {

                this.productName = ProductName;
                this.brand = Brand;
                this.model = Model;
                this.productDescription = ProductDescription;
                this.price = Price;
                this.rating = Rating;
                this.no = No;
                this.owner = Owner;
        }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductName='" + productName + '\'' +
                ", Brand='" + brand + '\'' +
                ", Model='" + model + '\'' +
                ", ProductDescription='" + productDescription + '\'' +
                ", Owner='" + owner + '\'' +
                ", Price=" + price +
                ", Rating=" + rating +
                ", No=" + no +
                '}';
    }
}

