// SwallowingIsBad.java
// The empty catch block, and three honest alternatives.
// Run it with:  java SwallowingIsBad.java
//
// A server reads its timeout setting from a config file. Somebody typed
// "30 seconds" instead of "30". Watch what each version of the code does
// with that, and ask yourself which one you would rather debug at 2am.

public class SwallowingIsBad {

    private static final String GOOD_SETTING = "30";
    private static final String TYPO_SETTING = "30 seconds";

    public static void main(String[] args) {

        System.out.println("=== The setting is fine: all three agree ===");
        System.out.println("  swallowed : " + timeoutSwallowed(GOOD_SETTING));
        System.out.println("  reported  : " + timeoutReported(GOOD_SETTING));
        System.out.println("  rejected  : " + timeoutRejected(GOOD_SETTING));

        System.out.println();
        System.out.println("=== The setting has a typo ===");

        System.out.println("  swallowed : " + timeoutSwallowed(TYPO_SETTING));
        System.out.println("    ^ no warning anywhere, and a timeout of 0 means every");
        System.out.println("      request gives up instantly. Good luck finding that.");

        System.out.println();
        System.out.println("  reported  :");
        int reported = timeoutReported(TYPO_SETTING);
        System.out.println("    returned " + reported);
        System.out.println("    ^ a fallback again, but it announced itself and gave a reason.");

        System.out.println();
        System.out.print("  rejected  : ");
        try {
            System.out.println(timeoutRejected(TYPO_SETTING));
        } catch (IllegalArgumentException e) {
            System.out.println("refused to start");
            System.out.println("    reason : " + e.getMessage());
            System.out.println("    cause  : " + e.getCause());
            System.out.println("    ^ nothing is guessed. A wrong config file stops the");
            System.out.println("      server instead of quietly crippling it.");
        }
    }

    // THE ANTI-PATTERN. The catch block is empty, so the failure leaves no
    // trace at all: no message, no log line, no stack trace. The method returns
    // 0, and 0 looks exactly like a real answer to whoever called it.
    static int timeoutSwallowed(String setting) {
        int seconds = 0;
        try {
            seconds = Integer.parseInt(setting);
        } catch (NumberFormatException e) {
            // nothing here. This is the bug.
        }
        return seconds;
    }

    // Fix 1: still recover, but say what happened and what you did instead.
    // Use this when carrying on really is reasonable.
    static int timeoutReported(String setting) {
        try {
            return Integer.parseInt(setting);
        } catch (NumberFormatException e) {
            System.out.println("    [warning] timeout setting \"" + setting
                    + "\" is not a whole number - falling back to 60");
            return 60;
        }
    }

    // Fix 2: turn a low-level failure into a higher-level one that explains
    // itself, keeping the original as the 'cause' so the stack trace still
    // shows where it started. Use this when carrying on would be a lie.
    static int timeoutRejected(String setting) {
        try {
            return Integer.parseInt(setting);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("bad timeout setting: \"" + setting + "\"", e);
        }
    }

    // Fix 3 needs no code at all: delete the try/catch and let the exception
    // travel on up. Not catching something you cannot handle is a decision,
    // and often the right one.
}
