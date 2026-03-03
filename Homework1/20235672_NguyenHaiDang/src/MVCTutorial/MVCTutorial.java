package MVCTutorial;
import java.util.Scanner;
import MVCTutorial.*;
    public class MVCTutorial {
        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            Test model = new Test();
            TestView view = new TestView();
            TestControl controller = new TestControl(model, view);

            System.out.print("Nhap ten: ");
            String name = scanner.nextLine();

            controller.setTestName(name);
            controller.updateView();

            scanner.close();
        }
    }
