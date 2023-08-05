package com.aspiresys;

import java.io.Console;

//In Order to do password masking, we need to call system console from the system itself
//For that we need to compile it with the following command [javac PasswordMasking.java]
//Then run it with the following command [java PasswordMasking]
//So that our application can call the system console
public class PasswordMasking {
        public void consoleFunc() {
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
            //console.printf("Password entered was: \n", new String(passwordArray));

        }

        public static void main(String[] args) {
            new PasswordMasking().consoleFunc();
        }

}
