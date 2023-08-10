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
    private static final Logger LOGGER = Logger.getLogger(Buyer.class.getName());
    private static final String PRODUCT_FILE_PATH;
    private static final String PRODUCT_LOG_PATH;

    static {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("E:\\Online_Shopping\\src\\main\\resources\\config.properties")) {
            properties.load(reader);
        } catch (IOException exception) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);
        }
        PRODUCT_FILE_PATH = properties.getProperty("productFile");
        PRODUCT_LOG_PATH=properties.getProperty("productLog");
        try{
            LOGGER.setUseParentHandlers(false);
            File file = new File(PRODUCT_LOG_PATH);
            boolean exists = file.exists();
            FileHandler fileHandler1 = new FileHandler(PRODUCT_LOG_PATH, exists);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler1.setFormatter(formatter);
            LOGGER.addHandler(fileHandler1);
        } catch (IOException exception) {
           LOGGER.info("Error in Authentication "+exception);
        }

        try {
            FileReader fileReader = new FileReader(PRODUCT_FILE_PATH);
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

        System.out.println("Choose the Product Number(S.No) to Buy the Product ");
        chooseProduct= scanner.nextInt();

        if (chooseProduct>products.size()){
            System.out.println("There is no Product available \n Please choose another Product");
            BuyProduct();
        }
        System.out.println("Choose the Quantity ");
        int quantity= scanner.nextInt();
        if (quantity>products.get(chooseProduct-1).getNo() || quantity<=0){
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
        System.out.println("Do you want to Continue Buying Y or N ?");
        option=scanner.next();
        }while (option.equalsIgnoreCase("y"));
            LOGGER.info("The Products brought by the "+AccountStatus.AccountStatusNote.getUserName()+" \n "+BuyedList.toString());
        int total = 0;
            for (Product product:BuyedList){
                total=total+product.getPrice()*product.getNo();
            }
            System.out.println("Total Price "+total);
            LOGGER.info("User had Buyed Products of worth  "+total);
        new PrintTable(BuyedList).printItemsWithCheckout();
        }
    }
}
