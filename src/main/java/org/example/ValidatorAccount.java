package org.example;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorAccount {
    //Role Buyer Seller Admin
    private String Username,Password,Role,Description,Phone_Number,Email;


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
        if (Phone_Number.length() ==10){
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
