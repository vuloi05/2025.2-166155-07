package Calculator.Swing;
    public class Model {
        public double calculate(double a, double b, String operator) {
            switch (operator) {
                case "+": return a + b;
                case "-": return a - b;
                case "*": return a * b;
                case "/": return a / b ;
                default: return 0;
            }
        }
    }
