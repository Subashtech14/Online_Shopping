package com.aspiresys.view;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintUserTable {
    public void printTableBasedOnUser(String filePath, String ownerToView) {

        List<CSVRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            // Read all CSV records into a list during the first pass
            for (CSVRecord record : parser) {
                records.add(record);
            }

            // Calculate optimal column widths
            int[] columnWidths = new int[7];
            for (CSVRecord record : records) {
                String[] fields = {record.get("name"), record.get("brand"), record.get("model"),
                        record.get("description"), record.get("price"), record.get("rating"), record.get("stock")};

                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].length() > columnWidths[i]) {
                        columnWidths[i] = fields[i].length();
                    }
                }
            }

            // Calculate table width and set margin
            int tableWidth = columnWidths.length + 1;
            for (int width : columnWidths) {
                tableWidth += width + 3;
            }
            int margin = 2;
            printSeparator(tableWidth + 2 * margin);
            printTableRow(columnWidths, "Name", "Brand", "Model", "Description", "Price", "Rating", "Stock");
            printSeparator(tableWidth + 2 * margin);

            for (CSVRecord record : records) {
                String owner = record.get("owner");
                if (owner.equals(ownerToView)) {

                    printTableRow(columnWidths,
                            record.get("name"), record.get("brand"), record.get("model"),
                            record.get("description"), record.get("price"), record.get("rating"),
                            record.get("stock"));
                    printSeparator(tableWidth + 2 * margin);
                }
            }
        } catch (IOException e) {
            Logger.getLogger(PrintUserTable.class.getName()).log(Level.SEVERE, "Error in Printing User Details", e);
        }
    }

    private static void printTableRow(int[] columnWidths, String... fields) {
        System.out.print("| ");
        for (int i = 0; i < fields.length; i++) {
            if (i == 4 || i == 5 || i == 6) {
                System.out.printf("%" + columnWidths[i] + "s | ", fields[i]);
            } else {
                System.out.printf("%-" + columnWidths[i] + "s | ", fields[i]);
            }
        }
        System.out.println();
    }

    private static void printSeparator(int length) {
        System.out.print("+");
        for (int i = 0; i < length - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
