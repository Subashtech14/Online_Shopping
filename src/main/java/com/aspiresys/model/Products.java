package com.aspiresys.model;

public class Products {
    private String name;
    private String brand;
    private String model;
    private String description;
    private double price;
    private double rating;
    private int stock;
    private String owner;

    public Products(String name, String brand, String model, String description,
                   double price, double rating, int stock, String owner) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.stock = stock;
        this.owner = owner;
    }


}

