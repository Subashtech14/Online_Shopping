package com.aspiresys.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AdminTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testAddUser_ValidInput_Success() {
        // Prepare test input data
        String input = "JohnDoe\nSubash27@\nSubashtech14@gmail.com\n9976622224\nBuyer\nSample Description";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Create an instance of the Admin class
        Admin admin = new Admin();

        // Invoke the addUser method
        admin.addUser();

        // Check if the correct message is printed to the console
        Assertions.assertEquals("Account created successfully. Please log in.", getConsoleOutput());

        // Verify that the user details were written to the CSV file
        String csvFilePath = "E:\\Online_Shopping\\src\\main\\resources\\user_details.csv";
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(csvFilePath));
            // Assuming headers are included, there should be at least two lines (headers + user details)
            Assertions.assertTrue(lines.size() >= 2);
            String lastLine = lines.get(lines.size() - 1);
            // Verify that the user details are correctly written in the last line of the CSV file
            Assertions.assertTrue(lastLine.contains("JohnDoe") && lastLine.contains("john14@gmail.com"));
        } catch (Exception e) {
            Assertions.fail("Error reading the CSV file: " + e.getMessage());
        }
    }

    @Test
    public void testAddUser_InvalidInput_Failure() {
        // Prepare test input data with invalid email and password
        String input = "JohnDoe\nInvalidPassword\ninvalid-email\n1234567890\nBuyer\nSample Description";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Create an instance of the Admin class
        Admin admin = new Admin();

        // Invoke the addUser method
        admin.addUser();

        // Check if the correct failure message is printed to the console
        Assertions.assertEquals("Account creation failed. Please try again.", getConsoleOutput());
    }

    private String getConsoleOutput() {
        String fullContent = outContent.toString();
        System.out.println("Full Console Output:\n" + fullContent);
        return fullContent.trim();
    }
}
