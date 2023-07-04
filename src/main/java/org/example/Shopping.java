package org.example;

import org.example.accountDemo.AccountStatus;
import org.example.accountDemo.Authentication;

import java.util.Scanner;

public class Shopping {
    public static void main(String[] args) {

        getStarted();
    }

static {
    new Seller().defaultProducts();
    new Authentication().defaultAccount();
}

    public static void getStarted() {
        int Option;
        System.out.println(AccountStatus.AccountStatusNote.getStatus());
        if (!AccountStatus.AccountStatusNote.getStatus()){
            System.out.println("""
                1 -> Login
                2 -> SignUp """);
        }
        System.out.println(""" 
                3 -> View Product Details
                4 -> View and Buy the Products """);
        if ( AccountStatus.AccountStatusNote.getStatus()){
            System.out.println("5-> Logout");
        }
        Scanner scanner=new Scanner(System.in);
        Option=scanner.nextInt();
        switch (Option){
            case 1 -> new Authentication().login();
            case 2 -> new Authentication().siginUp();
            case 3 -> new Seller().viewOurProduct();
            case 4 -> new Seller().viewAndBuy();

            case 5 -> {
                if ( AccountStatus.AccountStatusNote.getStatus()){
                    new Authentication().logout();
                }
                else {
                    System.out.println("""
                        Selected is not a Valid Option
                        Please Select Another One
                        """);
                }
            }

            default -> {
                System.out.println("""
                        Selected is not a Valid Option
                        Please Select Another One
                        """);
                throw new IllegalArgumentException();

            }
        }
    }
}