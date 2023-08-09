package com.aspiresys.controller;
/*
*It is the Starting Point of the application
*/

import com.aspiresys.authentication.Authentication;
import com.aspiresys.model.account.AccountStatus;
import com.aspiresys.view.ProductTablePrinter;

import java.util.Scanner;

public class Shopping {
    public static void getStarted() {
        System.out.println("""
                 ╔════════════════════════════════════════════════════════════════════════════════════════════╗
                 ║                                      Shopping System                                       ║
                 ╠════════════════════════════════════════════════════════════════════════════════════════════╣ \s
                """);
        int Option;
        if (!AccountStatus.AccountStatusNote.getStatus()) {
            new ProductTablePrinter().printProductTable();
            System.out.println("""
                    1 -> Login
                    2 -> SignUp\s""");

        }
        System.out.println("""
                3 -> Buy Products\s""");
        if (AccountStatus.AccountStatusNote.getStatus()) {
            System.out.println("4-> Logout");
        }
        Scanner scanner = new Scanner(System.in);
        Option = scanner.nextInt();
        switch (Option) {
            case 1 -> new Authentication().login();
            case 2 -> new Authentication().signUp();
            case 3 -> new Buyer().viewAndBuy();
            case 4 -> {
                if (AccountStatus.AccountStatusNote.getStatus()) {
                    new Authentication().logout();
                } else {
                    new Authentication().login();
                }
            }

            default -> {
                try {
                    System.out.println("""
                            Selected is not a Valid Option
                            Please Select Another One
                            """);
                    throw new IllegalArgumentException();
                } catch (IllegalArgumentException exception) {
                    Shopping.getStarted();
                }

            }
        }
    }
}
