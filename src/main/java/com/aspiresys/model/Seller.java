package com.aspiresys.model;


import com.aspiresys.authentication.Authentication;
import com.aspiresys.controller.Shopping;
import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.view.PrintTable;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Seller {

    static ArrayList<Product> BuyedList =new ArrayList<>();
    String ProductName,Brand, Model, ProductDescription;
    int Price, Rating, No;
    static  ArrayList<Product> products=new ArrayList<>();
    private static final Logger logger1 = Logger.getLogger(Seller.class.getName());
    static {
        try {
            logger1.setUseParentHandlers(false);
            FileHandler fileHandler1 = new FileHandler("src/main/java/com/aspiresys/logs/product.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler1.setFormatter(formatter);
            logger1.addHandler(fileHandler1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void defaultProducts(){
        try {
            Properties properties = new Properties();
            FileReader fileReader = new FileReader("src/main/resources/product.properties");
            properties.load(fileReader);
            int productIndex = 1;
            while (properties.containsKey("product" + productIndex + ".name")) {
                String name = properties.getProperty("product" + productIndex + ".name");
                String brand = properties.getProperty("product" + productIndex + ".brand");
                String model = properties.getProperty("product" + productIndex + ".model");
                String description = properties.getProperty("product" + productIndex + ".description");
                int price = Integer.parseInt(properties.getProperty("product" + productIndex + ".price"));
                int rating = Integer.parseInt(properties.getProperty("product" + productIndex + ".rating"));
                int stock = Integer.parseInt(properties.getProperty("product" + productIndex + ".stock"));

                Product product = new Product(name, brand, model, description, price, rating, stock);
                products.add(product);

                productIndex++;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


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

         new PrintTable().printItems(products);
         if (!AccountStatus.AccountStatusNote.getStatus()){
             System.out.println("Account is not Logged in \n Please Login or Sign Up to Continue");
             Shopping.getStarted();
         }else{
            BuyProduct();
         }
    }

    public void BuyProduct() {
        if (!AccountStatus.AccountStatusNote.getStatus()){
            System.out.println("You are not Logged in \n Please Login to Buy the Product");
            new Authentication().login();
        }
        else {
        int chooseProduct;
        String option;
        Scanner scanner=new Scanner(System.in);
        do{
        System.out.println("Choose the Product Number to Buy the Product ");
        chooseProduct= scanner.nextInt();
        System.out.println("Choose the Quantity ");
        int quantity= scanner.nextInt();
        Product product=products.get(chooseProduct-1);
        product.setNo(quantity);
        BuyedList.add(product);
        System.out.println("Do you want to Continue Y or N ?");
        option=scanner.next();
        }while (option.equalsIgnoreCase("y"));
            logger1.info("The Products brought by the "+AccountStatus.AccountStatusNote.getUsername()+" \n "+BuyedList.toString());
        int total = 0;
            for (Product product:BuyedList){
                total=total+product.getPrice()*product.getNo();
            }
            System.out.println("Total Price "+total);

            logger1.info("User had Buyed Products of worth  "+total);
        new PrintTable(BuyedList).printItemsWithCheckout();



    }
    }
}
