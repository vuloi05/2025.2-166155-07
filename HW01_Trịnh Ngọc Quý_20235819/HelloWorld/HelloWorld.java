import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Vui lòng nhập tên của bạn: ");
        String name = scanner.nextLine();
        System.out.println("Xin chào, " + name + "!");
        scanner.close();
    }
}
