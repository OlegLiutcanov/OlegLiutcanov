// TryWithResources.java
// Files, network connections and scanners are RESOURCES: the operating system
// hands you one and expects it back. try-with-resources hands it back for you,
// even when things go wrong.
//
// Run it with:  java TryWithResources.java
//
// This program writes a small file in the system temporary folder, reads it
// back, and deletes it. Nothing is left behind.

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class TryWithResources {

    public static void main(String[] args) {

        System.out.println("=== 1. Reading a file, with the closing handled for us ===");
        try {
            // createTempFile and writeString both declare 'throws IOException',
            // which is why this whole block sits inside a try.
            Path file = Files.createTempFile("java-sprint-", ".txt");
            Files.writeString(file, "alpha\nbeta\ngamma\n");
            System.out.println("  wrote 3 lines to a temporary file");

            // The resource goes in the parentheses after 'try'. When the block
            // ends - normally, or by exception, or by return - close() is
            // called on it automatically.
            try (Scanner fileScanner = new Scanner(file)) {
                int lineNumber = 1;
                while (fileScanner.hasNextLine()) {
                    System.out.println("    line " + lineNumber + ": " + fileScanner.nextLine());
                    lineNumber++;
                }
            }
            System.out.println("  scanner closed automatically");

            Files.delete(file);
            System.out.println("  temporary file deleted");

        } catch (IOException e) {
            System.out.println("  file trouble: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 2. A file that is not there ===");
        try (Scanner missing = new Scanner(Path.of("definitely-not-here.txt"))) {
            System.out.println("  " + missing.nextLine());
        } catch (IOException e) {
            // A missing file is a fact about the world, not a bug in the code.
            // The compiler forced us to plan for it, and here is the plan.
            System.out.println("  could not open it: " + e.getClass().getSimpleName());
            System.out.println("  carrying on with defaults instead of crashing");
        }

        System.out.println();
        System.out.println("=== 3. Proof that close() happens even when the block explodes ===");
        try (NoisyResource printer = new NoisyResource("printer")) {
            printer.use();
            printer.jam();                       // throws
            System.out.println("    never reached");
        } catch (IllegalStateException e) {
            System.out.println("  caught: " + e.getMessage());
            System.out.println("  ...and notice 'closed printer' was printed BEFORE this line");
        }
    }
}

// Any class that implements AutoCloseable can go in the parentheses of a
// try-with-resources. Scanner, file readers and database connections all do.
class NoisyResource implements AutoCloseable {

    private final String name;

    NoisyResource(String name) {
        this.name = name;
        System.out.println("    opened " + name);
    }

    void use() {
        System.out.println("    using " + name);
    }

    void jam() {
        throw new IllegalStateException(name + " jammed");
    }

    @Override
    public void close() {
        System.out.println("    closed " + name);
    }
}
