package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    static ArrayList<Account> account =new ArrayList<Account>();

    private String Username,Password,Role,Description,Phone_Number,Email;
    private String name,password;
    public  void login(){
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the UserName");
        name=sc.nextLine();
        System.out.println("Enter the Password");
        password=sc.nextLine();
       for (Account a:account){
           if (a.Username().equals(name) && a.Password().equals(password)){
               System.out.println("Account Logged in Successfully");
               if (a.Role().equals("Seller")){
                   new Seller().gettingStartedSeller();
               }

           }
           else {
               System.out.println("Username or Password Incorrect ");
               login();
           }
       }
    }

    public void siginUp() {

        Scanner input=new Scanner(System.in);

        System.out.println("Enter the User Name ");
        Username=input.nextLine();
        System.out.println("Enter the Password ");
        Password=input.nextLine();
        System.out.println("Enter the Email ");
        Email=input.nextLine();
        System.out.println("Enter the Phone Number ");
        Phone_Number=input.nextLine();
        System.out.println("""
                Enter the Role 
                Seller
                Buyer""");
        Role=input.nextLine();
        System.out.println("Description ");
        Description=input.nextLine();
        ValidatorAccount v=new ValidatorAccount(Username,Password,Role,Description,Phone_Number,Email);
        if(v.validate()){
            Account a=new Account(Username,Password,Role,Description,Phone_Number,Email);
            account.add(a);
            System.out.println("""
                    Account Created Successfully
                    Please Re-Login""");
            login();
        }
    }


}
