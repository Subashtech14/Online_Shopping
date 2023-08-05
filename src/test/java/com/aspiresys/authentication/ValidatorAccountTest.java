package com.aspiresys.authentication;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorAccountTest {

    @Test
    public void testValidAccount() {
        String userName = "user123";
        String password = "Abcd1234@";
        String role = "Buyer";
        String phoneNumber = "6549873210";
        String email = "user19@gmail.com";
        ValidatorAccount validator = new ValidatorAccount(userName, password, role, phoneNumber, email);
        boolean isValid = validator.validate();
        Assert.assertTrue(isValid);
    }

    @Test
    public void testInvalidUsername() {
        String userName = "user-123"; // Invalid username, contains a hyphen
        String password = "Abcd@1234";
        String role = "Buyer";
        String phoneNumber = "9876543210";
        String email = "user@example.com";

        ValidatorAccount validator = new ValidatorAccount(userName, password, role, phoneNumber, email);
        boolean isValid = validator.validate();
        Assert.assertFalse(isValid);
    }

    @Test
    public void testInvalidEmail() {
        String userName = "user_123";
        String password = "Abcd@1234";
        String role = "Buyer";
        String phoneNumber = "9876543210";
        String email = "userexample.com"; // Invalid email, missing @ symbol

        ValidatorAccount validator = new ValidatorAccount(userName, password, role, phoneNumber, email);
        boolean isValid = validator.validate();
        Assert.assertFalse(isValid);
    }

    @Test
    public void testInvalidPhoneNumber() {
        String userName = "user_123";
        String password = "Abcd@1234";
        String role = "Buyer";
        String phoneNumber = "12345"; // Invalid phone number, less than 10 digits
        String email = "user@example.com";

        ValidatorAccount validator = new ValidatorAccount(userName, password, role, phoneNumber, email);
        boolean isValid = validator.validate();
        Assert.assertFalse(isValid);
    }

    @Test
    public void testInvalidRole() {
        String userName = "user_123";
        String password = "Abcd@1234";
        String role = "InvalidRole"; // Invalid role
        String phoneNumber = "9876543210";
        String email = "user@example.com";

        ValidatorAccount validator = new ValidatorAccount(userName, password, role, phoneNumber, email);
        boolean isValid = validator.validate();
        Assert.assertFalse(isValid);
    }
}
