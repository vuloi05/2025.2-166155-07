package Calculator.Swing;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        Control controller = new Control(model, view);

        view.setVisible(true);
    }
}