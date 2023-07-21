package com.aspiresys.authentication;


import com.aspiresys.controller.Shopping;
import com.aspiresys.model.Seller;
import com.aspiresys.model.account.Account;
import com.aspiresys.model.account.AccountStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Authentication {
    private static final ArrayList<Account> accounts = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Authentication.class.getName());
    static {
        try {
            logger.setUseParentHandlers(false);
            FileHandler fileHandler = new FileHandler("src/main/java/com/aspiresys/logs/authentication.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void defaultAccount() {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/account.properties");
            properties.load(fileInputStream);
            String adminUsername = properties.getProperty("adminUsername");
            String adminPassword = properties.getProperty("adminPassword");
            String adminFirstName = properties.getProperty("adminFirstName");
            String adminLastName = properties.getProperty("adminLastName");
            String adminEmail = properties.getProperty("adminEmail");
            String adminRole = properties.getProperty("adminRole");
            Account adminAccount = new Account(adminUsername, adminPassword, adminFirstName, adminLastName, adminEmail, adminRole);
            accounts.add(adminAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter the Password: ");
        String password = scanner.nextLine();

        for (Account account : accounts) {
            if (account.Username().equals(username) && account.Password().equals(password)) {
                System.out.println("Account logged in successfully");
                new AccountStatus(username, 1);
                logger.info("Account Logged in UserName "+account.Username());
                if (account.Role().equals("Seller") || account.Role().equals("admin")) {
                    new Seller().gettingStartedSeller();
                    return true;
                } else {
                    Shopping.getStarted();
                }
            }
        }

        return false;
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

        ValidatorAccount validator = new ValidatorAccount(password, role, phoneNumber, email);
        if (validator.validate()) {
            accounts.add(new Account(username, password, role, description, phoneNumber, email));
            System.out.println("Account created successfully. Please log in.");
            login();
        } else {
            System.out.println("Account creation failed. Please try again.");
            signUp();
        }
    }

    public void logout() {
        logger.info("Account Logged out UserName "+AccountStatus.AccountStatusNote.getUsername());

        System.out.println("You have been logged out from the account");
        new AccountStatus();
        Shopping.getStarted();
    }
}
