package org.example;


import java.util.ArrayList;
import java.util.Scanner;

public class Seller {
    static ArrayList<Product> Buyed =new ArrayList<>();
    String ProductName,Brand, Model, ProductDescription;
    int Price, Rating, No;
    static  ArrayList<Product> products=new ArrayList<>();
    public void gettingStartedSeller(){
        int op;
        System.out.println("""
                1 -> View Product
                2 -> Add Your Own Product
               """);
        Scanner inp=new Scanner(System.in);
        op=inp.nextInt();
        switch (op){
            case 1 -> viewOurProduct();
            case 2 -> addProduct();
            default -> throw new IllegalArgumentException();
        }

    }
    public void viewOurProduct(){
    for (Product p:products){
        System.out.println(p.toString());
    }
    }
    public void addProduct(){
        String op;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the Product Name");
        ProductName=scanner.nextLine();
        System.out.println("Enter the Product Brand");
        Brand=scanner.nextLine();
        System.out.println("Enter the Product Model");
        Model=scanner.nextLine();
        System.out.println("Enter the Product Description");
        ProductDescription=scanner.nextLine();
        System.out.println("Enter the Product Price in  R.S ");
        Price=scanner.nextInt();
        System.out.println("Enter the Rating");
        Rating=scanner.nextInt();
        System.out.println("Enter the Quantity");
        No=scanner.nextInt();
        scanner.nextLine();
        Product product=new Product(ProductName,Brand,Model,ProductDescription,Price,Rating,No);
        products.add(product);
        System.out.println("Do you want to add more ");
        op= scanner.nextLine();
        if(op.equalsIgnoreCase("y")){
            addProduct();
        }
        else {
            viewOurProduct();
        }

        Main.getStarted();
    }
    public void viewAndBuy(){
         int i=0;
        for (Product p:products){
            System.out.println(i+" -> " +p.toString());
            i++;
        }
        BuyProduct();
    }

    private void BuyProduct() {
        int l;
        String op;
        Scanner sc=new Scanner(System.in);
        do{
        System.out.println("Choose the Product ");
        l= sc.nextInt();
        Buyed.add(products.get(l));
        System.out.println("Do you want to Continue?");
        op=sc.next();
        }while (op.equalsIgnoreCase("y"));
        int total = 0;
        for (Product p:Buyed){
            total=total+p.Price();
        }
        System.out.println("Total Price "+total);
    }
}
