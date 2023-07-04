package org.example;

import org.example.accountDemo.Account;

import java.util.ArrayList;

public class AccountStatic {
    static ArrayList<Account> account = new ArrayList<>();
    public static void main(String[] args) {
        account.add(new Account("admin","admin","admin","admin","admin","admin"));
        System.out.println(account);
    }
}
