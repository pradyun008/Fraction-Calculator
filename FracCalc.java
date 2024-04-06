// Pradyun Pinapala
// Period 5
// Fraction Calculator Project

import java.util.*;

// TODO: Description of what this program does goes here.
public class FracCalc {

    // It is best if we have only one console object for input
    public static Scanner console = new Scanner(System.in);

    // This main method will loop through user input and then call the
    // correct method to execute the user's request for help, test, or
    // the mathematical operation on fractions. or, quit.
    // DO NOT CHANGE THIS METHOD!!
    public static void main(String[] args) {

        // initialize to false so that we start our loop
        boolean done = false;

        // When the user types in "quit", we are done.
        while (!done) {
            // prompt the user for input
            String input = getInput();

            // special case the "quit" command
            if (input.equalsIgnoreCase("quit")) {
                done = true;
            } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
                // We allowed the UnitTestRunner to handle the command first.
                // If the UnitTestRunner didn't handle the command, process normally.
                String result = processCommand(input);

                // print the result of processing the command
                System.out.println(result);
            }
        }

        System.out.println("Goodbye!");
        console.close();
    }

    // Prompt the user with a simple, "Enter: " and get the line of input.
    // Return the full line that the user typed in.
    public static String getInput() {
        // TODO: Implement this method
        System.out.print("Enter: ");
        String input = console.nextLine();
        return input;
    }

    // processCommand will process every user command except for "quit".
    // It will return the String that should be printed to the console.
    // This method won't print anything.
    // DO NOT CHANGE THIS METHOD!!!
    public static String processCommand(String input) {

        if (input.equalsIgnoreCase("help")) {
            return provideHelp();
        }

        // if the command is not "help", it should be an expression.
        // Of course, this is only if the user is being nice.
        return processExpression(input);
    }

   
   /**
    * Processes a mathematical expression input by the user.
    * The expression can include operations on fractions and mixed numbers.
    * The method splits the input into individual components (operands and operators)
    * and processes them sequentially to compute the result.
    *
    * input The mathematical expression input by the user.
    * return The result of the mathematical expression as a String.
    */
   public static String processExpression(String input) {
       // Split the input expression into components (operands and operators)
       String[] expression = input.split(" ", -1);
       String first = expression[0]; // The first operand in the expression

       // Loop through the expression processing each operation
       for (int numOfOP = 0; numOfOP < expression.length / 2; numOfOP++) {
           // Combine operand and operator for processing
           first = otherprocessExpresion(first + " " + expression[numOfOP * 2 + 1] + " " + expression[numOfOP * 2 + 2]);
       }
       return first; // Return the final result of the expression
   }

   /**
    * Processes an individual operation within the mathematical expression.
    * This includes normalizing the operands, performing the operation,
    * and then simplifying the result.
    *
    *  input A part of the mathematical expression, including two operands and an operator.
    * return The result of the operation as a String.
    */
   public static String otherprocessExpresion(String input) {
       Scanner parser = new Scanner(input); // Scanner to parse the input
       String number1 = parser.next(); // First operand
       String operation = parser.next(); // Operator
       String number2 = parser.next(); // Second operand

       // Check for division by zero scenario
       if (zerochecker(number1) || zerochecker(number2)) {
           return ("Error, Cannot divide by zero"); // Return error if division by zero occurs
       }

       // Normalize, process, and simplify the numbers
       String normnumber1 = normalization(number1); // Normalize first operand
       String normnumber2 = normalization(number2); // Normalize second operand
       String mixednumebr1 = mixed(normnumber1); // Convert to mixed number if necessary
       String mixednumebr2 = mixed(normnumber2); // Convert to mixed number if necessary
       String finalnumber = normalization(operator(operation, mixednumebr1, mixednumebr2)); // Perform the operation and normalize result
       return simplify(finalnumber); // Simplify and return the final result
   }


  
    /**
     * Checks if the fraction has a denominator of zero, which is not allowed in arithmetic operations.
     * 
     * fraction1 The fraction string to check, in the format "numerator/denominator" or "whole_numerator/denominator".
     * return Boolean true if the denominator is zero, otherwise false.
     */
    public static Boolean zerochecker(String fraction1) {
        String denominator = deno(fraction1); // Extract the denominator from the fraction
        // Return true if the denominator is zero, indicating an invalid fraction
        return denominator.equals("0");
    }

    /**
     * Determines and performs the appropriate arithmetic operation based on the given operator.
     * Supports four basic arithmetic operations: addition, subtraction, multiplication, and division.
     * 
     *  operator The arithmetic operator as a string (e.g., "+", "-", "*", "/").
     *  num1 The first number (operand) in the operation, in fraction format.
     * num2 The second number (operand) in the operation, in fraction format.
     * return The result of the arithmetic operation as a string.
     */
    public static String operator(String operator, String num1, String num2) {
        if (operator.equals("+")) {
            return add(num1, num2); // Perform addition
        } else if (operator.equals("-")) {
            return subtract(num1, num2); // Perform subtraction
        } else if (operator.equals("*")) {
            return multiply(num1, num2); // Perform multiplication
        } else if (operator.equals("/")) {
            return divide(num1, num2); // Perform division
        } else {
            return "Invalid operator"; // Return error message for an invalid operator
        }
    }

    /**
     * Extracts the denominator part from a given fraction or whole number.
     * If the input is a whole number, it returns "1" as the implicit denominator.
     * 
     *  num2 The fraction or whole number in string format.
     * return The denominator as a string.
     */
    public static String deno(String num2) {
        if (num2.contains("/")) {
            // Fraction case: Extract and return the denominator part
            String d = num2.substring(num2.indexOf("/") + 1);
            return d;
        } else {
            // Whole number case: The implicit denominator is 1
            if(num2.equals("0")){
                return "1";
            }
            return num2;
        }
    }


   /**
    * Extracts the numerator from a given fraction string.
    * Handles both simple fractions (e.g., "1/2") and mixed numbers (e.g., "1_1/2").
    *
    *  num2 The fraction or mixed number string.
    *  return The numerator as a string.
    */
   public static String nume(String num2) {
       if (num2.contains("/") && num2.contains("_")) {
           // For mixed numbers: extract the numerator part of the fraction
           return num2.substring(num2.indexOf("_") + 1, num2.indexOf("/"));
       } else if (num2.contains("/")) {
           // For simple fractions: extract the numerator part
           return num2.substring(0, num2.indexOf("/"));
       } else {
           // If there's no fraction part, return the whole number as numerator
           return num2;
       }
   }

   /**
    * Extracts the whole number part from a mixed number string.
    * If the input is a simple fraction or a whole number, it handles those cases accordingly.
    *
    *  num2 The mixed number, fraction, or whole number string.
    * return The whole number part as a string.
    */
   public static String whole(String num2) {
       if (num2.contains("_")) {
           // For mixed numbers: extract the whole number part
           return num2.substring(0, num2.indexOf("_"));
       } else if (num2.contains("/")) {
           // For simple fractions: there is no whole number part, return "0"
           return "0";
       } else {
           // For whole numbers: return the number itself
           return num2;
       }
   }

   /**
    * Normalizes a fraction or mixed number string.
    * This includes adjusting the signs of the numerator and denominator and handling whole numbers.
    *
    *  num The fraction or mixed number string to be normalized.
    * return The normalized fraction or mixed number string.
    */
   public static String normalization(String num) {
       if (num.contains("/")) {
           int n = Integer.parseInt(nume(num)); // Extract and parse the numerator
           int y = Integer.parseInt(deno(num)); // Extract and parse the denominator

           // Adjust the signs for the numerator and denominator
           if (n < 0 && y < 0 || n > 0 && y < 0) {
               n = -n;
               y = -y;
           }

           // Handle mixed number formatting
           if (num.contains("_")) {
               return whole(num) + "_" + n + "/" + y; // Combine whole number with normalized fraction
           } else {
               return n + "/" + y; // Return normalized fraction
           }
       } else {
           // For whole numbers, convert to a fraction with denominator 1
           return num + "/1";
       }
   }


   /**
    * Calculates the Greatest Common Factor (GCF) of two integers.
    * This method uses the Euclidean algorithm, a well-known method for computing the greatest common divisor of two numbers.
    *
    *  a The first integer.
    *  b The second integer.
    * return The greatest common factor of a and b.
    */
   public static int gcf(int a, int b) {
       a = Math.abs(a); // Ensure a is positive, as GCF is always positive
       b = Math.abs(b); // Ensure b is positive

       // Apply the Euclidean algorithm to find GCF
       while (b != 0) {
           int temp = b;      // Temporary variable to hold the value of b
           b = a % b;         // Replace b with the remainder of a divided by b
           a = temp;          // Replace a with the original value of b
       }

       return a; // When b becomes 0, a contains the GCF
   }


  /**
   * Simplifies a fraction or mixed number to its simplest form.
   * The method handles fractions where the numerator is greater, less, or equal to the denominator.
   * It also simplifies fractions with numerators of zero and converts mixed numbers to improper fractions if necessary.
   *
   *  num The fraction or mixed number in string format.
   * return The simplified fraction as a string.
   */
  public static String simplify(String num) {
      int n = Integer.parseInt(nume(num)); // Extract and convert the numerator from the string
      int y = Integer.parseInt(deno(num)); // Extract and convert the denominator from the string
      int gcf = gcf(n, y);                 // Compute the greatest common factor of numerator and denominator

      // Handle case when numerator is zero
      if (n == 0) {
          return (n + "");
      }

      // Simplify the fraction when denominator is 1
      if (y == 1) {
          return Integer.toString(n);
      } else if (n > y) {
          // Simplify when numerator is greater than denominator
          int x = n % y; // Calculate remainder
          if (x == 0) {
              return (n / y + "");
          }
          int w = n / y; // Calculate the whole number part for mixed number
          return w + " " + x + "/" + y; // Return as a mixed number
      } else if (n == y) {
          // Return "1" when numerator and denominator are equal
          return "1";
      } else if (n < y) {
          // Handle negative numerators
          if (n < 0) {
              n = n * -1;
              if (n > y) {
                  int newn = n / y;
                  newn *= -1;
                  int neww = n % y;
                  num = newn + " " + neww + "/" + y;
              
              } else{
                int divider = gcf(n , y);
                n = n / divider;
                y = y / divider;
                n *= -1;
                num = n + "/" + y;
              }
          } else {
              // Simplify the fraction
              n = n / gcf; // Divide numerator by GCF
              y = y / gcf; // Divide denominator by GCF
              num = n + "/" + y;
          }
      } else {
          // Return "0" for any other case
          num = "0";
      }

      return num; // Return the simplified fraction or mixed number
  }


   /**
    * Adds two fractions or mixed numbers.
    * If the denominators are the same, it directly adds the numerators.
    * If the denominators are different, it finds a common denominator before performing the addition.
    *
    *  num The first fraction or mixed number in string format.
    *  num2 The second fraction or mixed number in string format.
    * return The sum of the two fractions or mixed numbers as a simplified fraction in string format.
    */
   public static String add(String num , String num2){
       // Extract and convert the numerator and denominator of the first fraction
       int n = Integer.parseInt(nume(num));
       int y = Integer.parseInt(deno(num));

       // Extract and convert the numerator and denominator of the second fraction
       int n2 = Integer.parseInt(nume(num2));
       int y2 = Integer.parseInt(deno(num2));

       // Check if the denominators are the same
       if(y == y2){
           // Directly add the numerators if the denominators are the same
           int n3 = n + n2;
           return n3 + "/" + y;
       } else {
           // Calculate the numerator for the common denominator
           int n3 = n * (LCM(y , y2) / y) + n2 * (LCM(y , y2) / y2);
           // Find the least common multiple of the two denominators
           int y3 = LCM(y , y2);
           return n3 + "/" + y3;
       }
   }


    /**
     * Subtracts one fraction or mixed number from another.
     * If the denominators are the same, it directly subtracts the numerators.
     * If the denominators are different, it finds a common denominator before performing the subtraction.
     *
     *  num The first fraction or mixed number in string format (the minuend).
     * num2 The second fraction or mixed number in string format (the subtrahend).
     * return The difference between the two fractions or mixed numbers as a simplified fraction in string format.
     */
    public static String subtract(String num , String num2){
        // Extract and convert the numerator and denominator of the first fraction
        int n = Integer.parseInt(nume(num));
        int y = Integer.parseInt(deno(num));

        // Extract and convert the numerator and denominator of the second fraction
        int n2 = Integer.parseInt(nume(num2));
        int y2 = Integer.parseInt(deno(num2));

        // Check if the denominators are the same
        if(y == y2){
            // Directly subtract the numerators if the denominators are the same
            int n3 = n - n2;
            // If the result is 0, return "0" as the simplified fraction
            if(n3 == 0){
                return "0";
            } else {
                return n3 + "/" + y;
            }
        } else {
            // Calculate the numerator for the common denominator
            int n3 = n * (LCM(y, y2) / y) - n2 * (LCM(y, y2) / y2);
            // Find the least common multiple of the two denominators
            int y3 = LCM(y, y2);
            // If the result is 0, return "0" as the simplified fraction
            if(n3 == 0){
                return "0";
            } else {
                return n3 + "/" + y3;
            }
        }
    }


   /**
    * Multiplies two fractions or mixed numbers.
    * The method multiplies the numerators and denominators of the fractions separately.
    *
    *  num The first fraction or mixed number in string format.
    *  num2 The second fraction or mixed number in string format.
    * return The product of num and num2 as a fraction in string format.
    */
   public static String multiply(String num , String num2){
       // Parse numerators and denominators from the input strings
       int n = Integer.parseInt(nume(num));
       int y = Integer.parseInt(deno(num));
       int n2 = Integer.parseInt(nume(num2));
       int y2 = Integer.parseInt(deno(num2));

       // Check for a zero in either numerator
       if(n == 0 || n2 == 0){
           return "0"; // Product is zero if any numerator is zero
       } else {
           // Multiply numerators and denominators respectively
           int n3 = n * n2;
           int y3 = y * y2;
           return n3 + "/" + y3; // Return the product as a fraction
       }
   }

   /**
    * Divides one fraction or mixed number by another.
    * The method inverts the second fraction (divisor) and multiplies it with the first fraction (dividend).
    *
    * num The dividend (fraction to be divided) in string format.
    *  num2 The divisor (fraction to divide by) in string format.
    * return The quotient of num divided by num2 as a fraction in string format.
    */
   public static String divide(String num , String num2){
       // Parse numerators and denominators from the input strings
       int n = Integer.parseInt(nume(num));
       int y = Integer.parseInt(deno(num));
       int n2 = Integer.parseInt(nume(num2));
       int y2 = Integer.parseInt(deno(num2));

       // Check for a zero in either numerator
       if(n == 0 || n2 == 0){
           return "0"; // Result is zero if any numerator is zero
       } else {
           // Multiply the dividend by the reciprocal of the divisor
           int n3 = n * y2;
           int y3 = y * n2;
           return n3 + "/" + y3; // Return the quotient as a fraction
       }
   }

   /**
    * Calculates the Least Common Multiple (LCM) of two denominators.
    * LCM is the smallest number that both denominators can divide without leaving a remainder.
    *
    *  denominator1 The first denominator.
    *  denominator2 The second denominator.
    * return The least common multiple of the two denominators.
    */
   public static int LCM(int denominator1, int denominator2){
       // Check if denominators are equal
       if(denominator1 == denominator2){
           return denominator1; // The LCM is the denominator itself if they are equal
       } else {
           // Calculate LCM using the formula: LCM(a, b) = (a * b) / GCF(a, b)
           int lcm = (denominator1 * denominator2) / gcf(denominator1 , denominator2);
           return lcm; // Return the calculated LCM
       }
   }


  /**
   * Converts a mixed number to an improper fraction or simplifies a fraction.
   * If the input is a mixed number (contains '_'), it converts it to an improper fraction.
   * If the input is already a fraction (not a mixed number), it simplifies it if possible.
   *
   *  num The mixed number or fraction in string format.
   * return The equivalent improper fraction or simplified fraction as a string.
   */
  public static String mixed(String num) {
      if (num.contains("_")) {
          // Handling mixed numbers
          int w = Integer.parseInt(whole(num)); // Extract the whole number part
          int n = Integer.parseInt(nume(num));  // Extract the numerator part
          int d = Integer.parseInt(deno(num));  // Extract the denominator part

          // Simplify or convert to improper fraction based on different cases
          if (n == 0 && w == 0) {
              return "0/1"; // If both parts are zero, return "0/1"
          }
          if (w == 0) {
              return n + "/" + d; // If only the whole number part is zero, return the fraction part
          }
          if (n == 0) {
              return (d * w) + "/" + d; // If only the numerator is zero, adjust with the whole number part
          }
          if ((d % n) == 0) {
              int divider = gcf(n, d); // Find GCF to simplify the fraction
              n = n / divider;
              d = d / divider;
          }
          if (w <= 0) {
              n *= -1; // Adjust sign if the whole number part is negative
          }

          int newNumerator = n + (w * d); // Calculate the new numerator for the improper fraction
          return newNumerator + "/" + d; // Return the improper fraction
      } else {
          // Handling simple fractions
          int n = Integer.parseInt(nume(num)); // Extract the numerator
          int d = Integer.parseInt(deno(num)); // Extract the denominator

          // Simplify the fraction if possible
          if (n == 0) {
              return "0/1"; // If numerator is zero, return "0/1"
          } else if ((d % n) == 0) {
              int divider = gcf(n, d); // Find GCF to simplify the fraction
              n = n / divider;
              d = d / divider;
          }
          return n + "/" + d; // Return the simplified fraction
      }
  }


    // Returns a string that is helpful to the user about how
    // to use the program. These are instructions to the user.
    public static String provideHelp() {
        // TODO: Update this help text!
        String help = "Welcome to Fraction Calculator!\n";
        help += "To use this program, enter a mathematical expression using fractions, mixed numbers, and operators (+, -, *, /).\n";
        help += "For example: 1/2 + 3/4\n";
        help += "This will add 1/2 and 3/4 and return the result.\n";
        return help;
    }
}
