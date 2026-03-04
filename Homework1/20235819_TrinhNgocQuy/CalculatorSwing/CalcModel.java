package CalculatorSwing;

public class CalcModel {
    private double result = 0;

    public void add(double number) {
        result += number;
    }

    public void subtract(double number) {
        result -= number;
    }

    public void multiply(double number) {
        result *= number;
    }

    public void divide(double number) {
        if (number != 0) {
            result /= number;
        } else {
            throw new ArithmeticException("Lỗi: Chia cho 0");
        }
    }

    public double getResult() {
        return result;
    }

    public void clear() {
        result = 0;
    }
}
