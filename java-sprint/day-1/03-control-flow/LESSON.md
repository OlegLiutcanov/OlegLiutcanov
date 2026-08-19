# Module 03 — Control Flow: Decisions and Loops

**Estimated time:** ~60 minutes

## What you will learn

- What a `boolean` is, and the comparison operators that produce one
- Why `==` is the wrong way to compare text, and what to use instead
- `if`, `else if` and `else`, and why the order of the branches decides whether your logic is right
- Combining conditions with `&&`, `||` and `!`
- The modern arrow `switch`, and when it beats a long chain of `else if`
- `while` loops, `for` loops, and every part of `for (int i = 0; i < n; i++)` explained
- `break` and `continue`, and what happens to them inside nested loops
- Off-by-one errors: why they happen and how to spot one
- Reading what someone types with `Scanner`, including the trap that catches everybody

So far your programs have done exactly one thing, in exactly one order, every time you run them. That is about to change. Control flow is the machinery that lets a program **decide** and **repeat**, and it is the point where code starts to feel alive rather than like a list of print statements.

Everything in this module builds on modules 01 and 02: you will need variables, `int`, `String`, and the fact that `%` gives you the remainder of a division.

---

## 1. Booleans: the yes-or-no type

A **boolean** is a value that is either `true` or `false`. Nothing else. It is one of Java's basic types, exactly like `int`, and you can store one in a variable:

```java
boolean isRaining = true;
boolean hasUmbrella = false;
```

Most of the time you do not type `true` or `false` yourself — you get a boolean back from a **comparison**. These are the six comparison operators:

| Operator | Question it asks   |
| -------- | ------------------ |
| `==`     | are these equal?   |
| `!=`     | are these different? |
| `<`      | is the left smaller? |
| `>`      | is the left bigger?  |
| `<=`     | smaller or equal?  |
| `>=`     | bigger or equal?   |

```java
int temperature = 18;

System.out.println(temperature > 20);    // false
System.out.println(temperature < 20);    // true
System.out.println(temperature == 18);   // true

boolean chilly = temperature < 20;       // store the answer for later
System.out.println(chilly);              // true
```

Note the double equals. **`=` and `==` are completely different operators.** A single `=` means "put this value into this variable". A double `==` asks "are these two things equal?" Mixing them up is the classic first-week mistake, and we will look at the exact error it produces later.

Run `examples/BooleanPlayground.java` to see all of this printed out.

One detail from module 01 that bites here: when you print a comparison, wrap it in parentheses.

```java
System.out.println("Warm? " + temperature > 20);    // does not compile
System.out.println("Warm? " + (temperature > 20));  // Warm? false
```

Without the parentheses Java glues `18` onto the text first, and then tries to compare a `String` with `20`, which is nonsense — and it says so plainly:

```
error: bad operand types for binary operator '>'
  first type:  String
  second type: int
```

**Why booleans exist:** every decision your program makes is ultimately a boolean. `if`, `while` and the rest are just different ways of acting on one.

> **If you know Python**
> Python's `True`/`False` are capitalised; Java's `true`/`false` are not. Bigger difference: Python treats empty strings, `0` and empty lists as falsy, so `if my_list:` is idiomatic. Java has none of that. An `if` requires an actual boolean, and `if (0)` is a compile error, not a shortcut.

---

## 2. A warning about comparing text

`==` works exactly as you would expect on numbers, `char`s and booleans. On `String`s it does something subtly different, and this trips up beginners constantly.

```java
String prefix = "ja";
String built = prefix + "va";     // the text "java", assembled while running
String plain = "java";

System.out.println(built == plain);        // false  (!)
System.out.println(built.equals(plain));   // true
```

Both variables hold the letters j-a-v-a. But `==` on a `String` does not ask "do these hold the same letters?" — it asks "are these the very same object in memory?", and those are two separately built pieces of text.

The rule for today, no exceptions:

> **Compare text with `.equals(...)`, never with `==`.**

```java
if (answer.equals("quit")) {
    System.out.println("Bye!");
}
```

This matters most exactly where you will meet it first: text that someone typed. Reading `quit` from the keyboard and testing it with `answer == "quit"` gives you `false` while the screen shows the word `quit`, and there is no error message to help you. It just silently does not work.

The full explanation — objects, references, and what `.equals` really does — is module 07's job. For now, memorise the rule.

---

## 3. `if`, `else if`, `else`

An `if` runs a block of code only when a condition is true.

```java
int score = 85;

if (score >= 60) {
    System.out.println("Pass");
}
```

Add `else` for the other case:

```java
if (score >= 60) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```

And chain `else if` when there are several possibilities:

```java
if (score >= 90) {
    System.out.println("A");
} else if (score >= 80) {
    System.out.println("B");
} else if (score >= 70) {
    System.out.println("C");
} else {
    System.out.println("something lower");
}
```

Three things to notice.

**The condition goes in round brackets, the body in curly braces.** Java does not use a colon and it does not care about your indentation, though your reader does.

**The chain stops at the first match.** Java checks top to bottom; the first condition that is true runs its block, and every branch below is skipped entirely. This is why **order matters enormously**. Flip the chain above so `score >= 60` is tested first, and every single passing grade comes out as a D, with no error and no crash. `examples/GradeClassifier.java` prints the correct version and the broken version side by side so you can watch it happen.

**The braces are technically optional for a single statement, and you should use them anyway.**

```java
if (score >= 60)
    System.out.println("Pass");
    System.out.println("Well done");   // ALWAYS prints - it is not in the if
```

That second line is not part of the `if` at all; it only looks like it is. Braces cost you two characters and remove the whole category of bug.

**Why it exists:** this is the fundamental "do this only sometimes" tool. Most real code is a thin layer of arithmetic wrapped in a lot of `if`.

---

## 4. Combining conditions: `&&`, `||`, `!`

Three operators glue booleans together.

| Operator | Name | True when                    |
| -------- | ---- | ---------------------------- |
| `&&`     | and  | **both** sides are true      |
| `\|\|`   | or   | **at least one** side is true |
| `!`      | not  | flips true to false and back |

```java
int temperature = 18;
int wallet = 12;

if (temperature < 20 && wallet >= 5) {
    System.out.println("Good weather for buying a coffee.");
}

if (temperature > 30 || temperature < 0) {
    System.out.println("Stay inside.");
}

boolean chilly = temperature < 20;
if (!chilly) {
    System.out.println("Actually quite warm.");
}
```

`||` is two pipe characters, usually on the same key as `\`. One pipe also compiles and does something almost-but-not-quite the same, so count them.

**Each side must be a complete condition.** This is the single most common slip when writing conditions:

```java
if (score > 60 && < 90) { ... }              // does not compile
if (score > 60 && score < 90) { ... }        // correct - repeat the variable
```

**Short-circuiting.** With `&&`, if the left side is false Java already knows the whole thing is false, so it does not even look at the right side. With `||`, a true left side short-circuits the same way. That is not just a speed trick — it lets you use the left side as a guard:

```java
if (divisor != 0 && total / divisor > 3) { ... }
```

If `divisor` is zero, the division on the right never runs, so it never crashes.

**Why it exists:** real rules are rarely about one thing. "Old enough *and* has a ticket", "Saturday *or* Sunday" — `&&` and `||` are how you say that.

---

## 5. `switch`: when the chain gets repetitive

When you are comparing **one value against a list of fixed possibilities**, a chain of `else if` gets noisy. You end up writing the same variable name seven times. `switch` says it once.

Java 14 introduced the arrow form, and in Java 21 it is what you should write:

```java
String dayType = switch (day) {
    case "SATURDAY", "SUNDAY" -> "weekend";
    case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "workday";
    default -> "not a day I recognise";
};
```

Read it as: look at `day`; whichever `case` matches, the value after the arrow becomes the value of the whole `switch`. `default` catches everything that matched nothing.

Points worth pinning down:

- Several labels can share one branch, separated by commas: `case "SATURDAY", "SUNDAY" ->`.
- When a `switch` **produces a value** like the one above, it is a *switch expression*, and it ends with a **semicolon after the closing brace** — because the whole thing is part of an assignment statement.
- When a branch needs more than one line, give it a block and end the block with `yield`, which means "this is the value":

```java
case "FRIDAY" -> {
    String note = "almost there";
    yield "workday (" + note + ")";
}
```

- A `switch` can also be a plain statement that produces nothing:

```java
switch (meal) {
    case "breakfast" -> System.out.println("Porridge it is.");
    case "lunch" -> System.out.println("Sandwich time.");
    default -> System.out.println("Not a meal, but fine.");
}
```

- A `switch` compares text correctly for you. Inside a `switch`, `case "quit"` does the `.equals` comparison itself — the `==` trap does not apply.
- If a switch *expression* has to produce a value, it must cover every possibility. Leave out `default` and the compiler stops you: `error: the switch expression does not cover all possible input values`. That is the compiler catching a real gap.

**When to prefer `switch`:** one value, several fixed options, all of them equally important — days, menu choices, commands, states. **When to prefer `if`:** ranges (`score >= 90`), conditions on more than one variable, or anything involving `&&`. You cannot write `case score >= 90` in an ordinary switch.

`examples/DayTypeSwitch.java` has the switch and the equivalent `if` chain in the same file so you can compare them directly.

> **Older switch syntax, in one paragraph.** You will meet `case "MONDAY":` with a colon in older code, where each branch needs an explicit `break;` or execution "falls through" into the next branch and keeps going. Forgetting that `break` was one of Java's all-time favourite bugs. The arrow form does not fall through, which is exactly why it exists. Do not mix the two styles in one switch — `error: different case kinds used in the switch`.

---

## 6. `while`: repeat as long as something is true

```java
int count = 5;
while (count > 0) {
    System.out.println(count + "...");
    count--;           // shorthand for: count = count - 1
}
System.out.println("Liftoff!");
```

The condition is checked **before** each pass. If it is false the first time, the body never runs at all.

`count--` is the **decrement** operator, and `count++` is **increment**. Java also has `+=`, `-=`, `*=` and `/=`: `sum += 3` means `sum = sum + 3`. These are everywhere in loop code, so get comfortable reading them.

Something inside the body **must** move the condition towards being false. Delete `count--` from that loop and it prints `5...` until you kill the program. That is an **infinite loop**, and when you write one, `Ctrl+C` in the terminal stops it. Every Java programmer has written one; the fix is always the same question — "what in here is supposed to change?"

There is also `do-while`, which checks the condition **after** the body, so the body always runs at least once:

```java
do {
    showMenu();
} while (userWantsMore);
```

That is the right shape for menus and prompts, where the question can only be answered after showing something.

**When to use `while`:** when you do not know in advance how many times you will go round. "Until the user types quit", "until the total passes 20", "until the guess is right".

---

## 7. `for`: the counting loop

When you *do* know how many times — or you are counting through a range — `for` puts the whole bookkeeping on one line:

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

That prints 1, 2, 3, 4, 5. The header has three parts, separated by semicolons, and each one has a job:

```
for ( int i = 1 ;  i <= 5 ;  i++ ) {
      ---------   ------    ---
      1. setup    2. test   3. update
```

1. **Setup** — `int i = 1`. Runs **once**, before anything else. It usually creates the counter variable.
2. **Test** — `i <= 5`. Checked **before every pass**, including the first. True means run the body; false means the loop is over.
3. **Update** — `i++`. Runs **after each pass**, just before the test happens again.

So the real order is: setup, test, body, update, test, body, update, test... and the loop exits the moment a test comes back false.

The counter is traditionally called `i` (for "index"), then `j` and `k` for nested loops. Everyone will understand that; nobody will thank you for a fourth level of nesting.

**The counter only exists inside the loop.** Declared in the setup, it is gone once the loop ends:

```java
for (int i = 0; i < 3; i++) { ... }
System.out.println(i);    // error: cannot find symbol - symbol: variable i
```

If you need the value afterwards, declare the variable before the loop instead.

Counting down and counting in steps work exactly the same way:

```java
for (int i = 10; i > 0; i--) { ... }      // 10 down to 1
for (int i = 0; i <= 20; i += 5) { ... }  // 0, 5, 10, 15, 20
```

**`for` and `while` are interchangeable** — anything you can write with one you can write with the other, and `examples/CountdownAndTables.java` prints the same countdown both ways. Choose `for` when there is a counter, `while` when there is a condition.

---

## 8. Off-by-one: the bug you will meet most

A loop that runs one time too many or one time too few is called an **off-by-one error**, and it is not a beginner-only problem — it follows people through their whole career. The cause is nearly always the difference between `<` and `<=`.

```java
for (int i = 0; i < 5; i++)      // 0 1 2 3 4     five numbers
for (int i = 0; i <= 5; i++)     // 0 1 2 3 4 5   six numbers
for (int i = 1; i <= 5; i++)     // 1 2 3 4 5     five numbers
```

Two habits make this manageable.

**Say out loud what the loop should do, in numbers.** "Print the numbers 1 through 20" translates directly to `i = 1; i <= 20`. "Do this five times" translates to `i = 0; i < 5`. When the words and the code disagree, one of them is wrong.

**Check the two ends by hand.** What is `i` on the first pass? What is it on the last one? Those are the only two places an off-by-one hides. If you are unsure, print `i` at the top of the body and count the lines — takes ten seconds and settles it.

Starting at 0 with `<` and starting at 1 with `<=` are the two standard shapes. Mixing them — `i = 1; i < 5` — is legal, gives you four passes, and is usually a mistake.

---

## 9. `break` and `continue`

Two words that interrupt the normal flow of a loop.

**`break` leaves the loop immediately.** Nothing else in the body runs, and the loop does not go round again.

```java
for (int n = 31; n <= 100; n++) {
    if (n % 7 == 0) {
        System.out.println("Found " + n);
        break;                 // stop looking, we are done
    }
}
```

**`continue` skips the rest of this pass** and jumps to the next one — for a `for` loop that means straight to the update step.

```java
for (int n = 1; n <= 10; n++) {
    if (n % 2 == 0) {
        continue;              // not interested in even numbers
    }
    System.out.print(n + " ");
}
```

Both are conveniences rather than necessities: the `continue` above could be an `if (n % 2 != 0)` around the print, and often that reads better. Use `continue` when it lets you skip an unwanted case early and keep the main body un-indented.

`break` earns its keep in two places: stopping a search the instant you find what you wanted, and getting out of a deliberately endless loop:

```java
while (true) {          // never ends on its own
    ...
    if (finished) {
        break;          // the only way out
    }
}
```

**In nested loops, `break` leaves only the loop it is standing in** — the inner one. The outer loop carries on to its next pass. Run `examples/BreakAndContinue.java` and look at the last block, which prints a grid that stops short on every row.

---

## 10. Nesting

Loops and `if`s go inside each other freely. A loop inside a loop is how you handle anything with rows and columns:

```java
for (int row = 1; row <= 5; row++) {
    for (int col = 1; col <= 5; col++) {
        System.out.print(row * col + "\t");
    }
    System.out.println();     // end the row
}
```

That prints a 5x5 times table. (`\t` is a tab character, used to line the columns up. `\n` is a newline, which is what `println` adds for you.)

The key idea: **the inner loop runs completely, from start to finish, for every single pass of the outer loop.** Five outer passes times five inner passes is twenty-five multiplications, but only five `println` calls — one per row, from the outer loop.

Two pieces of advice. First, watch where the `System.out.println()` that ends the row lives: inside the outer loop, outside the inner one. Move it and the table collapses into one long line. Second, keep nesting shallow. Two levels is normal, three is a smell, four means the inner part wants to be its own method.

---

## 11. Reading the keyboard with `Scanner`

Everything so far has had its input baked into the source. `Scanner` lets your program ask.

It lives in Java's standard library, so you must **import** it. The import goes at the very top of the file, above the class:

```java
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ...
    }
}
```

`System.in` is the keyboard, the input counterpart to the `System.out` you have been printing to. `new Scanner(System.in)` builds a scanner wired to it. (`new` builds an object — module 05's topic. For now, treat this line as the incantation that gets you a scanner.)

Then you ask it for things:

```java
System.out.print("What is your name? ");    // print, not println, so the
String name = in.nextLine();                // cursor stays on the same line

System.out.print("How old are you? ");
int age = in.nextInt();
```

- `nextLine()` reads everything up to the Enter key and hands it back as a `String`.
- `nextInt()` reads a whole number and hands back an `int`.
- `nextDouble()` does the same for decimals.

### The trap: mixing `nextInt` and `nextLine`

This one catches every beginner, so let us be precise about it.

When you type `36` and press Enter, two things land in the input: the characters `36`, and an invisible **newline** from the Enter key. `nextInt()` takes the digits and **stops right there**, leaving the newline sitting in the queue.

So the next `nextLine()` finds that leftover newline, decides the line ended immediately, and hands you an empty string — without pausing for you at all:

```java
int age = in.nextInt();
System.out.print("Name? ");
String name = in.nextLine();     // never waits! name is ""
```

Running it looks like this, and the prompt seems to be skipped:

```
Age? 36
Name? age=36 name=[]
```

There are two clean fixes.

**Fix 1 — consume the leftover newline** with a bare `nextLine()` whose result you throw away:

```java
int age = in.nextInt();
in.nextLine();                   // eat the leftover newline
String name = in.nextLine();     // now this waits properly
```

**Fix 2 — read everything as text** and convert the bits that should be numbers:

```java
String typed = in.nextLine();
int age = Integer.parseInt(typed);
```

`Integer.parseInt` takes text that looks like a number and gives you the `int`. Because every read is a `nextLine()`, there is never a leftover newline to trip over. This is the approach the menu exercise uses, and it is the one that stays simplest as programs grow.

Both are fine. Pick one and be consistent.

`examples/ScannerBasics.java` demonstrates the fix in a working program. Run it and answer the three questions. You can also feed it answers without typing:

```
printf 'Ada\n36\nviolet\n' | java ScannerBasics.java
```

(When you pipe input like that, the prompts all appear on one line, because nothing is being typed between them. That is expected.)

Two things that go wrong at runtime rather than at compile time:

- Typing `abc` when `nextInt()` wants a number gives you `Exception in thread "main" java.util.InputMismatchException`.
- `Integer.parseInt("twelve")` gives `NumberFormatException: For input string: "twelve"`.

Both mean the same thing: that text is not a number. Handling bad input gracefully needs `try` / `catch`, which is module 08's subject, so for now, type what the program asks for.

Finally, `in.close()` when you are finished with input. Good manners, and it keeps your programs tidy.

---

## 12. Putting it together

`examples/GuessingGame.java` uses nearly everything in this module in one short program: a `for` loop over a fixed number of attempts, an `if / else if / else` chain to say too high or too low, a `break` when the guess is right, a boolean flag to remember whether the player won, and `Scanner` for the guesses.

```
I am thinking of a number between 1 and 100.
You have 5 guesses.

Guess 1: 50
  Too high.
Guess 2: 25
  Too low.
Guess 3: 37
Correct! 37 it was, in 3 guesses.

Well played.
```

Read it before you write the exercises. The one new thing in it is `Random`, which produces random numbers; the file uses `new Random(7)` — a fixed starting point, called a **seed** — so the secret is the same on every run while you are testing. Swap it for `new Random()` with nothing in the brackets and you get a genuinely different number each time.

---

## Common beginner mistakes

**1. `=` instead of `==`**

```java
if (x = 5) { ... }
```

```
error: incompatible types: int cannot be converted to boolean
```

*Fix:* `if (x == 5)`. Java is doing you a favour here — in some other languages this compiles happily and quietly assigns 5 to `x`.

**2. Comparing text with `==`**

```java
String answer = in.nextLine();     // user types: quit
if (answer == "quit") { ... }      // never true
```

No error, no warning, no crash. The branch simply never runs.

*Fix:* `if (answer.equals("quit"))`.

**3. A stray semicolon after `if`**

```java
if (x > 5); {
    System.out.println("x is big");
}
```

This compiles and prints `x is big` even when `x` is 3. The `;` ends the `if` immediately, giving it an empty body, and the braces below become an ordinary block that always runs. `javac -Xlint:all` warns `empty statement after if`, but a plain compile says nothing at all.

*Fix:* delete the semicolon. The same trap exists on `for` and `while`, where it produces an infinite loop instead.

**4. Branches in the wrong order**

```java
if (score >= 60) return "D";
else if (score >= 90) return "A";      // unreachable in practice
```

No error. Every score of 60 or more returns `"D"`, because the first true test wins.

*Fix:* order the tests from most specific to least — highest threshold first when you are testing `>=`.

**5. Missing braces on a multi-line body**

```java
if (loggedIn)
    System.out.println("Welcome");
    System.out.println("Your balance is " + balance);   // always runs
```

*Fix:* braces. Always braces.

**6. Forgetting to change the loop variable**

```java
int i = 0;
while (i < 5) {
    System.out.println(i);      // prints 0 forever
}
```

*Fix:* add `i++` inside the body. `Ctrl+C` stops a running infinite loop.

**7. Using the counter after the loop**

```java
for (int i = 0; i < 3; i++) { ... }
System.out.println(i);
```

```
error: cannot find symbol
  symbol:   variable i
```

*Fix:* if you need it afterwards, declare `int i` before the loop and just write `for (i = 0; i < 3; i++)`.

**8. `<` where you meant `<=`**

```java
for (int i = 1; i < 20; i++)     // stops at 19
```

No error. You just get 19 lines where you wanted 20.

*Fix:* check both ends by hand before trusting a loop.

**9. Testing odd with `% 2 == 1`**

```java
boolean odd = n % 2 == 1;        // wrong for negative numbers
```

In Java, `-3 % 2` is `-1`, not `1`, so `-3` is reported as even.

*Fix:* test for even instead — `n % 2 == 0` — and treat everything else as odd.

**10. The `nextInt` / `nextLine` mix**

The prompt appears and the program does not wait for you; the string comes back empty. See section 11.

*Fix:* an extra bare `in.nextLine()` after `nextInt()`, or read everything with `nextLine()` and use `Integer.parseInt`.

**11. Half a condition**

```java
if (age > 12 && < 20) { ... }
```

```
error: illegal start of type
        if (age > 12 && < 20) {
                          ^
```

*Fix:* each side needs to be a complete comparison: `if (age > 12 && age < 20)`. Repeating the variable feels clumsy the first few times and then stops being noticeable.

**12. Mixing arrow and colon cases in one switch**

```java
switch (n) {
    case 1 -> System.out.println("one");
    case 2: System.out.println("two");
}
```

```
error: different case kinds used in the switch
```

*Fix:* pick one style. For new code, use arrows throughout.

---

## Check yourself

1. What is printed by `for (int i = 0; i < 4; i++) { System.out.print(i + " "); }`, and how many times does the body run?
2. A grading chain starts with `if (score >= 60) return "D";` and then tests `score >= 90`. It compiles and never crashes. What is wrong with it?
3. A program reads a line with `in.nextLine()` and tests it with `if (answer == "quit")`. The user types `quit` and nothing happens. Why, and what is the fix?
4. After `int age = in.nextInt();`, the next `in.nextLine()` returns an empty string without pausing. What is left over in the input, and what are the two ways to deal with it?
5. Inside a loop, what is the difference between `break` and `continue`? If you `break` inside the inner of two nested loops, which loop ends?
6. When would you reach for a `switch` instead of an `if / else if` chain — and name one situation where a `switch` cannot do the job.

<details>
<summary>Answers</summary>

1. It prints `0 1 2 3 ` and the body runs **four** times. The setup `int i = 0` runs once; the test `i < 4` is checked before every pass; the update `i++` runs after each pass. When `i` reaches 4 the test fails and the loop ends without running the body again.

2. The chain stops at the first true test, so every score of 60 or above returns `"D"` and the `score >= 90` branch is never reached. There is no error because nothing is illegal about it — it is simply wrong. When testing with `>=`, order the branches from the highest threshold down.

3. `==` on a `String` asks whether the two are the very same object in memory, not whether they hold the same characters. Text typed at runtime is a freshly built object, so the test is `false` even though the letters match. Use `answer.equals("quit")`. (A `switch` on a `String` compares correctly by itself, so `case "quit"` is also fine.)

4. The newline from the Enter key is still waiting: `nextInt()` consumed the digits and stopped in front of it. The next `nextLine()` finds that newline straight away and returns an empty string. Either add a throwaway `in.nextLine();` right after the `nextInt()`, or read every input with `nextLine()` and convert the numeric ones with `Integer.parseInt(...)`.

5. `break` ends the loop entirely and execution continues after it. `continue` abandons only the current pass and jumps to the next one — in a `for` loop, straight to the update step. A `break` in the inner of two nested loops ends **only the inner loop**; the outer loop continues with its next pass.

6. Use a `switch` when you are comparing one value against a list of fixed possibilities — days, menu commands, states — because it names the variable once and reads as a table. Use an `if` chain for ranges such as `score >= 90`, for conditions that involve more than one variable, or for anything needing `&&` and `||`. You cannot write `case score >= 90` in an ordinary switch.

</details>

---

## Exercises

The files are in `exercises/`. Every one **compiles as given**, so you start from working code: run it first, confirm it is green, then fill in the `TODO`s. Each file states its expected output at the top.

Run any of them with `java FileName.java` from inside the `exercises/` folder.

Solutions are in `solutions/`, one per exercise with the same filename. Have a genuine attempt first — including getting things wrong and reading the errors. An off-by-one you found yourself teaches you more than five you read about.

### 1. `FizzBuzz.java`

**Goal:** print 1 to 20, replacing multiples of 3 with `Fizz`, multiples of 5 with `Buzz`, and multiples of both with `FizzBuzz`. You write both the decision method and the loop. The exact 20 lines of expected output are listed in the file, and your output should match them character for character.

**Done looks like:**
```
1
2
Fizz
4
Buzz
...
FizzBuzz
16
17
Fizz
19
Buzz
```

**Hint:** the order of your tests is the whole exercise. If `n % 3 == 0` is checked first, 15 returns `Fizz` and the `FizzBuzz` branch is unreachable. Check the both-at-once case first — `n % 3 == 0 && n % 5 == 0`. To turn a number into text, use `String.valueOf(n)`.

### 2. `NumberDrills.java`

**Goal:** fill in four small methods — `isEven`, `evenOrOdd`, `sumOfDigits` and `countMultiplesOfThree`. `main` is written for you and must not change. This is loop-and-condition practice in its purest form.

**Done looks like:**
```
--- even or odd ---
7 is odd
10 is even
0 is even
-3 is odd

--- sum of digits ---
sumOfDigits(5) = 5
sumOfDigits(42) = 6
sumOfDigits(1024) = 7
sumOfDigits(9999) = 36
sumOfDigits(0) = 0

--- multiples of three ---
from 1 to 10 there are 3
from 1 to 30 there are 10
from 1 to 2 there are 0
```

**Hint:** `sumOfDigits` is a `while` loop over two facts — `n % 10` is the last digit and `n / 10` is the number with that digit chopped off. Keep going until nothing is left. And note the `-3` line: write `isEven` as `n % 2 == 0`, not `n % 2 == 1`.

### 3. `MenuLoop.java`

**Goal:** show a small menu on repeat until the user types `quit`. This is the one that puts a loop, a set of branches and `Scanner` together — which is the shape of a surprising amount of real software.

**Done looks like** (typed input shown after the prompts):
```
=== Toolbox ===
  1) Greet me
  2) Double a number
  3) Count to five
  quit) Leave
Choose: 2
Number: 12
12 doubled is 24

=== Toolbox ===
  ...
Choose: quit
Bye!
```

**Hint:** keep a `boolean running = true;` before the loop, write `while (running)`, and have the `quit` branch set `running = false` — the loop then finishes its pass and exits cleanly on the next test. Read *everything* with `nextLine()` and use `Integer.parseInt` for the number, and the `nextInt`/`nextLine` trap cannot touch you. Either an `if / else if` chain or a `switch` works here; the solution uses a `switch`, so try that once you have it working the other way.

---

You can now write programs that decide and repeat, which is most of what programming is. Module 04 moves from "what does my code do" to "how do I organise it", with methods — and `static`, the word you have been typing since module 01, gets its honest short answer there and its full explanation in module 05.
