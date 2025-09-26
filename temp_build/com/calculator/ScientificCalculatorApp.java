package com.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Main application class for Scientific Calculator
 * Provides menu-driven interface for calculator operations
 */
public class ScientificCalculatorApp {
    
    private static final Logger logger = LogManager.getLogger(ScientificCalculatorApp.class);
    private static final ScientificCalculator calculator = new ScientificCalculator();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        logger.info("Starting Scientific Calculator Application");
        
        System.out.println("=================================");
        System.out.println("  SCIENTIFIC CALCULATOR");
        System.out.println("=================================");
        System.out.println("Welcome to the Scientific Calculator!");
        System.out.println("This calculator supports the following operations:");
        System.out.println();
        
        boolean continueCalculating = true;
        
        while (continueCalculating) {
            displayMenu();
            int choice = getUserChoice();
            
            try {
                switch (choice) {
                    case 1:
                        performSquareRoot();
                        break;
                    case 2:
                        performFactorial();
                        break;
                    case 3:
                        performNaturalLogarithm();
                        break;
                    case 4:
                        performPower();
                        break;
                    case 5:
                        continueCalculating = false;
                        System.out.println("Thank you for using Scientific Calculator!");
                        logger.info("Application terminated by user");
                        break;
                    default:
                        System.out.println("Invalid choice! Please select a number between 1-5.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                logger.error("Operation failed: " + e.getMessage());
            }
            
            if (continueCalculating) {
                System.out.println("\n" + "=".repeat(50) + "\n");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("Choose an operation:");
        System.out.println("1. Square Root (√x)");
        System.out.println("2. Factorial (!x)");
        System.out.println("3. Natural Logarithm (ln(x))");
        System.out.println("4. Power (x^b)");
        System.out.println("5. Exit");
        System.out.print("\nEnter your choice (1-5): ");
    }
    
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    private static void performSquareRoot() {
        System.out.print("Enter a number for square root: ");
        try {
            double number = Double.parseDouble(scanner.nextLine().trim());
            double result = calculator.squareRoot(number);
            System.out.println("√" + number + " = " + result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }
    
    private static void performFactorial() {
        System.out.print("Enter a non-negative integer for factorial: ");
        try {
            int number = Integer.parseInt(scanner.nextLine().trim());
            long result = calculator.factorial(number);
            System.out.println(number + "! = " + result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid integer.");
        }
    }
    
    private static void performNaturalLogarithm() {
        System.out.print("Enter a positive number for natural logarithm: ");
        try {
            double number = Double.parseDouble(scanner.nextLine().trim());
            double result = calculator.naturalLogarithm(number);
            System.out.println("ln(" + number + ") = " + result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }
    
    private static void performPower() {
        System.out.print("Enter the base number (x): ");
        try {
            double base = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter the exponent (b): ");
            double exponent = Double.parseDouble(scanner.nextLine().trim());
            double result = calculator.power(base, exponent);
            System.out.println(base + "^" + exponent + " = " + result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid numbers.");
        }
    }
}