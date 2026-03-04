package calculator.controller;

import calculator.model.CalculatorModel;
import calculator.view.CalculatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {

    private CalculatorView theView;
    private CalculatorModel theModel;

    public CalculatorController(CalculatorView theView, CalculatorModel theModel) {
        this.theView = theView;
        this.theModel = theModel;

        this.theView.addCalculateListener(new CalculateListener());
    }

    class CalculateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double firstNumber = Double.parseDouble(theView.getFirstNumber());
                double secondNumber = Double.parseDouble(theView.getSecondNumber());
                String operator = theView.getSelectedOperator();

                theModel.calculate(firstNumber, secondNumber, operator);

                double result = theModel.getResult();

                // Display as integer if the result is a whole number
                if (result == Math.floor(result) && !Double.isInfinite(result)) {
                    theView.setResult(String.valueOf((long) result));
                } else {
                    theView.setResult(String.valueOf(result));
                }

            } catch (NumberFormatException ex) {
                theView.displayErrorMessage("Please enter valid numbers!");
            } catch (ArithmeticException ex) {
                theView.displayErrorMessage(ex.getMessage());
            }
        }
    }
}
