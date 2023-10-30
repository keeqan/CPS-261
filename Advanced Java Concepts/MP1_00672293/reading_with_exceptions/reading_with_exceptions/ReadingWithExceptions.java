package reading_with_exceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadingWithExceptions {

    public void process(String inputFilename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                System.out.println("The file is empty");
                return;
            }
            
            String[] parts = firstLine.split(" ");
            if (parts.length < 2) {
                System.out.println("The first line of the file should contain an output filename and a number");
                return;
            }

            String outputFilename = parts[0];
            int numberToRead;
            try {
                numberToRead = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("The second part of the first line should be a number");
                return;
            }

            if (numberToRead < 0) {
                System.out.println("The number to read should be non-negative");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
                int count = 0;
                String line;
                while ((line = reader.readLine()) != null && count < numberToRead) {
                    String[] numbers = line.split(" ");
                    for (String numberStr : numbers) {
                        try {
                            int number = Integer.parseInt(numberStr);
                            writer.write(number + " ");
                            if (++count % 10 == 0) {
                                writer.newLine();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping bad input: " + numberStr);
                        }
                    }
                }

                if (count < numberToRead) {
                    System.out.println("Not enough input numbers. Expected " + numberToRead + ", but got " + count);
                }
            } catch (IOException e) {
                System.out.println("Error writing to output file: " + e.getMessage());
            }

            System.out.println("Output file " + outputFilename + " created");
            
            // Print the content of the output file
            try (BufferedReader outputReader = new BufferedReader(new FileReader(outputFilename))) {
                String outputLine;
                while ((outputLine = outputReader.readLine()) != null) {
                    System.out.println(outputLine);
                }
            } catch (IOException e) {
                System.out.println("Error reading from output file: " + e.getMessage());
            }
            
        } catch (IOException e) {
            System.out.println("Error reading from input file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ReadingWithExceptions <input filename>");
            return;
        }

        ReadingWithExceptions reader = new ReadingWithExceptions();
        reader.process(args[0]);
    }
}