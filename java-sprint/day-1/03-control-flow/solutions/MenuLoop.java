// MenuLoop.java - solution
// Run it with:  java MenuLoop.java

import java.util.Scanner;

public class MenuLoop {

    static void printMenu() {
        System.out.println();
        System.out.println("=== Toolbox ===");
        System.out.println("  1) Greet me");
        System.out.println("  2) Double a number");
        System.out.println("  3) Count to five");
        System.out.println("  quit) Leave");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // The flag that keeps the loop alive. Setting it to false is how the
        // quit branch asks the loop to stop after the current pass.
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose: ");
            String choice = in.nextLine();

            // Everything is read with nextLine, so there is no leftover
            // newline to trip over. Text that should be a number gets
            // converted with Integer.parseInt.
            switch (choice) {
                case "1" -> System.out.println("Hello there!");
                case "2" -> {
                    System.out.print("Number: ");
                    int n = Integer.parseInt(in.nextLine());
                    System.out.println(n + " doubled is " + (n * 2));
                }
                case "3" -> {
                    for (int i = 1; i <= 5; i++) {
                        System.out.print(i + " ");
                    }
                    System.out.println();
                }
                case "quit" -> {
                    System.out.println("Bye!");
                    running = false;
                }
                default -> System.out.println("I do not know the option \"" + choice + "\".");
            }
        }

        in.close();
    }
}
