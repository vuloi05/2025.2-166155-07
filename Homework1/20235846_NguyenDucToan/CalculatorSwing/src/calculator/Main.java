package calculator;

import calculator.model.CalculatorModel;
import calculator.view.CalculatorView;
import calculator.controller.CalculatorController;

public class Main {

    public static void main(String[] args) {
        CalculatorView theView = new CalculatorView();
        CalculatorModel theModel = new CalculatorModel();
        CalculatorController theController = new CalculatorController(theView, theModel);

        theView.setVisible(true);
    }
}
