package calculator.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CalculatorView extends JFrame {

    private JTextField firstNumberField = new JTextField(10);
    private JTextField secondNumberField = new JTextField(10);
    private JTextField resultField = new JTextField(10);
    private JComboBox<String> operatorBox = new JComboBox<>(new String[]{"+", "-", "×", "÷"});
    private JButton calculateButton = new JButton("Calculate");

    public CalculatorView() {
        super("Calculator - Swing MVC");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 180);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        inputPanel.add(new JLabel("Number 1:"));
        inputPanel.add(firstNumberField);
        inputPanel.add(operatorBox);
        inputPanel.add(new JLabel("Number 2:"));
        inputPanel.add(secondNumberField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        calculateButton.setPreferredSize(new Dimension(120, 30));
        buttonPanel.add(calculateButton);
        buttonPanel.add(new JLabel("Result:"));
        resultField.setEditable(false);
        buttonPanel.add(resultField);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    public String getFirstNumber() {
        return firstNumberField.getText();
    }

    public String getSecondNumber() {
        return secondNumberField.getText();
    }

    public String getSelectedOperator() {
        return (String) operatorBox.getSelectedItem();
    }

    public void setResult(String result) {
        resultField.setText(result);
    }

    public void addCalculateListener(ActionListener listener) {
        calculateButton.addActionListener(listener);
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
