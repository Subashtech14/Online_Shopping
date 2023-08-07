package com.aspiresys.controller;
/*
*The Admin Class has the Master Access Control to the system
* The Admin can create, remove, edit and view Users
* The Admin can create, remove, edit and view Products
*/
import com.aspiresys.authentication.ValidatorAccount;
import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.view.PrintUser;
import com.aspiresys.view.ProductTablePrinter;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.Paths.get;

public class Admin {

    private static final Logger logger = Logger.getLogger(Admin.class.getName());
    String ProductName, Brand, Model, ProductDescription;
    int Price, Rating, Number;
    private static final String productFilePath;
    private static final String userFilePath;
    static {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("E:\\Online_Shopping\\src\\main\\resources\\config.properties")) {
            properties.load(reader);
        } catch (IOException exception) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);

        }
         productFilePath = properties.getProperty("productFile");
         userFilePath = properties.getProperty("userFile");
    }
    public void adminAccess() {
        System.out.println("""
                1 -> Add User
                2 -> Remove User
                3 -> Update User
                4 -> View User
                5 -> Add Product
                6 -> Remove Product
                7 -> Edit Product
                8 -> View Product
                9 -> Log Out
                10 -> Exit
                """);
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> addUser();
            case 2 -> removeUser();
            case 3 -> editUser();
            case 4 -> viewUser();
            case 5 -> addProduct();
            case 6 -> removeProduct();
            case 7 -> editProduct();
            case 8 -> viewProduct();
            case 9 -> logOut();
            case 10 -> System.exit(0);
            default -> {
                System.out.println("Option Not Available");
                adminAccess();
            }
        }

    }

    public void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter the Password: ");
        String password = scanner.nextLine();
        System.out.println("Enter the Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter the Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter the Role (Seller/Buyer): ");
        String role = scanner.nextLine();
        System.out.println("Enter the Description: ");
        String description = scanner.nextLine();
        ValidatorAccount validator = new ValidatorAccount(password, role, phoneNumber, phoneNumber, email);
        if (validator.validate()) {
            String[] headers = {"Username", "Password", "Email", "PhoneNumber", "Role", "Description"};
            String[] userDetails1 = {username, password, email, phoneNumber, role, description};
            writeUserDetails(userFilePath, headers, userDetails1);
            System.out.println("Account created successfully. Please log in.");
        } else {
            System.out.println("Account creation failed. Please try again.");
            addUser();
        }
        adminAccess();
    }

    public void writeUserDetails(String filePath, String[] headers, String[] userDetails) {
        Path path = get(filePath);
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            if (headers != null && headers.length > 0 && Files.size(path) == 0) {
                csvPrinter.printRecord((Object[]) headers);
            }
            csvPrinter.printRecord((Object[]) userDetails);
            csvPrinter.flush();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public void removeUser() {
        System.out.println("Enter the UserName to delete: ");
        Scanner scanner = new Scanner(System.in);
        String usernameToDelete = scanner.nextLine();
        Path inputFilePath = Paths.get(userFilePath);
        Path outputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\user_details_temp.csv");
        try (Reader reader = Files.newBufferedReader(inputFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
             Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Username", "Password", "Email", "PhoneNumber", "Role", "Description"));
            for (CSVRecord record : csvParser) {
                String username = record.get("Username"); // Assuming 'Username' is the column containing usernames
                if (!username.equals(usernameToDelete)) {
                    csvPrinter.printRecord(record);
                }
            }
        } catch (IOException exception) {
            logger.info("Error in Removing User Account "+exception);
        }
        // Replace the original file with the updated one
        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
            System.out.println("User account '" + usernameToDelete + "' deleted successfully.");
        } catch (IOException exception) {
            logger.info("Error in Removing User Account "+exception);
        }
        adminAccess();
    }
    public void editUser() {
        System.out.println("""
                Enter the correct Username to Update that field
                Enter the all the fields you want to update
                """);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter the Password: ");
        String password = scanner.nextLine();
        System.out.println("Enter the Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter the Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter the Role (Seller/Buyer): ");
        String role = scanner.nextLine();
        System.out.println("Enter the Description: ");
        String description = scanner.nextLine();
        ValidatorAccount validator = new ValidatorAccount(password, role, phoneNumber, phoneNumber, email);
        if (validator.validate()) {
            updateUser(username, password, email, phoneNumber, role, description);
        } else {
            System.out.println("Not a Valid Input");
            editUser();
        }
        adminAccess();
    }

    public void viewUser() {
        new PrintUser().printUserTable();
        adminAccess();
    }

    public void addProduct() {
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
        try {
            boolean isFileEmpty = isFileEmpty(productFilePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(productFilePath, true));
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                if (isFileEmpty) {
                    csvPrinter.printRecord("name", "brand", "model", "description", "price", "rating", "stock", "Owner");
                }
                csvPrinter.printRecord(ProductName, Brand, Model, ProductDescription, Price, Rating, Number, AccountStatus.AccountStatusNote.getUsername());
                csvPrinter.flush();
                System.out.println("Data appended to CSV file successfully!");
            }
        } catch (IOException exception) {
            logger.info("Error in Writing User Details "+exception);
        }
        adminAccess();
    }

    public void removeProduct() {
        System.out.println("Enter the Product Name to delete: ");
        Scanner scanner = new Scanner(System.in);
        String productNameToDelete = scanner.nextLine();
        Path inputFilePath = Paths.get(productFilePath);
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
        } catch (IOException exception) {
            logger.info("Error in Removing Product Details "+exception);
        }

        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
            System.out.println("Product '" + productNameToDelete + "' deleted successfully.");
        } catch (IOException exception) {
            logger.info("Error in Removing Product Details "+exception);
        }
        adminAccess();
    }

    public void editProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Product Name to edit: ");
        String productNameToEdit = scanner.nextLine();
        Path inputFilePath = Paths.get(productFilePath);
        Path outputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\products_temp.csv");
        try (Reader reader = Files.newBufferedReader(inputFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
             Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ProductName", "Brand", "Model", "Description", "Price", "Rating", "Number"));
            for (CSVRecord record : csvParser) {
                String productName = record.get("name");
                if (productName.equals(productNameToEdit)) {
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
                    String numberUpdated = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter the Owner: ");
                    String Owner = scanner.nextLine();
                    csvPrinter.printRecord(
                            productNameUpdated,
                            brandUpdated,
                            modelUpdated,
                            productDescriptionUpdated,
                            priceUpdated,
                            ratingUpdated,
                            numberUpdated,
                            Owner
                    );
                } else {
                    csvPrinter.printRecord(record);
                }
            }
        } catch (IOException exception) {
            logger.info("Error in Writing Product Details "+exception);
        }
        // Replace the original file with the updated one
        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
            System.out.println("Product '" + productNameToEdit + "' edited successfully.");
        } catch (IOException exception) {
            logger.info("Error in Writing Product Details "+exception);
        }
        adminAccess();
    }

    public void viewProduct() {
        new ProductTablePrinter().printProductTable();
        adminAccess();
    }

    public void logOut() {
        logger.info("Account Logged out UserName " + AccountStatus.AccountStatusNote.getUsername());
        System.out.println("You have been logged out from the account");
        new AccountStatus();
        Shopping.getStarted();
    }

    public static boolean isFileEmpty(String filePath) {
        try {
            long fileSize = Files.size(Paths.get(filePath));
            System.out.println("File Size "+fileSize);
            return fileSize == 0;
        } catch (Exception exception) {
            logger.info("Checking the file is Empty "+exception);
            return false;
        }
    }

    public void updateUser(String usernameToEdit, String newPassword, String newEmail, String newPhoneNumber, String newRole, String newDescription) {
        Path inputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\user_details.csv");
        Path outputFilePath = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\user_details_temp.csv");
        try (Reader reader = Files.newBufferedReader(inputFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Username", "Password", "Email", "PhoneNumber", "Role", "Description"));
             Writer writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.CREATE)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Username", "Password", "Email", "PhoneNumber", "Role", "Description"));
            for (CSVRecord record : csvParser) {
                String username = record.get("Username");
                if (username.equals(usernameToEdit)) {
                    // Edit the desired fields
                    csvPrinter.printRecord(
                            usernameToEdit,
                            newPassword,
                            newEmail,
                            newPhoneNumber,
                            newRole,
                            newDescription
                    );
                } else {
                    csvPrinter.printRecord(record);
                }
            }
        } catch (IOException exception) {
            logger.info("Error in Writing User Details "+exception);
        }
        // Replace the original file with the updated one
        try {
            Files.delete(inputFilePath);
            Files.move(outputFilePath, inputFilePath);
        } catch (IOException exception) {
            logger.info("Error in Writing User Details "+exception);
        }
        adminAccess();
    }
}