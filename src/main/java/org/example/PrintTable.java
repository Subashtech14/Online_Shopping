package org.example;

import java.util.ArrayList;

public class PrintTable {
    public void PrintItems(ArrayList<Product> products){
        for (Product product:products) {
            System.out.println("+--------------+--------------+");
            System.out.println("|   Field      |   Value      |");
            System.out.println("+--------------+--------------+");
            System.out.printf("| Product Name | %12s |\n", product.getProductName());
            System.out.printf("| Brand        | %12s |\n", product.getBrand());
            System.out.printf("| Model        | %12s |\n", product.getModel());
            System.out.printf("| Description  | %12s |\n", product.getProductDescription());
            System.out.printf("| Price        | %12d |\n", product.getPrice());
            System.out.printf("| Rating       | %12d |\n", product.getRating());
            System.out.printf("| Quantity     | %12d |\n", product.getNo());
            System.out.println("+--------------+--------------+");
        }

    }
}
