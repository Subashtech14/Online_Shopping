package org.example;

import java.io.Console;

public class PasswordMaskingExample {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        char[] passwordArray = console.readPassword("Enter your  password: ");
        for (int i = 0; i < passwordArray.length; i++) {
            System.out.print("*");
        }
        System.out.println();
        console.printf("Password entered was: \n", new String(passwordArray));

    }
}




