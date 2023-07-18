package com.aspiresys.view;

import com.aspiresys.model.Product;
import com.aspiresys.model.Seller;

import java.util.ArrayList;
import java.util.Scanner;

public class PrintTable {
    static ArrayList<Product> products=new ArrayList<>();
    public PrintTable(){}
    public PrintTable(ArrayList<Product> products){
        PrintTable.products =products;
    }
public void printItems() {
    int cellWidth = 18; // Adjust the cell width as needed
    int tableWidth = (cellWidth + 3) * 7; // Calculate table width based on cell width and number of columns

    // Print table--top border
    System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

    // Print table header
    System.out.printf("| %1$-" + cellWidth + "s | %2$-" + cellWidth + "s | %3$-" + cellWidth + "s | %4$-" + cellWidth + "s | %5$-" + cellWidth + "s | %6$-" + cellWidth + "s | %7$-" + cellWidth + "s |\n",
            "Product Name", "Brand", "Model", "Description", "Price", "Rating", "Quantity");

    // Print table mid border
    System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

    // Print table rows
    for (Product product : products) {
        System.out.printf("| %1$-" + cellWidth + "s | %2$-" + cellWidth + "s | %3$-" + cellWidth + "s | %4$-" + cellWidth + "s | %5$-" + cellWidth + "d | %6$-" + cellWidth + "d | %7$-" + cellWidth + "d |\n",
                product.getProductName(),
                product.getBrand(),
                product.getModel(),
                product.getProductDescription(),
                product.getPrice(),
                product.getRating(),
                product.getNo());

        // Print table row separator
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");
    }
}
public void printItemsWithCheckout() {
        int cellWidth = 18; // Adjust the cell width as needed
        int tableWidth = (cellWidth + 3) * 7; // Calculate table width based on cell width and number of columns

        // Print table--top border
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

        // Print table header
        System.out.printf("| %1$-" + cellWidth + "s | %2$-" + cellWidth + "s | %3$-" + cellWidth + "s | %4$-" + cellWidth + "s | %5$-" + cellWidth + "s | %6$-" + cellWidth + "s | %7$-" + cellWidth + "s |\n",
                "Product Name", "Brand", "Model", "Description", "Price", "Rating", "Quantity");

        // Print table mid border
        System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

        // Print table rows
        for (Product product : products) {
            System.out.printf("| %1$-" + cellWidth + "s | %2$-" + cellWidth + "s | %3$-" + cellWidth + "s | %4$-" + cellWidth + "s | %5$-" + cellWidth + "d | %6$-" + cellWidth + "d | %7$-" + cellWidth + "d |\n",
                    product.getProductName(),
                    product.getBrand(),
                    product.getModel(),
                    product.getProductDescription(),
                    product.getPrice(),
                    product.getRating(),
                    product.getNo());

            // Print table row separator
            System.out.println("+" + "-".repeat(tableWidth - 2) + "+");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to checkout Y or N ?");
        String option=scanner.nextLine();
        if (option.equalsIgnoreCase("y")){
            checkOut();
        }
        else {
            new Seller().BuyProduct();
        }

    }

    public void checkOut() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a payment option: 1 or 2");
        System.out.println("1. Credit Card");
        System.out.println("2. Cash on Delivery");

        int paymentOption = scanner.nextInt();

        if (paymentOption == 1) {
            payCreditCard(products);
        } else if (paymentOption == 2) {
            payCashOnDelivery(products);
        } else {
            System.out.println("Invalid payment option.");
        }
    }

    private void payCashOnDelivery(ArrayList<Product> products) {
        int total = 0;
        for (Product product:products){
            total=total+product.getPrice();
        }
        System.out.println("Please pay the cash on delivery of cash "+total);
        System.exit(0);
    }

    public void payCreditCard(ArrayList<Product> product){
        int total = 0;
        for (Product products:products){
            total=total+products.getPrice();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your credit card number: ");
        String creditCardNumber = scanner.next();
        System.out.println("Enter your credit card Pin number: ");
        String creditPinNumber = scanner.next();
        if (isValidCreditCard(creditCardNumber,creditPinNumber)) {
                try{
                    for (int i=0;i<=100;i=i+10){
                        Thread.sleep(1000);
                        System.out.println("Processing Payment... +"+i+"%");
                    }
                System.out.println("Purchase successful! ");

                }
                catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

        } else {
            System.out.println("Invalid credit card number. Payment failed.");
        }

    }

    private boolean isValidCreditCard(String creditCardNumber , String creditPinNumber) {
        return creditCardNumber.length() == 10 && creditCardNumber.matches("[0-9]+") && creditPinNumber.length() == 4 && creditPinNumber.matches("[0-9]+");
    }
}



