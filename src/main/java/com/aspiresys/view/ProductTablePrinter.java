package com.aspiresys.view;
//"E:\\Online_Shopping\\src\\main\\resources\\products.csv"

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductTablePrinter {
    private static final Logger LOGGER = Logger.getLogger(ProductTablePrinter.class.getName());
    public  void printProductTable() {
        Path path = Paths.get("E:\\Online_Shopping\\src\\main\\resources\\products.csv");

        try (Reader reader = Files.newBufferedReader(path);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            System.out.println("Products Table:");

            CSVRecord headerRecord = csvParser.iterator().next();
            int numColumns = headerRecord.size();
            int[] columnWidths = new int[numColumns];
            for (int i = 0; i < numColumns; i++) {
                columnWidths[i] = headerRecord.get(i).length();
            }
            for (CSVRecord record : csvParser) {
                for (int i = 0; i < numColumns; i++) {
                    columnWidths[i] = Math.max(columnWidths[i], record.get(i).length());
                }
            }
            int totalTableWidth = Arrays.stream(columnWidths).sum() + (numColumns * 3) + 1;
            printHorizontalLine(totalTableWidth);
            printHeader(headerRecord, columnWidths);
            printHorizontalLine(totalTableWidth);
            try (Reader readerForRows = Files.newBufferedReader(path);
                 CSVParser csvParserForRows = new CSVParser(readerForRows, CSVFormat.DEFAULT)) {
                csvParserForRows.iterator().next();
                for (CSVRecord record : csvParserForRows) {
                    printRow(record, columnWidths);
                }

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error reading products.csv file");
            }
            printHorizontalLine(totalTableWidth);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading products.csv file");
        }
    }

    private static void printHeader(CSVRecord headerRecord, int[] columnWidths) {
        for (int i = 0; i < headerRecord.size(); i++) {
            System.out.print("| ");
            printCentered(headerRecord.get(i), columnWidths[i]);
            System.out.print(" ");
        }
        System.out.println("|");
    }

    private static void printHorizontalLine(int width) {
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private static void printRow(CSVRecord record, int[] columnWidths) {
        System.out.print("| ");
        for (int i = 0; i < record.size(); i++) {
            printCentered(record.get(i), columnWidths[i]);
            System.out.print(" | ");
        }
        System.out.println();
    }

    private static void printCentered(String value, int width) {
        int padding = width - value.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;
        for (int i = 0; i < leftPadding; i++) {
            System.out.print(" ");
        }
        System.out.print(value);
        for (int i = 0; i < rightPadding; i++) {
            System.out.print(" ");
        }
    }
}
