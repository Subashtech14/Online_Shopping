package com.aspiresys.view;

import com.aspiresys.controller.Shopping;
import com.aspiresys.model.Product;
import com.aspiresys.controller.Buyer;

import java.util.ArrayList;
import java.util.Scanner;

public class PrintTable {
    static ArrayList<Product> products=new ArrayList<>();

    public PrintTable(ArrayList<Product> products){
        PrintTable.products =products;

    }
    public void printItems() {
        int cellWidth = 18; // Adjust the cell width as needed
        int tableWidth = (cellWidth + 3) * 8; // Calculate table width based on cell width and number of columns

        // Print table--top border
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

        // Print table header
        System.out.printf("| %5s | %-" + (cellWidth + 5) + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s |\n",
                "S.No", "Product Name", "Brand", "Model", "Description", "Price", "Rating", "Quantity");

        // Print table mid border
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

        // Print table rows
        int serialNumber = 1;
        for (Product product : products) {
            System.out.printf("| %5d | %-" + (cellWidth + 5) + "." + (cellWidth + 5) + "s | %-" + cellWidth + "." + cellWidth + "s | %-" + cellWidth + "." + cellWidth + "s | %-" + cellWidth + "." + cellWidth + "s | %-" + cellWidth + "d | %-" + cellWidth + "d | %-" + cellWidth + "d |\n",
                    serialNumber,
                    product.getProductName(),
                    product.getBrand(),
                    product.getModel(),
                    product.getProductDescription(),
                    product.getPrice(),
                    product.getRating(),
                    product.getNo()
            );

            // Print table row separator
            System.out.println("+" + "-".repeat(tableWidth - 2) + "+");
            serialNumber++;
        }
    }


    public void printItemsWithCheckout() {
        int cellWidth = 18; // Adjust the cell width as needed
        int tableWidth = (cellWidth + 3) * 8; // Calculate table width based on cell width and number of columns

        // Print table--top border
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

        // Print table header
        System.out.printf("| %5s | %-" + (cellWidth + 5) + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s | %-" + cellWidth + "s |\n",
                "S.No", "Product Name", "Brand", "Model", "Description", "Price", "Rating", "Quantity");

        // Print table mid border
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

        // Print table rows
        int serialNumber = 1;
        for (Product product : products) {
            System.out.printf("| %5d | %-" + (cellWidth + 5) + "." + (cellWidth + 5) + "s | %-" + cellWidth + "." + cellWidth + "s | %-" + cellWidth + "." + cellWidth + "s | %-" + cellWidth + "." + cellWidth + "s | %-" + cellWidth + "d | %-" + cellWidth + "d | %-" + cellWidth + "d |\n",
                    serialNumber,
                    product.getProductName(),
                    product.getBrand(),
                    product.getModel(),
                    product.getProductDescription(),
                    product.getPrice(),
                    product.getRating(),
                    product.getNo()
            );

            // Print table row separator
            System.out.println("+" + "-".repeat(tableWidth - 2) + "+");
            serialNumber++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to checkout Y or N ?");
        String option = scanner.nextLine();
        if (option.equalsIgnoreCase("y")) {
            checkOut();
        } else {
            new Buyer().BuyProduct();
        }
    }


    public void checkOut() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a payment option: 1 or 2");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash on Delivery");
        int paymentOption = scanner.nextInt();
        if (paymentOption == 1) {
            payCreditCard();
        } else if (paymentOption == 2) {
            payCashOnDelivery(products);
        } else {
            System.out.println("Invalid payment option.");
            checkOut();
        }
    }

    private void payCashOnDelivery(ArrayList<Product> products) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Address: ");
        String address= scanner.nextLine();
        int total = 0;
        for (Product product:products){
            total=total+product.getPrice()*product.getNo();
        }
        System.out.println("Please pay the cash on delivery of cash "+total);
        System.out.println("Your Order Will be delivered at "+address);
        Shopping.getStarted();
    }

    public void payCreditCard(){
        int total = 0;
        for (Product products:products){
            total=total+products.getPrice()*products.getNo();
        }
        System.out.println("Please pay the credit card of cash "+total);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your credit card number: ");
        String creditCardNumber = scanner.next();
        System.out.println("Enter your credit card Pin number: ");
        String creditPinNumber = scanner.next();
        scanner.nextLine();
        System.out.println("Enter your Address: ");
        String Address= scanner.nextLine();
        if (isValidCreditCard(creditCardNumber,creditPinNumber)) {
                try{
                    for (int i=0;i<=100;i=i+10){
                        Thread.sleep(1000);
                        System.out.println("Processing Payment... +"+i+"%");
                    }
                System.out.println("Purchase successful! ");
                    System.out.println("Your Order Will be delivered at "+Address);
                Shopping.getStarted();
                }
                catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

        } else {
            System.out.println("Invalid credit card number. Payment failed.");
            payCreditCard();
        }

    }

    private boolean isValidCreditCard(String creditCardNumber , String creditPinNumber) {
        return creditCardNumber.length() == 10 && creditCardNumber.matches("[0-9]+") && creditPinNumber.length() == 4 && creditPinNumber.matches("[0-9]+");
    }
}



