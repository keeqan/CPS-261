package mp4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

//  bad indentation
class BadIndentationException extends RuntimeException {
    BadIndentationException(String error) {
        super(error);
    }
}

public class IndentChecker {
    // Stack to keep indentation levels
    private Stack<Integer> indentStack = new Stack<>();
    // Output results to a file
    private PrintWriter outputWriter;

    // initializes the output writer
    public IndentChecker() {
        try {
            outputWriter = new PrintWriter("IndentationOutput.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Error creating output file: " + e.getMessage());
            System.exit(1);
        }
    }

    // first non-blank character in a line
    private int findFirstNonBlank(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (!Character.isWhitespace(line.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    // check its indentation
    private void processLine(String line, int lineNumber) {
        int index = findFirstNonBlank(line);

        if (index == -1) return; // Skip blank lines

        // Check indentation and update
        if (indentStack.isEmpty() || index > indentStack.peek()) {
            indentStack.push(index);
            return;
        }

        //  dedentation
        while (!indentStack.isEmpty() && indentStack.peek() > index) {
            indentStack.pop();
        }

        //  bad indentation
        if (indentStack.isEmpty() || indentStack.peek() != index) {
            throw new BadIndentationException("Bad indentation at line #" + lineNumber);
        }
    }

    //  check the indentation of a file
    public void checkIndentation(String fileName) {
        indentStack.clear();
        Scanner input = null;

        try {
            input = new Scanner(new File(fileName));
            int lineNumber = 1;
            // Process each line 
            while (input.hasNextLine()) {
                String line = input.nextLine();
                outputWriter.println(lineNumber + ": " + line);
                processLine(line, lineNumber++);
            }
            outputWriter.println("****** " + fileName + " is properly indented.");
        } catch (BadIndentationException error) {
            // bad indentation exception
            outputWriter.println(error.getMessage() + " in file: " + fileName);
        } catch (FileNotFoundException e) {
            //  file not found exception
            outputWriter.println("Can't open file: " + fileName);
        } finally {
            // Close scanner 
            if (input != null) input.close();
        }
    }

    //  process command-line arguments
    public static void main(String[] args) {
        IndentChecker indentChecker = new IndentChecker();
        // Process each file 
        for (String arg : args) {
            indentChecker.outputWriter.println("Processing file: " + arg);
            indentChecker.checkIndentation(arg);
        }
        // Close the output
        indentChecker.outputWriter.close();
    }
}