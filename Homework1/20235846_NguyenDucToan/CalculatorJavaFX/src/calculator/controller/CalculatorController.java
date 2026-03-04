package calculator.controller;

import calculator.model.CalculatorModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField firstNumberField;

    @FXML
    private ComboBox<String> operatorBox;

    @FXML
    private TextField secondNumberField;

    @FXML
    private TextField resultField;

    private CalculatorModel model;

    @FXML
    public void initialize() {
        model = new CalculatorModel();
        operatorBox.setItems(FXCollections.observableArrayList("+", "-", "×", "÷"));
        operatorBox.getSelectionModel().selectFirst();
        resultField.setEditable(false);
    }

    @FXML
    private void handleCalculate() {
        try {
            double firstNumber = Double.parseDouble(firstNumberField.getText());
            double secondNumber = Double.parseDouble(secondNumberField.getText());
            String operator = operatorBox.getValue();

            model.calculate(firstNumber, secondNumber, operator);

            double result = model.getResult();

            // Display as integer if the result is a whole number
            if (result == Math.floor(result) && !Double.isInfinite(result)) {
                resultField.setText(String.valueOf((long) result));
            } else {
                resultField.setText(String.valueOf(result));
            }

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers!");
        } catch (ArithmeticException ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
