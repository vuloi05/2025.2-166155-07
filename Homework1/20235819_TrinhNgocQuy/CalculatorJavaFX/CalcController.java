package CalculatorJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CalcController {

    @FXML
    private TextField firstNumber;

    @FXML
    private TextField secondNumber;

    @FXML
    private TextField calcSolution;

    private CalcModel model = new CalcModel();

    @FXML
    void handleAdd(ActionEvent event) {
        calculate("+");
    }

    @FXML
    void handleSubtract(ActionEvent event) {
        calculate("-");
    }

    @FXML
    void handleMultiply(ActionEvent event) {
        calculate("*");
    }

    @FXML
    void handleDivide(ActionEvent event) {
        calculate("/");
    }

    private void calculate(String operator) {
        try {
            double n1 = Double.parseDouble(firstNumber.getText());
            double n2 = Double.parseDouble(secondNumber.getText());

            model.clear();
            model.add(n1);

            switch (operator) {
                case "+":
                    model.add(n2);
                    break;
                case "-":
                    model.subtract(n2);
                    break;
                case "*":
                    model.multiply(n2);
                    break;
                case "/":
                    model.divide(n2);
                    break;
            }

            calcSolution.setText(String.valueOf(model.getResult()));
        } catch (NumberFormatException e) {
            showAlert("Vui lòng nhập 2 số hợp lệ");
        } catch (ArithmeticException e) {
            showAlert("Lỗi: Không thể chia cho 0");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
