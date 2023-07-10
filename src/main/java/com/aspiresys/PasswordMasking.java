package com.aspiresys;

import java.io.Console;
import java.util.Arrays;

public class PasswordMasking {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console available");
            System.exit(1);
        }

        char[] passwordArray = console.readPassword("Enter your password: ");
        String password = new String(passwordArray);

        // Mask the password
        Arrays.fill(passwordArray, '*');

        // Do something with the password...

        // Clear the password from memory
       // java.util.Arrays.fill(passwordArray, '');
    }
}
