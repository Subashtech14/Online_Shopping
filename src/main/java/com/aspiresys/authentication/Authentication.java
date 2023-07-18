package com.aspiresys.authentication;

import com.aspiresys.Online_Shopping;
import com.aspiresys.Shopping;
import com.aspiresys.model.Seller;
import com.aspiresys.model.account.Account;
import com.aspiresys.model.account.AccountStatus;

import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    private static final ArrayList<Account> accounts = new ArrayList<>();

    public void defaultAccount() {
        accounts.add(new Account("admin", "admin", "admin", "admin", "admin", "admin"));
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

    public void siginUp() {
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

        ValidatorAccount validator = new ValidatorAccount(username, password, role, description, phoneNumber, email);
        if (validator.validate()) {
            accounts.add(new Account(username, password, role, description, phoneNumber, email));
            System.out.println("Account created successfully. Please log in.");
            login(); // Prompt the user to log in again after creating the account
        } else {
            System.out.println("Account creation failed. Please try again.");
            siginUp();
        }
    }

    public void logout() {
        new AccountStatus();
        System.out.println("You have been logged out from the account");
        Shopping.getStarted();
    }
}
