package academy.kata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("(Для выхода введите \"Exit\")");

        while (true) {
            System.out.println("Введите пример:");
            String inputString = br.readLine();
            if (inputString.equals("Exit") || inputString.equals("exit")) {
                System.exit(0);
            } else {
                System.out.println(calc(inputString));
            }
        }
    }

    public static String calc(String input) {
        int firstNum = 0;
        int secondNum = 0;
        int output;
        char sign = '+';
        boolean isRoman = false;

        //Checking the count of numbers in the expression
        if (input.split(" ").length != 3) {
            System.out.println("Incorrect expression!");
            throw new IllegalArgumentException("Incorrect expression!");
        }

        //Defining numbers and sign
        try {
            //Arabic numbers
            firstNum = Integer.parseInt(input.split(" ")[0]);
            sign = input.split(" ")[1].charAt(0);
            secondNum = Integer.parseInt(input.split(" ")[2]);
        } catch (Exception e) {
            firstNum = romanToArabic(input.split(" ")[0]);
            sign = input.split(" ")[1].charAt(0);
            secondNum = romanToArabic(input.split(" ")[2]);
            isRoman = true;
        }

        //Checking the value of numbers
        if (firstNum < 1 || firstNum > 10 || secondNum < 1 || secondNum > 10) {
            System.out.println("Numbers out of range!");
            throw new IllegalArgumentException("Numbers out of range!");
        }

        //Calculation and output
        try {
            output = calculate(firstNum, secondNum, sign);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Incorrect sign!");
            throw new IllegalArgumentException("Incorrect sign!");
        }

        if (isRoman) {
            return arabicToRoman(output);
        } else {
            return String.valueOf(output);
        }
    }

    private static Integer calculate(int a, int b, char sign) {
        Integer result;

        switch (sign) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    private static int decodeSingleRomanLetter(char letter) {
        switch (letter) {
            case 'X':
                return 10;
            case 'V':
                return 5;
            case 'I':
                return 1;
            default:
                System.out.println("Incorrect arabic number!");
                throw new IllegalArgumentException("Incorrect arabic number!");
        }
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        roman = roman.toUpperCase();

        for (int i = 0; i < roman.length() - 1; i++) {
            if (decodeSingleRomanLetter(roman.charAt(i)) < decodeSingleRomanLetter(roman.charAt(i + 1))) {
                result -= decodeSingleRomanLetter(roman.charAt(i));
            } else {
                result += decodeSingleRomanLetter(roman.charAt(i));
            }
        }

        result += decodeSingleRomanLetter(roman.charAt(roman.length() - 1));

        return result;
    }

    private enum RomanNums {
        I(1), IV(4), V(5), IX(9), X(10);
        final int number;

        RomanNums(int number) {
            this.number = number;
        }
    }

    private static String arabicToRoman(int arabic) {
        if (arabic <= 0) {
            System.out.println("The Arabic number is less than or equal to 0!");
            throw new IllegalArgumentException("The Arabic number is less than or equal to 0!");
        }

        StringBuilder buf = new StringBuilder();

        final RomanNums[] values = RomanNums.values();
        for (int i = values.length - 1; i >= 0; i--) {
            while (arabic >= values[i].number) {
                buf.append(values[i]);
                arabic -= values[i].number;
            }
        }

        return buf.toString();
    }
}