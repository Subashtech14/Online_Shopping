package com.aspiresys;

import com.aspiresys.authentication.Authentication;
import com.aspiresys.model.Seller;
import com.aspiresys.model.account.AccountStatus;

import java.util.Scanner;

public class Shopping {
    public static void getStarted() {
        int Option;
        //System.out.println(AccountStatus.AccountStatusNote.getStatus());
        if (!AccountStatus.AccountStatusNote.getStatus()) {
            System.out.println("""
                    1 -> Login
                    2 -> SignUp """);
        }
        System.out.println(""" 
                3 -> View Product Details
                4 -> View and Buy the Products """);
        if (AccountStatus.AccountStatusNote.getStatus()) {
            System.out.println("5-> Logout");
        }
        Scanner scanner = new Scanner(System.in);
        Option = scanner.nextInt();
        switch (Option) {
            case 1 -> {
                if (new Authentication().login()) {
                    new Seller().gettingStartedSeller();
                } else {
                    System.out.println("Username or Password Incorrect");
                    new Authentication().login();
                }
            }
            case 2 -> new Authentication().siginUp();
            case 3 -> new Seller().viewOurProduct();
            case 4 -> new Seller().viewAndBuy();

            case 5 -> {
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
                    System.out.println(exception);
                    Shopping.getStarted();
                }

            }
        }
    }
}
