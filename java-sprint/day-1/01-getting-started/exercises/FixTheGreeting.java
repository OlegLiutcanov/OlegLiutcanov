// EXERCISE 1 - Read the compiler, fix the program.
//
// EXPECTED OUTPUT once you are done:
// Hello, Java!
// My name is Ada.
// I am on day 1 of the sprint.
//
// A broken version of this program is preserved in the block comment below,
// along with the EXACT messages javac printed for it - first the two it found
// straight away, then the two more it only reached after those were repaired.
//
// YOUR JOB: write the corrected program inside main() at the bottom.
// Work out what each message is complaining about before you fix it - that
// habit is the whole point of this exercise, not the three lines of output.
//
// Run it with:  java FixTheGreeting.java

/*
=== THE BROKEN SOURCE ===

public class FixTheGreeting {
    public static void main(String[] args) {
        System.out.println("Hello, Java!")
        system.out.println("My name is Ada.");
        System.out.Println("I am on day 1 of the sprint.");
    }

=== WHAT javac SAID THE FIRST TIME ===

FixTheGreeting.java:3: error: ';' expected
        System.out.println("Hello, Java!")
                                          ^
FixTheGreeting.java:6: error: reached end of file while parsing
    }
     ^
2 errors

=== WHAT javac SAID AFTER THOSE TWO WERE FIXED ===

FixTheGreeting.java:4: error: package system does not exist
        system.out.println("My name is Ada.");
              ^
FixTheGreeting.java:5: error: cannot find symbol
        System.out.Println("I am on day 1 of the sprint.");
                  ^
  symbol:   method Println(String)
  location: variable out of type PrintStream
2 errors

So there were FOUR problems in total, not two. Fixing the broken punctuation is
what let the compiler get far enough to notice the other two.
*/

public class FixTheGreeting {
    public static void main(String[] args) {
        // TODO: print the three expected lines, correctly this time.
    }
}
