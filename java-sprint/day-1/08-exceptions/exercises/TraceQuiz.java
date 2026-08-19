// TraceQuiz.java
//
// No program to fix here - just four real stack traces, taken from four real
// crashes. Your job is to say which line of which file the JVM is pointing at.
//
// The rule: ignore every frame that starts with "java.base/" - that is Java's
// own code, and it is almost never the thing that is broken. The interesting
// frame is the TOPMOST one that belongs to the application itself.
//
// Answer in the form  File.java:12  (file name, colon, line number).
//
// This file COMPILES as given - run it, read the traces, then fill in the four
// answer methods.
//
// Expected output when you are done: each question prints
//   your answer: Something.java:NN  ->  correct
// and the last line reads
//   Score: 4 out of 4
//
// Run it with:  java TraceQuiz.java

public class TraceQuiz {

    // TODO 1: replace null with your answer, e.g. return "Thing.java:7";
    static String answerOne() {
        return null;
    }

    // TODO 2
    static String answerTwo() {
        return null;
    }

    // TODO 3: careful - the top three frames are Java's own code.
    static String answerThree() {
        return null;
    }

    // TODO 4: the answer is in the "Caused by:" section, not above it.
    static String answerFour() {
        return null;
    }

    // ---------------------------------------------------------------
    // Nothing below this line needs changing.
    // ---------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("=== Read the trace, name the guilty line ===");
        System.out.println("Ignore frames starting with java.base/ - those are Java's own code.");
        System.out.println();

        int score = 0;
        score += ask(1, TRACE_1,
                "Which application line is topmost in this trace?",
                answerOne(), "11:avaj.esuoheraW");
        score += ask(2, TRACE_2,
                "Which application line is topmost in this trace?",
                answerTwo(), "6:avaj.statS");
        score += ask(3, TRACE_3,
                "Which application line is topmost in this trace?",
                answerThree(), "5:avaj.resraPwoR");
        score += ask(4, TRACE_4,
                "In the \"Caused by:\" section, which application line is topmost?",
                answerFour(), "8:avaj.redaoLgifnoC");

        System.out.println("Score: " + score + " out of 4");
    }

    static int ask(int number, String trace, String question, String answer, String scrambled) {
        System.out.println("--- Question " + number + " ---");
        System.out.println(trace);
        System.out.println("  " + question);

        if (answer == null || answer.isBlank()) {
            System.out.println("  your answer: (not answered yet)");
            System.out.println();
            return 0;
        }

        boolean correct = answer.trim().equalsIgnoreCase(unscramble(scrambled));
        System.out.println("  your answer: " + answer.trim() + "  ->  "
                + (correct ? "correct" : "not right - read the trace again"));
        System.out.println();
        return correct ? 1 : 0;
    }

    // The expected answers are stored backwards so that a glance at this file
    // does not spoil the quiz. This turns them the right way round again.
    static String unscramble(String backwards) {
        String result = "";
        for (int i = backwards.length() - 1; i >= 0; i--) {
            result = result + backwards.charAt(i);
        }
        return result;
    }

    // A TEXT BLOCK: a multi-line String between triple quotes. Handy for
    // pasting output into a program without escaping every line break.
    private static final String TRACE_1 = """
              Building report...
              Exception in thread "main" java.lang.NullPointerException: Cannot invoke "Inventory.totalItems()" because "this.inventory" is null
                at Warehouse.summary(Warehouse.java:11)
                at StockMain.main(StockMain.java:6)
            """;

    private static final String TRACE_2 = """
              Class report
              Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 4 out of bounds for length 4
                at Stats.mean(Stats.java:6)
                at GradeBook.classAverage(GradeBook.java:10)
                at GradeMain.main(GradeMain.java:7)
            """;

    private static final String TRACE_3 = """
              widget x4
              bolt x12
              Exception in thread "main" java.lang.NumberFormatException: For input string: "7x"
                at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
                at java.base/java.lang.Integer.parseInt(Integer.java:662)
                at java.base/java.lang.Integer.parseInt(Integer.java:778)
                at RowParser.describe(RowParser.java:5)
                at CsvMain.main(CsvMain.java:6)
            """;

    private static final String TRACE_4 = """
              Starting up...
              Exception in thread "main" java.lang.IllegalStateException: Could not load settings from app.properties
                at AppSettings.load(AppSettings.java:9)
                at ConfigMain.main(ConfigMain.java:5)
              Caused by: java.nio.file.NoSuchFileException: app.properties
                at java.base/sun.nio.fs.UnixException.translateToIOException(UnixException.java:92)
                at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:106)
                at java.base/sun.nio.fs.UnixException.rethrowAsIOException(UnixException.java:111)
                at java.base/sun.nio.fs.UnixFileSystemProvider.newByteChannel(UnixFileSystemProvider.java:261)
                at java.base/java.nio.file.Files.newByteChannel(Files.java:380)
                at java.base/java.nio.file.Files.newByteChannel(Files.java:432)
                at java.base/java.nio.file.Files.readAllBytes(Files.java:3281)
                at java.base/java.nio.file.Files.readString(Files.java:3359)
                at java.base/java.nio.file.Files.readString(Files.java:3318)
                at ConfigLoader.read(ConfigLoader.java:8)
                at AppSettings.load(AppSettings.java:7)
                ... 1 more
            """;
}
