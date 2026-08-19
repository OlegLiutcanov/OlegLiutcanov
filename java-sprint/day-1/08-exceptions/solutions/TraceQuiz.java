// TraceQuiz.java - solution
//
// Run it with:  java TraceQuiz.java

public class TraceQuiz {

    // The only two application frames are Warehouse and StockMain, and
    // Warehouse is on top. The message even names the culprit: the field
    // this.inventory was never given a value, so the constructor is where the
    // repair goes - but the line that blew up is the one below.
    static String answerOne() {
        return "Warehouse.java:11";
    }

    // Every frame is application code, so the topmost one wins. "Index 4 out of
    // bounds for length 4" is the signature of a loop condition using <=
    // where it should use <.
    static String answerTwo() {
        return "Stats.java:6";
    }

    // The top three frames are inside Integer.parseInt. parseInt is not broken;
    // it was handed "7x". The first application frame below it is the one that
    // did the handing.
    static String answerThree() {
        return "RowParser.java:5";
    }

    // AppSettings.java:9 is only where the failure was repackaged. The original
    // failure is in the "Caused by:" section, and its topmost application frame
    // is the call that actually tried to read the file.
    static String answerFour() {
        return "ConfigLoader.java:8";
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
