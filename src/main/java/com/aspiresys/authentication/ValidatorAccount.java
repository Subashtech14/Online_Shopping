package com.aspiresys.authentication;
/*
* The Validate Account Class is Used to Validate various user credentials
* E.g : PhoneNumber,Email,Password,Role
* */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorAccount {
    //Role Buyer Seller Admin
    private final String USER_NAME;
    private final String PASSWORD;
    private final String ROLE;
    private final String PHONE_NUMBER;
    private final String EMAIL;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{5,20}$";
    private static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

    static ArrayList<String> invalidNumbers = new ArrayList<>();
    static {
        invalidNumbers.add("1234567890"); // inValid
        invalidNumbers.add("9876543210"); // inValid
        invalidNumbers.add("123456789");  // Invalid
        invalidNumbers.add("abcdefghij");  // Invalid
        invalidNumbers.add("0987654321"); // inValid
    }

    public ValidatorAccount(String UserName, String password, String role, String phoneNumber, String email) {
        this.PASSWORD = password;
        this.ROLE = role;
        this.USER_NAME = UserName;
        this.PHONE_NUMBER = phoneNumber;
        this.EMAIL=email;
    }
    public  boolean validate()  {
        boolean email=emailValid();
        boolean pass=passwordValid();
        boolean phone=phoneNumberValid();
        boolean role=roleValid();
        boolean valid = isValidUsername(USER_NAME);
        return valid && email && pass && phone && role;

    }

        public static boolean isValidUsername(String username) {
            if (username == null || username.isEmpty()) {
                return false;
            }
            Matcher matcher = pattern.matcher(username);
            if (matcher.matches()) {
                return true;
            }
            System.out.println("Username not valid");
            return false;

    }


    private  boolean emailValid() {
        //Based on RFC 5322 For Email Validation
        String emailPattern="^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern=Pattern.compile(emailPattern);
        Matcher matcher=pattern.matcher(EMAIL);
        if(matcher.matches()){
            return true;
        }
        System.out.println("Email not valid");
        return false;


    }

    private  boolean passwordValid() {
        String password="^[A-Za-z0-9]+@$";
        Pattern pattern=Pattern.compile(password);
        Matcher matcher=pattern.matcher(PASSWORD);
        if(matcher.matches()){
            return true;
        }
        System.out.println("Password not Strong");
        return false;
    }

    private  boolean phoneNumberValid() {
        if (PHONE_NUMBER.length() == 10 && PHONE_NUMBER.matches("[0-9]+")){
           for (String invalidNumber:invalidNumbers){
               if (PHONE_NUMBER.equals(invalidNumber)){
                   return false;
               }
           }
           return true;
        }
        System.out.println("Phone number not valid format");
       return false;
    }

    private  boolean roleValid() {
        if (ROLE.equals("Buyer") || ROLE.equals("Seller")  || ROLE.equals("Admin")){
            return true;
        }
        System.out.println("Role not Valid");
       return false;

    }
}
