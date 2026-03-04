package CalculatorSwing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalcController {
    private CalcView theView;
    private CalcModel theModel;

    public CalcController(CalcView theView, CalcModel theModel) {
        this.theView = theView;
        this.theModel = theModel;

        this.theView.addCalculationListener(new CalculateListener());
    }

    class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double firstNumber, secondNumber = 0;
            try {
                firstNumber = theView.getFirstNumber();
                secondNumber = theView.getSecondNumber();
                theModel.clear();
                theModel.add(firstNumber);

                String command = e.getActionCommand();
                switch (command) {
                    case "+":
                        theModel.add(secondNumber);
                        break;
                    case "-":
                        theModel.subtract(secondNumber);
                        break;
                    case "*":
                        theModel.multiply(secondNumber);
                        break;
                    case "/":
                        theModel.divide(secondNumber);
                        break;
                }

                theView.setCalcSolution(theModel.getResult());

            } catch (NumberFormatException ex) {
                theView.displayErrorMessage("Vui lòng nhập 2 số hợp lệ");
            } catch (ArithmeticException ex) {
                theView.displayErrorMessage("Lỗi: Không thể chia cho 0");
            }
        }
    }
}
