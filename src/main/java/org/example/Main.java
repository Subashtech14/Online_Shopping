package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        getStarted();
    }

    public static void getStarted() {
        int Option;
        System.out.println("""
                1 -> Login
                2 -> SignUp
                3 -> View Product Details
                4 -> View and Buy the Products
                """);
        Scanner scanner=new Scanner(System.in);
        Option=scanner.nextInt();
        switch (Option){
            case 1 -> new Authentication().login();
            case 2 -> new Authentication().siginUp();
            case 3 -> new Seller().viewOurProduct();
            case 4 -> new Seller().viewAndBuy();

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