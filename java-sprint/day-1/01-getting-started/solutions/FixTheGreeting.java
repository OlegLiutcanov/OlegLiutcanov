// SOLUTION to Exercise 1.
//
// The four repairs:
//   1. line 3 of the broken source was missing its ;
//   2. the class was missing its final }
//   3. system -> System   (capital S; Java is case sensitive)
//   4. Println -> println (lower-case p; the method is spelled println)

public class FixTheGreeting {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
        System.out.println("My name is Ada.");
        System.out.println("I am on day 1 of the sprint.");
    }
}
