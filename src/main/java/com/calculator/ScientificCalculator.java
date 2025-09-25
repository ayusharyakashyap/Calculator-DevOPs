package com.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Scientific Calculator class with basic mathematical operations
 * Author: Your Name
 * Date: September 2025
 */
public class ScientificCalculator {
    
    private static final Logger logger = LogManager.getLogger(ScientificCalculator.class);
    
    /**
     * Calculate square root of a number
     * @param x the number to calculate square root for (must be non-negative)
     * @return square root of x
     * @throws IllegalArgumentException if x is negative
     */
    public double squareRoot(double x) {
        logger.info("Calculating square root of: " + x);
        
        if (x < 0) {
            logger.error("Cannot calculate square root of negative number: " + x);
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        
        double result = Math.sqrt(x);
        logger.info("Square root result: " + result);
        return result;
    }
    
    /**
     * Calculate factorial of a number
     * @param n the number to calculate factorial for (must be non-negative integer)
     * @return factorial of n
     * @throws IllegalArgumentException if n is negative
     */
    public long factorial(int n) {
        logger.info("Calculating factorial of: " + n);
        
        if (n < 0) {
            logger.error("Cannot calculate factorial of negative number: " + n);
            throw new IllegalArgumentException("Cannot calculate factorial of negative number");
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        
        logger.info("Factorial result: " + result);
        return result;
    }
    
    /**
     * Calculate natural logarithm (base e) of a number
     * @param x the number to calculate natural log for (must be positive)
     * @return natural logarithm of x
     * @throws IllegalArgumentException if x is non-positive
     */
    public double naturalLogarithm(double x) {
        logger.info("Calculating natural logarithm of: " + x);
        
        if (x <= 0) {
            logger.error("Cannot calculate natural logarithm of non-positive number: " + x);
            throw new IllegalArgumentException("Cannot calculate natural logarithm of non-positive number");
        }
        
        double result = Math.log(x);
        logger.info("Natural logarithm result: " + result);
        return result;
    }
    
    /**
     * Calculate power of a number (x^b)
     * @param x the base number
     * @param b the exponent
     * @return x raised to the power b
     */
    public double power(double x, double b) {
        logger.info("Calculating power: " + x + "^" + b);
        
        double result = Math.pow(x, b);
        logger.info("Power result: " + result);
        return result;
    }
}