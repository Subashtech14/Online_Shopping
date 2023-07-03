package org.example.account;

import org.example.Seller;
import org.example.Shopping;
import org.example.ValidatorAccount;
import org.example.account.Account;

import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    static ArrayList<Account> account =new ArrayList<Account>();
    public void defaultAccount(){
        account.add(new Account("admin","admin","admin","admin","admin","admin"));
    }

    public  void login(){
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the UserName");
        String name = scanner.nextLine();
        System.out.println("Enter the Password");
        String password = scanner.nextLine();
       for (Account a:account){
           if (a.Username().equals(name) && a.Password().equals(password)){
               System.out.println("Account Logged in Successfully");
               new AccountStatus(name,1);
               if (a.Role().equals("Seller") || a.Role().equals("admin")){
                   new Seller().gettingStartedSeller();
               }
               else {
                   new Seller().viewAndBuy();
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
        String username = input.nextLine();
        System.out.println("Enter the Password ");
        String password1 = input.nextLine();
        System.out.println("Enter the Email ");
        String email = input.nextLine();
        System.out.println("Enter the Phone Number ");
        String phone_Number = input.nextLine();
        System.out.println("""
                Enter the Role 
                Seller
                Buyer""");
        String role = input.nextLine();
        System.out.println("Description ");
        String description = input.nextLine();
        ValidatorAccount v=new ValidatorAccount(username, password1, role, description, phone_Number, email);
        if(v.validate()){
            Account a=new Account(username, password1, role, description, phone_Number, email);
            account.add(a);
            System.out.println("""
                    Account Created Successfully
                    Please Re-Login""");
            login();
        }
    }


    public void logout() {
        new AccountStatus();
        System.out.println("You have been logged out from the account");
        Shopping.getStarted();
    }
}
