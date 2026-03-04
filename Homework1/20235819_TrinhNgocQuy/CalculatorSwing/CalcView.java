package CalculatorSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalcView extends JFrame {
    private JTextField firstNumber = new JTextField(10);
    private JTextField secondNumber = new JTextField(10);
    private JButton addButton = new JButton("+");
    private JButton subButton = new JButton("-");
    private JButton mulButton = new JButton("*");
    private JButton divButton = new JButton("/");
    private JTextField calcSolution = new JTextField(10);

    public CalcView() {
        JPanel calcPanel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);

        calcPanel.add(new JLabel("Số 1: "));
        calcPanel.add(firstNumber);
        calcPanel.add(new JLabel("Số 2: "));
        calcPanel.add(secondNumber);

        calcPanel.add(addButton);
        calcPanel.add(subButton);
        calcPanel.add(mulButton);
        calcPanel.add(divButton);

        calcPanel.add(new JLabel("Kết quả: "));
        calcSolution.setEditable(false);
        calcPanel.add(calcSolution);

        this.add(calcPanel);
    }

    public double getFirstNumber() {
        return Double.parseDouble(firstNumber.getText());
    }

    public double getSecondNumber() {
        return Double.parseDouble(secondNumber.getText());
    }

    public void setCalcSolution(double solution) {
        calcSolution.setText(Double.toString(solution));
    }

    void addCalculationListener(ActionListener listenForCalcButton) {
        addButton.addActionListener(listenForCalcButton);
        subButton.addActionListener(listenForCalcButton);
        mulButton.addActionListener(listenForCalcButton);
        divButton.addActionListener(listenForCalcButton);
    }

    void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
