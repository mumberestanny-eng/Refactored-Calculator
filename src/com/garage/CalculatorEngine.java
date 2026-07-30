package com.garage;

/**
 * Handles mathematical evaluation, state management, and formatting.
 * Strictly decoupled from any UI framework (SRP compliant).
 */
public class CalculatorEngine {

    private String operandA = "0";
    private String operandB = "0";
    private String currentOperator = null;
    private boolean isNewInput = true;

    public String processInput(String labelText, String buttonValue) {
        if (isDigitOrDecimal(buttonValue)) {
            return handleDigitOrDecimal(labelText, buttonValue);
        }

        if (isTopOperator(buttonValue)) {
            return handleTopOperator(labelText, buttonValue);
        }

        if (isArithmeticOperator(buttonValue)) {
            return handleArithmeticOperator(labelText, buttonValue);
        }

        if ("=".equals(buttonValue)) {
            return calculateResult(labelText);
        }

        return labelText;
    }

    private boolean isDigitOrDecimal(String value) {
        return "0123456789.".contains(value);
    }

    private boolean isTopOperator(String value) {
        return "AC".equals(value) || "+/-".equals(value) || "%".equals(value);
    }

    private boolean isArithmeticOperator(String value) {
        return "/".equals(value) || "x".equals(value) || "+".equals(value) || "-".equals(value);
    }

    private String handleDigitOrDecimal(String currentDisplay, String input) {
        if (isNewInput) {
            isNewInput = false;
            return ".".equals(input) ? "0." : input;
        }

        if (".".equals(input) && currentDisplay.contains(".")) {
            return currentDisplay; // Prevent multiple decimals
        }

        return "0".equals(currentDisplay) && !".".equals(input) ? input : currentDisplay + input;
    }

    private String handleTopOperator(String currentDisplay, String command) {
        if ("AC".equals(command)) {
            reset();
            return "0";
        }

        double val = Double.parseDouble(currentDisplay);
        if ("+/-".equals(command)) {
            val *= -1;
        } else if ("%".equals(command)) {
            val /= 100.0;
        }

        return formatResult(val);
    }

    private String handleArithmeticOperator(String currentDisplay, String op) {
        operandA = currentDisplay;
        currentOperator = op;
        isNewInput = true;
        return currentDisplay;
    }

    private String calculateResult(String currentDisplay) {
        if (currentOperator == null) {
            return currentDisplay;
        }

        operandB = currentDisplay;
        double numA = Double.parseDouble(operandA);
        double numB = Double.parseDouble(operandB);
        double result = 0;

        switch (currentOperator) {
            case "+": result = numA + numB; break;
            case "-": result = numA - numB; break;
            case "x": result = numA * numB; break;
            case "/":
                if (numB == 0) return "Error"; // Clean error handling
                result = numA / numB;
                break;
        }

        reset();
        return formatResult(result);
    }

    public void reset() {
        operandA = "0";
        operandB = "0";
        currentOperator = null;
        isNewInput = true;
    }

    private String formatResult(double number) {
        if (number % 1 == 0) {
            return String.valueOf((long) number);
        }
        return String.valueOf(number);
    }
}
