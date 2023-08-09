package com.aspiresys.authentication;
/*
*This Authentication file is used to authenticate the user
* which has function for Login and SignUp and Logout Options
*/

import com.aspiresys.controller.Admin;
import com.aspiresys.controller.SellerPage;
import com.aspiresys.controller.Shopping;
import com.aspiresys.model.UserDetails;
import com.aspiresys.model.account.AccountStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.aspiresys.controller.Shopping.getStarted;
import static java.nio.file.Paths.get;
public class Authentication {
    private static final Logger LOGGER = Logger.getLogger(Authentication.class.getName());

    private static final String userFilePath;

    static {
        Properties properties = new Properties();
        try (Reader reader = new FileReader("E:\\Online_Shopping\\src\\main\\resources\\config.properties")) {
            properties.load(reader);
        } catch (IOException exception) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, "Can't Read Properties File", exception);
        }
        userFilePath = properties.getProperty("userFile");
    }


    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter the Password: ");
        String password = scanner.nextLine();
        Map<String, UserDetails> userDetailsMap = readUserDetailsFromCSV();
        if (userDetailsMap.containsKey(username)) {
            UserDetails userDetails = userDetailsMap.get(username);
            if (username.equals(userDetails.username()) && password.equals(userDetails.password())) {
                new AccountStatus(username, 1);
                String role = userDetails.role();
                System.out.println("Login Successful!");
                if (role.equalsIgnoreCase("Seller")) {
                    new SellerPage().sellerAccess();
                } else if (role.equalsIgnoreCase("admin")) {
                    new Admin().adminAccess();
                } else {
                    Shopping.getStarted();
                }
            }
            else {
                System.out.println("Login Failed. Please try again.");
                login();
            }
        }
        else {
            System.out.println("Login Failed. Please try again.");
            login();
        }


    }
    private static Map<String, UserDetails> readUserDetailsFromCSV() {
        Map<String, UserDetails> userDetailsMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(get(userFilePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                String username = csvRecord.get(0);
                String password = csvRecord.get(1);
                String role = csvRecord.get(4);
                UserDetails userDetails = new UserDetails(username,password, role);
                userDetailsMap.put(username, userDetails);
            }
        } catch (IOException e) {
            LOGGER.info("Error in Reading User Details"+e.getMessage());
        }

        return userDetailsMap;
    }


    public void signUp() {
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
        ValidatorAccount validator = new ValidatorAccount(username,password, role, phoneNumber, email);
        if (validator.validate()) {
            System.out.println("Account created successfully. Please log in.");
            String[] headers = {"Username", "Password", "Email", "PhoneNumber","Role","Description"};
            String[] userDetails1 = {username, password, email, phoneNumber,role,description};
            writeUserDetailsToCSV(headers, userDetails1);
            login();
        } else {
            System.out.println("Account creation failed. Please try again.");
            signUp();
        }
    }
    private static void writeUserDetailsToCSV(String[] headers, String[] userDetails) {
        Path path = get(Authentication.userFilePath);
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            if (headers != null && headers.length > 0 && Files.size(path) == 0) {
                csvPrinter.printRecord((Object[]) headers);
            }
            csvPrinter.printRecord((Object[]) userDetails);
            csvPrinter.flush();
        } catch (IOException exception) {
           LOGGER.info("Error in Writing User Details" +exception.getMessage());
        }
    }

    public void logout() {
        System.out.println("You have been logged out from the account");
        new AccountStatus();
        getStarted();
    }
}
