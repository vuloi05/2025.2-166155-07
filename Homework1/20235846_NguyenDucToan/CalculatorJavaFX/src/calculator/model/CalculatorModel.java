package calculator.model;

public class CalculatorModel {

    private double result;

    public void calculate(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "×":
                result = firstNumber * secondNumber;
                break;
            case "÷":
                if (secondNumber == 0) {
                    throw new ArithmeticException("Cannot divide by zero!");
                }
                result = firstNumber / secondNumber;
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public double getResult() {
        return result;
    }
}
