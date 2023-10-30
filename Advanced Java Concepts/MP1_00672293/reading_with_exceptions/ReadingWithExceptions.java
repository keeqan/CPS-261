package reading_with_exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadingWithExceptions {

    public void process(String inputFilename) {

        // Opens Input File
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {

            // Read the First line
            String firstLine = reader.readLine();

            // Check if the file is empty
            if (firstLine == null) {
                System.out.println("This file is empty");
                return;
            }
            
            // Split first lint into parts
            String[] parts = firstLine.split(" ");

            // Check if the first line has filename
            if (parts.length < 2) {
                System.out.println("Needs output filename and a number");
                return;
            }

            // Extract the output filename and the number of lines to read
            String outputFilename = parts[0];
            int numberToRead;
            try {
                
                // Try to parse the number from the first line
                numberToRead = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {

                // Handle the case where the number if valid
                System.out.println("Second part should be a number");
                return;
            }

            // Check non-negative
            if (numberToRead < 0) {
                System.out.println("The number should be non-negative");
                return;
            }

            // Try to write to output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
                int count = 0;
                String line;

                // Read the file 
                while ((line = reader.readLine()) != null && count < numberToRead) {
                    String[] numbers = line.split(" ");
                    for (String numberStr : numbers) {
                        try {

                            // Try to parse each number and write it to the output file
                            int number = Integer.parseInt(numberStr);
                            writer.write(number + " ");

                            // Add a new line every 
                            if (++count % 10 == 0) {
                                writer.newLine();
                            }
                        } catch (NumberFormatException e) {

                            // not valid number
                            System.out.println("Skipping bad input: " + numberStr);
                        }
                    }
                }

                // Check if we have required reading 
                if (count < numberToRead) {
                    System.out.println("Not enough input numbers. Expected: " + numberToRead );
                }
            } catch (IOException e) {

                // output error handling
                System.out.println("Error writing to output file: " + e.getMessage());
            }

            System.out.println("Output file " + outputFilename + " was created");
            
            // Print output file
            try (BufferedReader outputReader = new BufferedReader(new FileReader(outputFilename))) {
                String outputLine;
                while ((outputLine = outputReader.readLine()) != null) {
                    System.out.println(outputLine);
                }
            } catch (IOException e) {
                
                // Error reading from the output file
                System.out.println("Error reading from output file: " + e.getMessage());
            }
            
        } catch (IOException e) {

            // Reading from the input file
            System.out.println("Error reading from input file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // Check the input filename
        if (args.length < 1) {
            System.out.println("Please provide the input filename");
            return;
        }

        // Create ReadingWithExceptions
        ReadingWithExceptions reader = new ReadingWithExceptions();
        reader.process(args[0]);
    }
}