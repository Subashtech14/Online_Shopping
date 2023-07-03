package org.example;

public class Product{
        String ProductName, Brand, Model, ProductDescription;
        int Price, Rating, No;
        public Product(String ProductName, String Brand, String Model, String ProductDescription, int Price, int Rating, int No) {
            this.ProductName = ProductName;
            this.Brand = Brand;
            this.Model = Model;
            this.ProductDescription = ProductDescription;
            this.Price = Price;
            this.Rating = Rating;
            this.No = No;
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
        return "Product Name = " + ProductName  +
                "\nBrand = " + Brand  +
                "\nModel = " + Model  +
                "\nProduct Description = " + ProductDescription  +
                "\nPrice = " + Price +
                "\nRating = " + Rating +
                "\nQuantity = " + No ;
    }
}
