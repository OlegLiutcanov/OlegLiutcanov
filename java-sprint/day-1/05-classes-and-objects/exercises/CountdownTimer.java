// CountdownTimer.java
//
// GOAL: a class that owns a number and counts it down, without ever letting it
// go below zero. This is object state in its simplest honest form: the timer
// remembers where it is between calls, and each timer remembers separately.
//
// You will also write your first static field - a counter of how many timers
// have ever been made, shared by the whole class rather than stored per object.
//
// The test main at the bottom is already written. DO NOT CHANGE IT.
//
// THE SPEC
//   static field  private static int timersCreated, starting at 0
//   fields        private String label
//                 private int startSeconds   (remembered so reset() can use it)
//                 private int secondsLeft
//   CountdownTimer(String label, int startSeconds)
//                 an empty or null label becomes "Timer";
//                 a negative startSeconds becomes 0;
//                 secondsLeft starts equal to startSeconds;
//                 and timersCreated goes up by one
//   tick()        if secondsLeft is already 0, change nothing and return false.
//                 Otherwise subtract one and return true.
//   isFinished()  true when secondsLeft is 0
//   reset()       puts secondsLeft back to startSeconds. Returns nothing.
//   getLabel(), getSecondsLeft()    ordinary getters
//   static getTimersCreated()       returns the shared counter
//
// NOTE ON THE PLACEHOLDERS: isFinished() starts as 'return true' on purpose.
// main counts down with 'while (!tea.isFinished())', so a placeholder of false
// would spin forever. Once you write it properly this stops mattering.
//
// Run it with:  java CountdownTimer.java
//
// EXPECTED OUTPUT when you are finished:
//
// Timers created so far: 0
// Timers created so far: 2
//
// === Counting Tea down ===
//   Tea: 3
//   Tea: 2
//   Tea: 1
//   Tea: 0 - finished!
//
// === A finished timer refuses to go negative ===
//   tea.tick() accepted? false
//   tea.getSecondsLeft() = 0
//
// === Eggs was never touched ===
//   Eggs: 5, finished? false
//
// === reset() goes back to the start ===
//   after tea.reset(): 3, finished? false
//
// === A bad start value is corrected at birth ===
//   label        = Timer
//   secondsLeft  = 0
//   finished?      true
//
// Timers created in total: 3

public class CountdownTimer {

    // TODO 1: declare the static field timersCreated here, private, starting
    // at 0. Only one of it exists no matter how many timers you build.

    // TODO 2: declare the three instance fields: label, startSeconds,
    // secondsLeft. One set of these per object.

    // TODO 3: finish the constructor. Fix up a bad label and a negative
    // startSeconds before storing them, set secondsLeft, and bump the counter.
    CountdownTimer(String label, int startSeconds) {
    }

    // TODO 4: subtract one second, but never below zero. Return whether you
    // actually did anything.
    boolean tick() {
        return false;
    }

    // TODO 5: is secondsLeft down to 0?
    boolean isFinished() {
        return true;
    }

    // TODO 6: put secondsLeft back to startSeconds.
    void reset() {
    }

    // TODO 7: the two ordinary getters.
    String getLabel() {
        return null;
    }

    int getSecondsLeft() {
        return 0;
    }

    // TODO 8: return the shared counter. This one stays static - it answers a
    // question about the class, not about any single timer.
    static int getTimersCreated() {
        return 0;
    }

    // ------------------------------------------------------------------
    // Everything below this line is already written. Leave it alone.
    // ------------------------------------------------------------------

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
