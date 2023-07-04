package org.example.accountDemo;

import org.example.Seller;
import org.example.Shopping;
import org.example.ValidatorAccount;

import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    static ArrayList<Account> account = new ArrayList<>();
    public void defaultAccount(){
        account.add(new Account("admin","admin","admin","admin","admin","admin"));
    }

    public  boolean login(){
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the UserName");
        String name = scanner.nextLine();
        System.out.println("Enter the Password");
        String password = scanner.nextLine();
        System.out.println(account);
       for (Account accounts:account){
           System.out.println("account status "+accounts);
           System.out.println(accounts.Username().equals(name) && accounts.Password().equals(password));
           if (accounts.Username().equals(name) && accounts.Password().equals(password)){

               System.out.println("Account Logged in Successfully");
               new AccountStatus(name,1);
               if (accounts.Role().equals("Seller") || accounts.Role().equals("admin")){
                   return true;
               }
               else {
                   new Seller().viewAndBuy();
               }

           }


       }
        return false;
    }

    public void siginUp() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the User Name ");
        String username = scanner.nextLine();
        System.out.println("Enter the Password ");
        String password1 = scanner.nextLine();
        System.out.println("Enter the Email ");
        String email = scanner.nextLine();
        System.out.println("Enter the Phone Number ");
        String phone_Number = scanner.nextLine();
        System.out.println("""
                Enter the Role 
                Seller
                Buyer""");
        String role = scanner.nextLine();
        System.out.println("Description ");
        String description = scanner.nextLine();
        ValidatorAccount validate=new ValidatorAccount(username, password1, role, description, phone_Number, email);
        if(validate.validate()){
            System.out.println("Account Added"+account.add(new Account(username, password1, role, description, phone_Number, email)));
            System.out.println("""
                    Account Created Successfully
                    Please Re-Login""");
            System.out.println(account);
            login();
        }
        System.out.println("Account is not Created");
        siginUp();
    }


    public void logout() {
        new AccountStatus();
        System.out.println("You have been logged out from the account");
        Shopping.getStarted();
    }
}
