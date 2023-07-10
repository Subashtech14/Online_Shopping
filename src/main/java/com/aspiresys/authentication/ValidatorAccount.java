package com.aspiresys.authentication;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorAccount {
    //Role Buyer Seller Admin
    private String Username,Password,Role,Description,Phone_Number,Email;
    static ArrayList<String> invalidNumbers = new ArrayList<>();
    static {
        invalidNumbers.add("1234567890"); // inValid
        invalidNumbers.add("9876543210"); // inValid
        invalidNumbers.add("123456789");  // Invalid
        invalidNumbers.add("abcdefghij");  // Invalid
        invalidNumbers.add("0987654321"); // inValid
    }

    public ValidatorAccount(String username, String password, String role, String description, String phone_Number, String email) {
        Username = username;
        Password = password;
        Role = role;
        Description = description;
        Phone_Number = phone_Number;
        Email=email;
    }
    public  boolean validate()  {
        boolean email=emailValid();
        boolean pass=passwordValid();
        boolean phone=phoneNumberValid();
        boolean role=roleValid();
        if(email && pass && phone && role){
//            System.out.println(email && pass && phone && role);
            return true;
        }
      return false;

    }




    private  boolean emailValid() {
        //Based on RFC 5322 For Email Validation
        String emailPattern="^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern=Pattern.compile(emailPattern);
        Matcher matcher=pattern.matcher(Email);
        if(matcher.matches()){
            return true;
        }
        System.out.println("Email not valid");
       return false;


    }

    private  boolean passwordValid() {
        String password="^[A-Za-z0-9]+@$";
        Pattern pattern=Pattern.compile(password);
        Matcher matcher=pattern.matcher(Password);
        if(matcher.matches()){
            return true;
        }
        System.out.println("Password not Strong");
       return false;
    }

    private  boolean phoneNumberValid() {
        if (Phone_Number.length() == 10 && Phone_Number.matches("[0-9]+")){
           for (String invalidNumber:invalidNumbers){
               if (Phone_Number.equals(invalidNumber)){
                   return false;
               }
           }
           return true;
        }
        System.out.println("Phone number not valid format");
       return false;
    }

    private  boolean roleValid() {
        if (Role.equals("Buyer") || Role.equals("Seller")  || Role.equals("Admin")){
            return true;
        }
        System.out.println("Role not Valid");
       return false;

    }
}
