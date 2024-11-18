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
        Scanner scanner = null;
        System.out.println("Консольное приложение «Калькулятор». Для выхода наберите «exit»");
        try {
            scanner = new Scanner(System.in);
            while (true) {
                String input = createInput(scanner);
                if (input.equals("exit")) break;
                System.out.println("Output: " + resultOfArithmeticOperation(input));
            }
        } catch (InvalidFormatException exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (scanner != null) scanner.close();
        }
    }

    public static int resultOfArithmeticOperation(String input) throws InvalidFormatException {
        String[] words = input.split(OPERATION_REGEX);

        if (isValidOperation(words)) {
            throw new InvalidFormatException("Строка не является математической операцией.");
        }

        if (isValidFormatOperation(words)) {
            throw new InvalidFormatException("Формат математической операции не удовлетворяет заданию -" +
                    "два операнда и один оператор (+, -, /, *)");
        }

        int a;
        int b;
        try {
            a = Integer.parseInt(words[0].replaceAll(" ", ""));
            b = Integer.parseInt(words[1].replaceAll(" ", ""));
        } catch (NumberFormatException exception) {
            throw new InvalidFormatException("Не удалось получить числа для арифметической операции.");
        }

        int index = getIndexOperationInInput(input);
        char operation = input.charAt(index);

        if (!isValidFormatNumbers(a, b)) {
            throw new InvalidFormatException("На вход переданы числа, " +
                    "с которыми не работает калькулятор");
        }

        return calculator(a, b, operation);
    }

    /**
     * Получение данных из консоли
     *
     * @return строка сданными из консоли
     */
    private static String createInput(Scanner scanner) {
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

    /**
     * Проверка, является ли строка математической операцией
     *
     * @param words массив слов
     * @return true, если строка - нематематическая операция
     */
    private static boolean isValidOperation(String[] words) {
        return words.length == 0 || words.length == 1;
    }

    /**
     * Проверить удовлетворяет ли заданию формат математической операции
     *
     * @param words массив слов
     * @return true, если формат математической операции не удовлетворяет заданию
     */
    private static boolean isValidFormatOperation(String[] words) {
        return words.length > 2;
    }

    /**
     * Проверить переданные на вход числа, удовлетворяют ли они, формату чисел,
     * с которыми не работает калькулятор
     *
     * @param a первое число
     * @param b второе число
     * @return true, если числа соответствуют
     */
    private static boolean isValidFormatNumbers(int a, int b) {
        return a >= 1 && b >= 1 && a <= 10 && b <= 10;
    }
}
