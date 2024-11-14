package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Приложение читает из консоли введенные пользователем строки,
 * числа, арифметические операции, проводимые между ними и выводит в консоль результат
 * их выполнения.
 */
public class App {
    public final static String OPERATION_REGEX = "[-+/*]";

    public static void main(String[] args) {
        try {
            while (true) {
                String input = createInput();
                System.out.println("Output: " + calc(input));
            }
        } catch (InvalidFormatException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static String calc(String input) throws InvalidFormatException {
        String[] words = input.split(OPERATION_REGEX);

        if (words.length == 0 || words.length == 1) {
            throw new InvalidFormatException("Строка не является математической операцией.");
        }

        if (words.length > 2) {
            throw new InvalidFormatException("Формат математической операции не удовлетворяет заданию -" +
                    "два операнда и один оператор (+, -, /, *)");
        }

        int a = Integer.parseInt(words[0]);
        int b = Integer.parseInt(words[1]);
        int index = getIndexOperationInInput(input);
        char operation = input.charAt(index);

        if (a < 1 || b < 1 || a > 10 || b > 10) {
            throw new InvalidFormatException("На вход переданы числа, " +
                    "с которыми не работает калькулятор");
        }

        return String.valueOf(calculator(a, b, operation));
    }

    /**
     * Получение данных из консоли
     *
     * @return строка сданными из консоли
     */
    private static String createInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input: ");
        return scanner.nextLine().trim();
    }

    /**
     * Результат арифметической операции
     *
     * @param a         первое число
     * @param b         второе число
     * @param operation тип арифметической операции
     * @return результат арифметической операции
     */
    private static int calculator(int a, int b, char operation) throws InvalidFormatException {
        return switch (operation) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new InvalidFormatException("Недопустимая арифметическая" +
                    " операция: " + operation);
        };
    }

    /**
     * Индекс математической операции в строке запроса
     *
     * @param input строка запроса
     * @return индекс математической операции
     */
    private static int getIndexOperationInInput(String input) {
        int index = 0;
        Pattern pattern = Pattern.compile(OPERATION_REGEX);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            index = matcher.start();
        }
        return index;
    }
}
