package com.aspiresys.view;

import com.aspiresys.model.Product;

import java.util.ArrayList;

public class PrintTable {

public void PrintItems(ArrayList<Product> products) {
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

}
//    public void PrintItems(ArrayList<Product> products){
//        System.out.println("+--------------+--------------+--------------+--------------+--------------+--------------+--------------+");
//        System.out.println("| Product Name |    Brand     |    Model     | Description  |    Price     |   Rating     |  Quantity    |");
//        System.out.println("+--------------+--------------+--------------+--------------+--------------+--------------+--------------+");
//
//        for (Product product : products) {
//
//            System.out.printf("| %12s | %12s | %12s | %12s | %12d | %12d | %12d |\n",
//                    product.getProductName(),
//                    product.getBrand(),
//                    product.getModel(),
//                    product.getProductDescription(),
//                    product.getPrice(),
//                    product.getRating(),
//                    product.getNo());
//            System.out.println("+--------------+--------------+--------------+--------------+--------------+--------------+--------------+");
//        }
//    }


