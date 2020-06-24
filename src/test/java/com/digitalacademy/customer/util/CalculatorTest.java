package com.digitalacademy.customer.util;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void testAddMethod(){
        assertEquals(4, calculator.add(1,3));
        assertEquals(5,calculator.add(3,2));
    }
    @Test
    void testMultiplyMethod(){
        assertEquals(4, calculator.multiply(2,2));
        assertEquals(6,calculator.multiply(2,3));
    }
    @Test
    void testDivideMethod(){
        assertEquals(4,calculator.divide(16,4));
        assertEquals(5,calculator.divide(25,5));
    }
}
