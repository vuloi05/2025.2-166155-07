package Calculator.Swing;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    JTextField num1Field = new JTextField(10);
    JTextField num2Field = new JTextField(10);
    JButton addBtn = new JButton("+");
    JButton subBtn = new JButton("-");
    JButton mulBtn = new JButton("*");
    JButton divBtn = new JButton("/");
    JLabel resultLabel = new JLabel("Result: ");

    public View() {
        setTitle("Calculator MVC - Swing");
        setSize(350,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Number 1:"));
        add(num1Field);
        add(new JLabel("Number 2:"));
        add(num2Field);
        add(addBtn);
        add(subBtn);
        add(mulBtn);
        add(divBtn);
        add(resultLabel);
    }
}