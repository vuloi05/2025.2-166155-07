package Calculator.Swing;

import java.awt.event.*;

public class Control {

    private Model model;
    private View view;

    public Control(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addBtn.addActionListener(e -> calculate("+"));
        view.subBtn.addActionListener(e -> calculate("-"));
        view.mulBtn.addActionListener(e -> calculate("*"));
        view.divBtn.addActionListener(e -> calculate("/"));
    }

    private void calculate(String operator) {
        double a = Double.parseDouble(view.num1Field.getText());
        double b = Double.parseDouble(view.num2Field.getText());

        double result = model.calculate(a, b, operator);

        view.resultLabel.setText("Result: " + result);
    }
}