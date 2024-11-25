package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void testCalc1() throws InvalidFormatException {
        String input = "1 + 2";
        int actual = 3;
        int expected = App.resultOfArithmeticOperation(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCalc2() {
        String input = "1";
        Assertions.assertThrows(
                InvalidFormatException.class, () -> App.resultOfArithmeticOperation(input));
    }

    @Test
    public void testCalc3() {
        String input = "1 + 2 1";
        Assertions.assertThrows(
                InvalidFormatException.class, () -> App.resultOfArithmeticOperation(input));
    }

    @Test
    public void testCalc4() {
        String input = "1 + 2 + 3";
        Assertions.assertThrows(
                InvalidFormatException.class, () -> App.resultOfArithmeticOperation(input));
    }
}