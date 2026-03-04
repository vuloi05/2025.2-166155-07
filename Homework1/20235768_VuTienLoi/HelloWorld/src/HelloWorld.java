// Main branch version

import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Hello, " + name + "!");

        System.out.println("Welcome to Homework01!");

        scanner.close();
    }
}
