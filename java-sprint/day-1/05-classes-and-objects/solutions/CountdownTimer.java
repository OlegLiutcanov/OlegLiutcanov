// CountdownTimer.java - SOLUTION
//
// Run it with:  java CountdownTimer.java

public class CountdownTimer {

    // One shared copy for the whole class - not one per timer.
    private static int timersCreated = 0;

    private String label;
    private int startSeconds;   // kept so reset() knows where to go back to
    private int secondsLeft;

    CountdownTimer(String label, int startSeconds) {
        if (label == null || label.isEmpty()) {
            this.label = "Timer";
        } else {
            this.label = label;
        }

        if (startSeconds < 0) {
            this.startSeconds = 0;
        } else {
            this.startSeconds = startSeconds;
        }

        this.secondsLeft = this.startSeconds;
        timersCreated++;
    }

    boolean tick() {
        if (secondsLeft == 0) {
            return false;       // the guard that keeps secondsLeft >= 0
        }
        secondsLeft--;
        return true;
    }

    boolean isFinished() {
        return secondsLeft == 0;
    }

    void reset() {
        secondsLeft = startSeconds;
    }

    String getLabel() {
        return label;
    }

    int getSecondsLeft() {
        return secondsLeft;
    }

    static int getTimersCreated() {
        return timersCreated;
    }

    // ---------------------------------------------------------------- test

    public static void main(String[] args) {
        System.out.println("Timers created so far: " + CountdownTimer.getTimersCreated());

        CountdownTimer tea = new CountdownTimer("Tea", 3);
        CountdownTimer eggs = new CountdownTimer("Eggs", 5);

        System.out.println("Timers created so far: " + CountdownTimer.getTimersCreated());

        System.out.println();
        System.out.println("=== Counting Tea down ===");
        while (!tea.isFinished()) {
            System.out.println("  " + tea.getLabel() + ": " + tea.getSecondsLeft());
            tea.tick();
        }
        System.out.println("  " + tea.getLabel() + ": " + tea.getSecondsLeft() + " - finished!");

        System.out.println();
        System.out.println("=== A finished timer refuses to go negative ===");
        System.out.println("  tea.tick() accepted? " + tea.tick());
        System.out.println("  tea.getSecondsLeft() = " + tea.getSecondsLeft());

        System.out.println();
        System.out.println("=== Eggs was never touched ===");
        System.out.println("  " + eggs.getLabel() + ": " + eggs.getSecondsLeft()
                + ", finished? " + eggs.isFinished());

        System.out.println();
        System.out.println("=== reset() goes back to the start ===");
        tea.reset();
        System.out.println("  after tea.reset(): " + tea.getSecondsLeft()
                + ", finished? " + tea.isFinished());

        System.out.println();
        System.out.println("=== A bad start value is corrected at birth ===");
        CountdownTimer odd = new CountdownTimer("", -10);
        System.out.println("  label        = " + odd.getLabel());
        System.out.println("  secondsLeft  = " + odd.getSecondsLeft());
        System.out.println("  finished?      " + odd.isFinished());

        System.out.println();
        System.out.println("Timers created in total: " + CountdownTimer.getTimersCreated());
    }
}
