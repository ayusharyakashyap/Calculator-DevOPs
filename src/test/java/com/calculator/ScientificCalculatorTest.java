package com.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ScientificCalculator class
 * Tests all calculator operations with various scenarios
 */
@DisplayName("Scientific Calculator Tests")
public class ScientificCalculatorTest {
    
    private ScientificCalculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new ScientificCalculator();
    }
    
    // Square Root Tests
    @Test
    @DisplayName("Test square root of positive numbers")
    void testSquareRootPositiveNumbers() {
        assertEquals(4.0, calculator.squareRoot(16.0), 0.001);
        assertEquals(5.0, calculator.squareRoot(25.0), 0.001);
        assertEquals(3.0, calculator.squareRoot(9.0), 0.001);
        assertEquals(1.0, calculator.squareRoot(1.0), 0.001);
    }
    
    @Test
    @DisplayName("Test square root of zero")
    void testSquareRootZero() {
        assertEquals(0.0, calculator.squareRoot(0.0), 0.001);
    }
    
    @Test
    @DisplayName("Test square root of decimal numbers")
    void testSquareRootDecimal() {
        assertEquals(1.414, calculator.squareRoot(2.0), 0.001);
        assertEquals(2.236, calculator.squareRoot(5.0), 0.001);
    }
    
    @Test
    @DisplayName("Test square root of negative number throws exception")
    void testSquareRootNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.squareRoot(-1.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.squareRoot(-25.0);
        });
    }
    
    // Factorial Tests
    @Test
    @DisplayName("Test factorial of small positive numbers")
    void testFactorialPositiveNumbers() {
        assertEquals(1, calculator.factorial(0));
        assertEquals(1, calculator.factorial(1));
        assertEquals(2, calculator.factorial(2));
        assertEquals(6, calculator.factorial(3));
        assertEquals(24, calculator.factorial(4));
        assertEquals(120, calculator.factorial(5));
    }
    
    @Test
    @DisplayName("Test factorial of larger numbers")
    void testFactorialLargerNumbers() {
        assertEquals(720, calculator.factorial(6));
        assertEquals(5040, calculator.factorial(7));
        assertEquals(40320, calculator.factorial(8));
    }
    
    @Test
    @DisplayName("Test factorial of negative number throws exception")
    void testFactorialNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.factorial(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.factorial(-5);
        });
    }
    
    // Natural Logarithm Tests
    @Test
    @DisplayName("Test natural logarithm of positive numbers")
    void testNaturalLogarithmPositiveNumbers() {
        assertEquals(0.0, calculator.naturalLogarithm(1.0), 0.001);
        assertEquals(1.0, calculator.naturalLogarithm(Math.E), 0.001);
        assertEquals(2.0, calculator.naturalLogarithm(Math.E * Math.E), 0.001);
    }
    
    @Test
    @DisplayName("Test natural logarithm of specific values")
    void testNaturalLogarithmSpecificValues() {
        assertEquals(0.693, calculator.naturalLogarithm(2.0), 0.001);
        assertEquals(1.609, calculator.naturalLogarithm(5.0), 0.001);
        assertEquals(2.303, calculator.naturalLogarithm(10.0), 0.001);
    }
    
    @Test
    @DisplayName("Test natural logarithm of zero throws exception")
    void testNaturalLogarithmZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.naturalLogarithm(0.0);
        });
    }
    
    @Test
    @DisplayName("Test natural logarithm of negative number throws exception")
    void testNaturalLogarithmNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.naturalLogarithm(-1.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.naturalLogarithm(-5.0);
        });
    }
    
    // Power Tests
    @Test
    @DisplayName("Test power with positive base and exponent")
    void testPowerPositiveNumbers() {
        assertEquals(8.0, calculator.power(2.0, 3.0), 0.001);
        assertEquals(25.0, calculator.power(5.0, 2.0), 0.001);
        assertEquals(1.0, calculator.power(10.0, 0.0), 0.001);
        assertEquals(10.0, calculator.power(10.0, 1.0), 0.001);
    }
    
    @Test
    @DisplayName("Test power with negative base")
    void testPowerNegativeBase() {
        assertEquals(-8.0, calculator.power(-2.0, 3.0), 0.001);
        assertEquals(4.0, calculator.power(-2.0, 2.0), 0.001);
        assertEquals(1.0, calculator.power(-5.0, 0.0), 0.001);
    }
    
    @Test
    @DisplayName("Test power with negative exponent")
    void testPowerNegativeExponent() {
        assertEquals(0.5, calculator.power(2.0, -1.0), 0.001);
        assertEquals(0.25, calculator.power(2.0, -2.0), 0.001);
        assertEquals(0.1, calculator.power(10.0, -1.0), 0.001);
    }
    
    @Test
    @DisplayName("Test power with decimal exponent")
    void testPowerDecimalExponent() {
        assertEquals(4.0, calculator.power(16.0, 0.5), 0.001);
        assertEquals(2.0, calculator.power(8.0, 1.0/3.0), 0.001);
    }
    
    @Test
    @DisplayName("Test power edge cases")
    void testPowerEdgeCases() {
        assertEquals(1.0, calculator.power(0.0, 0.0), 0.001);
        assertEquals(0.0, calculator.power(0.0, 1.0), 0.001);
        assertEquals(1.0, calculator.power(1.0, 1000.0), 0.001);
    }
}