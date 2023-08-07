package com.aspiresys.controller;
/*
*The Buyer class will be able to view the products, add the product to the cart and buy the product
* */

import com.aspiresys.authentication.Authentication;
import com.aspiresys.model.Product;
import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.view.PrintTable;
import com.aspiresys.view.ProductTablePrinter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Buyer {
    static ArrayList<Product> BuyedList = new ArrayList<>();
    static  ArrayList<Product> products=new ArrayList<>();
    private static final Logger logger1 = Logger.getLogger(Buyer.class.getName());
    private static final String productFilePath;
    private static final String productLog;

    static {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("E:\\Online_Shopping\\src\\main\\resources\\config.properties")) {
            properties.load(reader);
        } catch (IOException exception) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);
        }
        productFilePath = properties.getProperty("productFile");
        productLog=properties.getProperty("productLog");
        try{
            logger1.setUseParentHandlers(false);
            FileHandler fileHandler1 = new FileHandler(productLog);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler1.setFormatter(formatter);
            logger1.addHandler(fileHandler1);
        } catch (IOException exception) {
           logger1.info("Error in Authentication "+exception);
        }

        try {
            FileReader fileReader = new FileReader(productFilePath);
            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);
            for (CSVRecord record : parser) {
                String name = record.get("name");
                String brand = record.get("brand");
                String model = record.get("model");
                String description = record.get("description");
                double price = Double.parseDouble(record.get("price"));
                double rating = Double.parseDouble(record.get("rating"));
                int stock = Integer.parseInt(record.get("stock"));
                String owner = record.get("owner");
                Product product = new Product(name, brand, model, description, (int) price, (int) rating, stock, owner);
                products.add(product);
            }
            fileReader.close();
        } catch (IOException exception) {
           Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);
        }
    }

    public void viewOurProduct(){
        new ProductTablePrinter().printProductTable();
        Shopping.getStarted();
    }
    private static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return file.length() == 0;
    }
    public void viewAndBuy(){
        //new ProductTablePrinter().printProductTable();
        new PrintTable(products).printItems();
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
        if (quantity>products.get(chooseProduct-1).getNo()){
            System.out.println("Product is Out of Stock \nThe Product availability is "+products.get(chooseProduct-1).getNo());
            System.out.println("Do you want to Continue Buying Y or N ?");
            String options=scanner.next();
            quantity=products.get(chooseProduct-1).getNo();
            if (!options.equalsIgnoreCase("y")){
                BuyProduct();
            }
        }
        Product product=products.get(chooseProduct-1);
        product.setNo(quantity);
        BuyedList.add(product);
        System.out.println("Do you want to add the Product to the Cart Y or N ?");
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
