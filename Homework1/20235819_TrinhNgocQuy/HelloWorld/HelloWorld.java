import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
<<<<<<< HEAD
        System.out.print("Vui lòng nhập tên của bạn: ");
        String name = scanner.nextLine();
        System.out.println("Xin chào, " + name + "!");
=======
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Welcome, " + name + "!");
>>>>>>> 91b5c48d0346553c5f254a35be8cbec4b1b35a3e
        scanner.close();
    }
}
