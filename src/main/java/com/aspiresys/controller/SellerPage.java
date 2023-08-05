package com.aspiresys.controller;
/*
* The Seller Page is used For the Seller
* The Seller Page is used For the Seller to Create and update and remove the products
* */

import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.view.PrintUserTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import static com.aspiresys.controller.Admin.isFileEmpty;
public class SellerPage {
    private static final Logger logger = Logger.getLogger(Admin.class.getName());
    String ProductName, Brand, Model, ProductDescription;
    int Price, Rating, Number;
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
        logger.info("Account Logged out UserName " + AccountStatus.AccountStatusNote.getUsername());
        System.out.println("You have been logged out from the account");
        new AccountStatus();
        Shopping.getStarted();
    }

    private void viewProduct() {
        String ownerToView = AccountStatus.AccountStatusNote.getUsername();
        new PrintUserTable().printTableBasedOnUser(productFilePath, ownerToView);
        sellerAccess();
    }


    private void editProduct() {
        new PrintUserTable().printTableBasedOnUser(productFilePath, AccountStatus.AccountStatusNote.getUsername());
        String ownerToEdit = AccountStatus.AccountStatusNote.getUsername();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Product Name to edit: ");
        String productNameToEdit = scanner.nextLine();

        Path inputFilePath = Paths.get(productFilePath);
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

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in Writing Product Details", e);
            return;
        }
        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
            System.out.println("Product '" + productNameToEdit + "' edited successfully.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in Writing Product Details", e);
        }
        sellerAccess();
    }
    private void removeProduct() {
        new PrintUserTable().printTableBasedOnUser(productFilePath, AccountStatus.AccountStatusNote.getUsername());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Product Name to remove: ");
        String productNameToRemove = scanner.nextLine();

        Path inputFilePath = Paths.get(productFilePath);
        Path outputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\products_temp.csv");

        List<CSVRecord> recordsToRemove = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(inputFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
             Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE)) {

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("name", "brand", "model", "description", "price", "rating", "stock", "owner"));

            for (CSVRecord record : csvParser) {
                String productName = record.get("name");
                String owner = record.get("owner");

                if (productName.equals(productNameToRemove) && owner.equals(AccountStatus.AccountStatusNote.getUsername())) {
                    recordsToRemove.add(record);
                } else {
                    // Write the record to the new CSV file if it doesn't match the product to be removed
                    csvPrinter.printRecord(record);
                }
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in Reading/Writing Product Details", e);
            return;
        }

        // Check if any records were found and removed
        if (recordsToRemove.isEmpty()) {
            System.out.println("Product not found or you don't have permission to remove this product.");
            return;
        }

        System.out.println("Product removed successfully!");

        // Replace the original file with the updated one
        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in Writing Product Details", e);
        }
        sellerAccess();
    }
        


    private void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Product Name");
        ProductName = scanner.nextLine();
        System.out.println("Enter the Product Brand");
        Brand = scanner.nextLine();
        System.out.println("Enter the Product Model");
        Model = scanner.nextLine();
        System.out.println("Enter the Product Description");
        ProductDescription = scanner.nextLine();
        System.out.println("Enter the Product Price in  R.S ");
        Price = scanner.nextInt();
        System.out.println("Enter the Rating");
        Rating = scanner.nextInt();
        System.out.println("Enter the Quantity");
        Number = scanner.nextInt();
        scanner.nextLine();
        String csvFilePath = "E:\\Online_Shopping\\src\\main\\resources\\products.csv";
        try {
            boolean isFileEmpty = isFileEmpty(csvFilePath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true));
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

                // If the file is empty, print the header
                if (!isFileEmpty) {
                    csvPrinter.printRecord("name", "brand", "model", "description", "price", "rating", "stock", "Owner");
                }

                csvPrinter.printRecord(ProductName, Brand, Model, ProductDescription, Price, Rating, Number, AccountStatus.AccountStatusNote.getUsername());
                csvPrinter.flush();
                System.out.println("Data appended to CSV file successfully!");
            }
        } catch (IOException e) {
            logger.info("Error in Writing User Details");
        }
        sellerAccess();
    }
}