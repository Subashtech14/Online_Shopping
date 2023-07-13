package com.aspiresys.model;


import com.aspiresys.Shopping;
import com.aspiresys.view.PrintTable;
import com.aspiresys.Online_Shopping;
import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.authentication.Authentication;

import java.util.ArrayList;
import java.util.Scanner;

public class Seller {

    static ArrayList<Product> BuyedList =new ArrayList<>();
    String ProductName,Brand, Model, ProductDescription;
    int Price, Rating, No;
    static  ArrayList<Product> products=new ArrayList<>();
    public void defaultProducts(){
        products.add(new Product("Samsung Galaxy S20", "Samsung", "Galaxy S20", "Samsung Galaxy S20", 9999, 4, 5));
        products.add(new Product("IPhone 11", "Apple", "iPhone 11", "Apple iPhone 11", 9999, 4, 5));
        products.add(new Product("IPhone 12", "Apple", "iPhone 12", "Apple iPhone 12", 9999, 4, 5));
    }
    public void gettingStartedSeller(){
        int option;
        System.out.println("""
                1 -> View Product
                2 -> Add Your Own Product
               """);
        Scanner input=new Scanner(System.in);
        option=input.nextInt();
        switch (option){
            case 1 -> viewOurProduct();
            case 2 -> addProduct();
            default -> throw new IllegalArgumentException();
        }

    }
    public void viewOurProduct(){
        new PrintTable().printItems(products);
        Shopping.getStarted();
    }

    public void addProduct(){
        String option;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the Product Name");
        ProductName=scanner.nextLine();
        System.out.println("Enter the Product Brand");
        Brand=scanner.nextLine();
        System.out.println("Enter the Product Model");
        Model=scanner.nextLine();
        System.out.println("Enter the Product Description");
        ProductDescription=scanner.nextLine();
        System.out.println("Enter the Product Price in  R.S ");
        Price=scanner.nextInt();
        System.out.println("Enter the Rating");
        Rating=scanner.nextInt();
        System.out.println("Enter the Quantity");
        No=scanner.nextInt();
        scanner.nextLine();
        Product product=new Product(ProductName,Brand,Model,ProductDescription,Price,Rating,No);
        products.add(product);
        System.out.println("Do you want to add more ");
        option= scanner.nextLine();
        if(option.equalsIgnoreCase("y")){
            addProduct();
        }
        else {
            viewOurProduct();
        }

        Shopping.getStarted();
    }
    public void viewAndBuy(){
//         int i=0;
         new PrintTable().printItems(products);
         if (!AccountStatus.AccountStatusNote.getStatus()){
             System.out.println("Account is not Logged in \n Please Login or Sign Up to Continue");
             Shopping.getStarted();
         }else{
            BuyProduct();
         }
    }

    private void BuyProduct() {
        if (  AccountStatus.AccountStatusNote.getStatus()){
            System.out.println("You are not Logged in \n Please Login to Buy the Product");
            new Authentication().login();
        }
        else {
        int chooseProduct;
        String option;
        Scanner scanner=new Scanner(System.in);
        do{
        System.out.println("Choose the Product ");
        chooseProduct= scanner.nextInt();
        System.out.println("Choose the Quantity ");
        int quantity= scanner.nextInt();
        Product product=products.get(chooseProduct);
        product.setNo(quantity);
        BuyedList.add(product);
        System.out.println("Do you want to Continue?");
        option=scanner.next();
        }while (option.equalsIgnoreCase("y"));
        int total = 0;
        new PrintTable().printItems(BuyedList);
        for (Product product:BuyedList){
            total=total+product.getPrice();
        }
        System.out.println("Total Price "+total);
    }
    }
}
