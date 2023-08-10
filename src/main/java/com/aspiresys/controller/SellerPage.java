package com.aspiresys.controller;
/*
* The Seller Page is used For the Seller
* The Seller Page is used For the Seller to Create and update and remove the products
* */

import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.view.PrintUserTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SellerPage {
    private static final Logger LOGGER = Logger.getLogger(Admin.class.getName());
    String productName, brand, model, productDescription;
    int price, rating, number;
    private static final String PRODUCT_FILE_PATH;
    private static final String EXCEPTION_FILE_PATH;
    private static final Logger EXCEPTIONS = Logger.getLogger(SellerPage.class.getName());

    static {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("E:\\Online_Shopping\\src\\main\\resources\\config.properties")) {
            properties.load(reader);

        } catch (IOException exception) {
            Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);
        }
        PRODUCT_FILE_PATH = properties.getProperty("productFile");
        EXCEPTION_FILE_PATH = properties.getProperty("exception");
        try{
            EXCEPTIONS.setUseParentHandlers(false);
            FileHandler fileHandler1 = new FileHandler(EXCEPTION_FILE_PATH);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler1.setFormatter(formatter);
            EXCEPTIONS.addHandler(fileHandler1);
        } catch (IOException e) {
            EXCEPTIONS.info("Error in Authentication");
        }


    }
    public void sellerAccess() {
        System.out.println("""
                1 -> Add Product
                2 -> Remove Product
                3 -> Edit Product
                4 -> View Product
                5 -> Log Out
                6 -> Exit
                """);
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> addProduct();
            case 2 -> removeProduct();
            case 3 -> editProduct();
            case 4 -> viewProduct();
            case 5 -> logOut();
            case 6 -> System.exit(0);
            default -> {
                System.out.println("Option Not Available");
                sellerAccess();
            }
        }
    }

    private void logOut() {
        LOGGER.info("Account Logged out UserName " + AccountStatus.AccountStatusNote.getUserName());
        System.out.println("You have been logged out from the account");
        new AccountStatus();
        Shopping.getStarted();
    }

    private void viewProduct() {
        new PrintUserTable().printTableBasedOnUser(PRODUCT_FILE_PATH, AccountStatus.AccountStatusNote.getUserName());
        sellerAccess();
    }
    private void editProduct() {
        String ownerToEdit = AccountStatus.AccountStatusNote.getUserName();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Product Name to edit: ");
        String productNameToEdit = scanner.nextLine();
        Path inputFilePath = Paths.get(PRODUCT_FILE_PATH);
        Path outputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\products_temp.csv");
        try (Reader reader = Files.newBufferedReader(inputFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
             Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("name", "brand", "model", "description", "price", "rating", "stock", "owner"));
            for (CSVRecord record : csvParser) {
                String productName = record.get("name");
                String owner = record.get("owner");
                if (productName.equals(productNameToEdit) && owner.equals(ownerToEdit)) {
                    System.out.println("Enter the Product Name");
                    String productNameUpdated = scanner.nextLine();
                    System.out.println("Enter the Product Brand");
                    String brandUpdated = scanner.nextLine();
                    System.out.println("Enter the Product Model");
                    String modelUpdated = scanner.nextLine();
                    System.out.println("Enter the Product Description");
                    String productDescriptionUpdated = scanner.nextLine();
                    System.out.println("Enter the Product Price in R.S ");
                    String priceUpdated = scanner.nextLine();
                    System.out.println("Enter the Rating");
                    String ratingUpdated = scanner.nextLine();
                    System.out.println("Enter the Quantity");
                    String stockUpdated = scanner.nextLine();
                    csvPrinter.printRecord(
                            productNameUpdated,
                            brandUpdated,
                            modelUpdated,
                            productDescriptionUpdated,
                            priceUpdated,
                            ratingUpdated,
                            stockUpdated,
                            ownerToEdit
                    );
                } else {
                    csvPrinter.printRecord(record);
                }
            }
            csvParser.close();
            csvPrinter.close();

        } catch (IOException e) {
            EXCEPTIONS.log(Level.SEVERE, "Error in Writing Product Details", e);
            return;
        }
        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
            System.out.println("Product '" + productNameToEdit + "' edited successfully.");
        } catch (IOException exception) {
            EXCEPTIONS.log(Level.SEVERE, "Error in Writing Product Details", exception);
        }
        sellerAccess();
    }
    public void removeProduct() {
        System.out.println("Enter the Product Name to delete: ");
        Scanner scanner = new Scanner(System.in);
        String productNameToDelete = scanner.nextLine();
        Path inputFilePath = Paths.get(PRODUCT_FILE_PATH);
        Path outputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\products_temp.csv");
        try (Reader reader = Files.newBufferedReader(inputFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
             Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("name", "brand", "model", "description", "price", "rating", "stock", "owner"));
            for (CSVRecord record : csvParser) {
                String productName = record.get("name");
                if (!productName.equals(productNameToDelete)) {
                    csvPrinter.printRecord(record);
                }
            }

            reader.close();
            csvParser.close();
            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException exception) {
            EXCEPTIONS.info("Error in Removing Product Details "+exception);
        }

        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
            System.out.println("Product '" + productNameToDelete + "' deleted successfully.");
        } catch (IOException exception) {
            EXCEPTIONS.info("Error in Removing Product File " + exception);
        }
        sellerAccess();
    }
    public void addProduct() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the Product Name");
    productName = scanner.nextLine();
    System.out.println("Enter the Product Brand");
    brand = scanner.nextLine();
    System.out.println("Enter the Product Model");
    model = scanner.nextLine();
    System.out.println("Enter the Product Description");
    productDescription = scanner.nextLine();
    System.out.println("Enter the Product Price in  R.S ");
    price = scanner.nextInt();
    System.out.println("Enter the Rating");
    rating = scanner.nextInt();
    System.out.println("Enter the Quantity");
    number = scanner.nextInt();
    scanner.nextLine();

    try {
        boolean isFileEmpty = isFileEmpty(PRODUCT_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE_PATH, true));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            System.out.println(isFileEmpty);
            if (isFileEmpty) {
                csvPrinter.printRecord("name", "brand", "model", "description", "price", "rating", "stock", "Owner");
            }

            csvPrinter.printRecord(productName, brand, model, productDescription, price, rating, number, AccountStatus.AccountStatusNote.getUserName());
            csvPrinter.flush();
            System.out.println("Data appended to CSV file successfully!");
        }
    } catch (IOException exception) {
        EXCEPTIONS.info("Error in Writing User Details "+exception);
    }
    sellerAccess();
}
    public static boolean isFileEmpty(String filePath) {
        try {
            long fileSize = Files.size(Paths.get(filePath));
            System.out.println("File Size "+fileSize);
            return fileSize == 0;
        } catch (Exception exception) {

            EXCEPTIONS.info("Checking the file is Empty "+exception);
            return false;
        }
    }
}