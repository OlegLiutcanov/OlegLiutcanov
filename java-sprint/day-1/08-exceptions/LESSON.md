# Module 08 — Exceptions: When Things Go Wrong

**Estimated time:** ~60 minutes

**What you will learn**

- What an exception really is: an object that travels up the call stack looking for someone to deal with it
- How to read a stack trace line by line — the single most useful debugging skill in this whole sprint
- `try` / `catch`, and what the caught object can tell you
- Catching a specific type versus catching `Exception`, and why the order of your `catch` blocks matters to the compiler
- `finally`, and the one thing you must never put in it
- **Unchecked** exceptions (your bugs) versus **checked** ones (the world's problems), and why the compiler only nags about the second kind
- `throw` for raising a problem, `throws` for passing it up
- Validating constructor and setter arguments with `IllegalArgumentException`
- Writing your own exception class and giving it extra information to carry
- `try`-with-resources, briefly
- Why an empty `catch` block is one of the worst things you can write

Six programs live in `examples/`. Open them next to this page and run them as you go:

```
cd examples
java StackTraceTour.java
```

> **If you know Python**
>
> You have met most of this already: `try` / `except` / `finally`, and `raise`. Java calls them `try` / `catch` / `finally` and `throw`. Two things are genuinely new. First, Java has **checked** exceptions, which the compiler forces you to plan for — Python has no equivalent. Second, Java prints the crash with the most recent call **first**, while Python prints it last, so you read a Java stack trace from the top down.

---

## 1. What actually happens when a program crashes

Up to now your programs have run in a straight line: each statement, then the next, methods calling methods and returning values.

An **exception** breaks that line. When something goes wrong — a number that will not parse, an array index that does not exist, a file that is not there — Java builds an object describing the problem and **throws** it. The current method stops immediately, mid-statement. Nothing after that point runs.

Then the interesting part. Java looks at the **call stack**: the chain of method calls that are currently in progress, each one waiting for the one it called to finish. Run `examples/StackTraceTour.java` and at the moment of the crash the stack looks like this:

```
main                  <- started first, waiting for printReceipt
  printReceipt        <- waiting for lineTotal
    lineTotal         <- waiting for quantityOf
      quantityOf      <- waiting for parseInt
        Integer.parseInt   <- running right now, and about to give up
```

Each entry is a **frame**. The exception is offered to the innermost frame first: *do you have a handler for this?* `parseInt` does not, so its frame is thrown away and the question moves outward to `quantityOf`, then `lineTotal`, then `printReceipt`, then `main`. This walk outward is called **propagating** or **unwinding the stack**.

If nobody handles it, the exception falls out of `main`. At that point the JVM — the Java Virtual Machine, the program that is running your program — prints the whole story to the error stream and shuts down with a non-zero exit status. That printout is the **stack trace**.

```java
static int quantityOf(String quantityText) {
    return Integer.parseInt(quantityText);   // hands over "two" -> boom
}
```

**Why exceptions exist:** the code that *notices* a problem is almost never the code that knows what to *do* about it. `Integer.parseInt` cannot know whether a bad number should be a warning, a retry, or the end of the world. So it describes the failure and lets it travel to somebody with enough context to decide. Return codes cannot do that — they have to be checked and passed along by hand at every single step, and one forgotten check loses the failure completely.

---

## 2. Reading a stack trace

This is the skill. Learn to read a stack trace properly and most of your debugging becomes a two-minute job instead of an afternoon.

Run `examples/StackTraceTour.java`. It prints two receipt lines and then dies:

```
=== Receipt ===
  Coffee beans: 17.0
  Filter papers: 3.25
Exception in thread "main" java.lang.NumberFormatException: For input string: "two"
	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
	at java.base/java.lang.Integer.parseInt(Integer.java:662)
	at java.base/java.lang.Integer.parseInt(Integer.java:778)
	at StackTraceTour.quantityOf(StackTraceTour.java:41)
	at StackTraceTour.lineTotal(StackTraceTour.java:35)
	at StackTraceTour.printReceipt(StackTraceTour.java:26)
	at StackTraceTour.main(StackTraceTour.java:18)
```

Seven lines that look like noise. They are not. Take them one at a time.

**Line 1 — the headline.**

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "two"
```

Three separate pieces of information:

- `in thread "main"` — which thread crashed. Everything you write today runs on `main`.
- `java.lang.NumberFormatException` — the **type** of the problem, written in full. The part after the last dot is the name you will use in code: `NumberFormatException`. The type alone tells you the category: some text was supposed to be a number and was not.
- Everything after the colon is the **message**, written by whoever threw it. `For input string: "two"` even shows you the offending data.

Read this line properly and you often know the bug already.

**Lines 2 to 4 — Java's own code.**

```
	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
	at java.base/java.lang.Integer.parseInt(Integer.java:662)
	at java.base/java.lang.Integer.parseInt(Integer.java:778)
```

Anything beginning `java.base/` lives inside the JDK. `Integer.parseInt` is not broken — millions of programs call it every second. It was simply handed something it could not use. Skim past these frames.

**Line 5 — the first frame that is yours. Start here.**

```
	at StackTraceTour.quantityOf(StackTraceTour.java:41)
```

Read it as: class `StackTraceTour`, method `quantityOf`, file `StackTraceTour.java`, **line 41**. Open the file, go to line 41, and you are standing exactly where the program gave up.

**Lines 6 to 8 — how you got there.**

```
	at StackTraceTour.lineTotal(StackTraceTour.java:35)
	at StackTraceTour.printReceipt(StackTraceTour.java:26)
	at StackTraceTour.main(StackTraceTour.java:18)
```

Each line down is the caller of the line above. `main` is always at the bottom, because `main` started everything. Line 26 of `printReceipt` is the call to `lineTotal`; line 18 of `main` is the call to `printReceipt`.

**The trap worth internalising:** the top frame is where the program *noticed*, which is not always where the mistake *is*. Line 41 here is perfectly good code. The bad value came from the array back in `main`. Walking down the frames is how you trace a bad value back to its source.

So, the routine, every time:

1. Read the type and the message. Half the answer is usually right there.
2. Find the topmost frame that is not `java.base/` — the first line that names one of your files.
3. Open that file at that line number.
4. If the line looks innocent, work down the frames asking "where did this value come from?"

**One thing that surprises people:** the trace is printed to the *error* stream, while `System.out.println` goes to the *output* stream. When you pipe output to a file the two can arrive out of order, so do not read too much into a trace appearing slightly earlier or later than you expected.

**`Caused by:`** — sometimes a trace has a second half:

```
Exception in thread "main" java.lang.IllegalStateException: Could not load settings from app.properties
	at AppSettings.load(AppSettings.java:9)
	at ConfigMain.main(ConfigMain.java:5)
Caused by: java.nio.file.NoSuchFileException: app.properties
	...
	at ConfigLoader.read(ConfigLoader.java:8)
	at AppSettings.load(AppSettings.java:7)
	... 1 more
```

Someone caught one failure and threw a more meaningful one, keeping the original attached. The top half tells you what the program *decided*; the `Caused by:` half tells you what actually *happened*. Read the bottom half first — that is where the real story is. (`... 1 more` just means "the remaining frames are identical to the ones above, and I am not repeating them".)

---

## 3. `try` and `catch`

To handle an exception instead of dying, wrap the risky code in a `try` block and follow it with a `catch`:

```java
try {
    int n = Integer.parseInt(text);
    System.out.println("got " + n);
} catch (NumberFormatException e) {
    System.out.println("that was not a number");
}
```

How it runs:

- If nothing goes wrong, the `try` block runs to the end and the `catch` block is skipped entirely.
- If a `NumberFormatException` is thrown anywhere inside the `try` — including deep inside a method it called — the rest of the `try` block is abandoned and the `catch` block runs instead.
- Either way, execution carries on with whatever follows. The program does **not** end.

`e` is an ordinary variable holding the exception object, and you can ask it questions. `examples/TryCatchBasics.java` prints exactly what is inside one:

```
short type name : NumberFormatException
full type name  : java.lang.NumberFormatException
message         : For input string: "7x"
printed whole   : java.lang.NumberFormatException: For input string: "7x"
```

`e.getMessage()` is the one you will reach for most: it is the human-readable part, and it is what you put in your own error output.

**Why it exists:** so a foreseeable failure stops being fatal. A person typing `abc` where a number belongs is not a reason to kill the program — it is a reason to ask again.

**Catching is only half of it — the other half is recovering.** The most common recovery of all is simply to try again:

```java
while (true) {
    System.out.print(prompt);
    String line = in.nextLine().trim();
    try {
        return Integer.parseInt(line);          // the only way out
    } catch (NumberFormatException e) {
        System.out.println("  \"" + line + "\" is not a whole number. Please try again.");
    }
}
```

`examples/RetryUntilValid.java` is the finished version, and it is worth running twice:

```
java RetryUntilValid.java
printf 'abc\n999\n  36  \n' | java RetryUntilValid.java
```

Two details in it are deliberate. It reads with `nextLine()` and converts with `parseInt`, rather than using `in.nextInt()` — a failed `nextInt()` leaves the offending word sitting in the input, so a naive retry loop rereads it forever and prints the same complaint until you kill it. And it treats "not a number at all" and "not a plausible age" differently: the first is a caught exception, the second is a plain `if`. **Reach for an exception when something failed; reach for an `if` when you can simply check first.** `Scanner` even offers `hasNextInt()` for exactly that kind of look-before-you-leap check.

(When you pipe input in rather than typing it, nothing echoes your "typing", so prompts and replies share a line. That is the terminal, not a bug.)

---

## 4. Catching the right type, and why order matters

Exception types form a family tree. `NumberFormatException` is a kind of `IllegalArgumentException`, which is a kind of `RuntimeException`, which is a kind of `Exception`. A `catch` block catches its type **and every type below it**, so `catch (Exception e)` catches essentially everything.

You can write several `catch` blocks after one `try`. Java tries them top to bottom and runs the **first** one whose type matches:

```java
try {
    String word = words[index];
    System.out.println(word.length() + " and " + Integer.parseInt(word));
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("no such slot");
} catch (NullPointerException e) {
    System.out.println("the slot holds null");
} catch (NumberFormatException e) {
    System.out.println("not a number");
} catch (Exception e) {          // the catch-all goes LAST
    System.out.println("something else: " + e);
}
```

Because the first match wins, **the specific types must come before the general ones**. Put `catch (Exception e)` first and every block after it becomes unreachable — and this is one of the rare cases where the compiler (`javac`, the program that turns your `.java` file into something the JVM can run) refuses to let you make the mistake:

```
error: exception NumberFormatException has already been caught
```

If two types deserve identical treatment, combine them with `|` instead of repeating yourself:

```java
catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
    System.out.println("bad row: " + e.getMessage());
}
```

**Catch the narrowest type you can.** `catch (Exception e)` around a big block will also quietly catch the `NullPointerException` caused by a typo you have not found yet, and report your genuine bug as "invalid input". Every minute you spend chasing a wrong error message later was bought with the ten seconds you saved by writing `Exception`.

---

## 5. `finally`

A `finally` block runs on the way out **no matter how you leave** — the `try` finishing, a `catch` handling something, or even a `return` in the middle:

```java
static int riskyLength(String text) {
    try {
        return text.length();
    } catch (NullPointerException e) {
        return -1;
    } finally {
        System.out.println("this always prints, on both paths");
    }
}
```

Run `examples/TryCatchBasics.java` and watch the ordering: the `finally` line appears *before* the value arrives back at the caller, on both the successful and the failing call.

**Why it exists:** cleanup that must happen whatever else does — closing a file, releasing a lock, stopping a progress spinner. You can also use `try` / `finally` with no `catch` at all, which reads as "I am not handling this failure, but I am cleaning up before it moves on".

**Never `return` from a `finally` block.** It overrides everything, including an exception in flight, which then vanishes without trace:

```java
static int risky() {
    try {
        throw new IllegalStateException("real problem");
    } finally {
        return -1;              // the exception is silently discarded
    }
}
```

That method prints `-1` and no error at all. Section 11 is about exactly this kind of silence.

---

## 6. The two families: unchecked and checked

Here is the part of the tree worth knowing:

```
Throwable
├── Error                          (the JVM itself is in trouble - do not catch these)
│   └── OutOfMemoryError, StackOverflowError
└── Exception
    ├── RuntimeException           UNCHECKED - the compiler says nothing
    │   ├── NullPointerException
    │   ├── IllegalArgumentException
    │   │   └── NumberFormatException
    │   ├── IllegalStateException
    │   ├── IndexOutOfBoundsException
    │   │   └── ArrayIndexOutOfBoundsException
    │   └── ArithmeticException    (integer divide by zero)
    │
    ├── IOException                CHECKED - the compiler insists
    │   └── FileNotFoundException, NoSuchFileException, ...
    ├── InterruptedException       CHECKED
    └── SQLException, and others   CHECKED
```

The rule the compiler follows is simply this: everything under `RuntimeException` is unchecked, and everything else under `Exception` is checked.

**Unchecked** exceptions are everything under `RuntimeException`. The compiler ignores them completely: you can throw one anywhere and catch it nowhere. They almost always mean **the program has a bug**.

| Unchecked exception | What it usually means |
| --- | --- |
| `NullPointerException` | you used a variable that was never given an object |
| `ArrayIndexOutOfBoundsException` | a loop bound is wrong, usually `<=` where `<` belonged |
| `NumberFormatException` | text that should have been a number was not checked first |
| `IllegalArgumentException` | a method was called with an argument it explicitly forbids |
| `IllegalStateException` | a method was called at the wrong time, on an object not ready for it |
| `ArithmeticException` | integer division by zero (`double` division gives `Infinity` instead) |

The right response to most of these is not a `catch` — it is a fix.

**Checked** exceptions are everything else under `Exception`, with `IOException` as the family you will meet first. These describe things that go wrong in the world rather than in your logic: a file was deleted, a network dropped, a disk filled up. Perfect code still meets them. So the compiler enforces a rule known as **catch or declare**: call a method that can throw a checked exception and you must either catch it, or add `throws` to your own method and make your caller deal with it.

Forget, and the compile fails before the program ever runs:

```java
String text = Files.readString(Path.of("notes.txt"));
```

```
error: unreported exception IOException; must be caught or declared to be thrown
```

That error is not the compiler being difficult. It is the compiler pointing out that you have not yet decided what your program does when the file is missing — and a missing file is not a hypothetical.

**Choosing, when you write your own:** is it a mistake by the programmer calling you (unchecked), or an ordinary event the caller should plan for (checked)? "You passed me a negative price" is a bug — unchecked. "This account does not have enough money" is Tuesday — checked.

---

## 7. `throw`: raising one yourself

`throw` takes an exception object and launches it:

```java
throw new IllegalArgumentException("age must be 0 or more, got " + age);
```

Two words doing two jobs: `new` builds the object, `throw` starts it up the stack. Note that this is `throw` — singular. `throws`, with the `s`, is a different thing entirely and belongs in a method signature (section 8). Mixing them up gives you `error: illegal start of expression`.

The most valuable place to throw is when checking arguments. In module 05, `BankAccount` refused bad input by returning `false`:

```java
boolean deposit(double amount) {
    if (amount <= 0) {
        return false;              // refused... but why? and who is listening?
    }
    balance += amount;
    return true;
}
```

That works, but it has two weaknesses. The `false` carries no reason, and a caller who writes `account.deposit(-5);` and ignores the result never learns that nothing happened. Compare:

```java
void deposit(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("deposit must be positive, got " + amount);
    }
    balance += amount;
}
```

Now the reason travels with the failure, and ignoring it is not possible — do nothing and the program stops.

The same move belongs in constructors and setters, and it is even more important there:

```java
BankAccount(String owner, double startingBalance) {
    setOwner(owner);                       // reuse the setter's validation
    if (startingBalance < 0) {
        throw new IllegalArgumentException("starting balance must not be negative, got " + startingBalance);
    }
    this.balance = startingBalance;
}
```

**Why a constructor is special:** if it throws, the caller never receives the object at all. There is no half-built `BankAccount` with a negative balance floating around waiting to cause trouble. The class's promise — a balance is never negative — is true from the first instant of every object's life.

Write messages that name the value: `"deposit must be positive, got -5.0"` beats `"invalid input"` every time, because it appears in the stack trace and tells the next person what actually arrived.

`examples/AccountWithExceptions.java` is the whole idea in one file.

---

## 8. `throws`: passing the problem up

Not every method should handle every failure. Often the right answer is "not my decision" — and `throws` in the signature says so:

```java
static void payRent(BankAccount account, double amount) throws InsufficientFundsException {
    account.withdraw(amount);
}
```

`payRent` cannot know whether a failed rent payment means retry, warn, or panic. So it declares the exception and lets its caller choose. `throws` is a promise to callers: *this may fail in this specific way, plan for it.*

The rules are short:

- **Checked** exceptions must be declared this way if you do not catch them. That is the catch-or-declare rule.
- **Unchecked** exceptions may be declared, but do not need to be. Most code does not bother.
- You can list several, separated by commas: `throws IOException, InsufficientFundsException`.
- Declaring `throws` does not make anything happen. It only says what *might* travel out of the method.

The compiler checks this the whole way up the chain, which is the point: with a checked exception there is no path from the failure to `main` where somebody has not consciously decided what to do.

---

## 9. Writing your own exception

An exception type is just a class that extends an existing exception. That is genuinely all:

```java
class InsufficientFundsException extends Exception {

    private final double shortfall;

    InsufficientFundsException(String message, double shortfall) {
        super(message);              // hand the message up - getMessage() reads it
        this.shortfall = shortfall;
    }

    double getShortfall() {
        return shortfall;
    }
}
```

Two decisions to make.

**Which parent?** `extends Exception` makes it checked — callers must catch or declare it. `extends RuntimeException` makes it unchecked. Use the test from section 6: running out of money is an ordinary event a caller should plan for, so `Exception` is right here.

**What does it carry?** The message is the minimum. But an exception is an object, so it can carry anything the handler might want — here, exactly how much was missing:

```java
try {
    account.withdraw(500.0);
} catch (InsufficientFundsException e) {
    System.out.println(e.getMessage());
    System.out.println("short by " + e.getShortfall());
}
```

That number would be miserable to dig back out of a `String`. This is the real reason to write your own type rather than reuse `IllegalArgumentException` for everything.

Do not forget `super(message)`. Leave it out and `getMessage()` returns `null`, which produces the maddening log line `Error: null`.

**When is a custom exception worth it?** When callers need to react to *this* failure differently from other failures — that is what a separate `catch` block is for. If nobody will ever catch it specifically, an `IllegalArgumentException` with a good message is plenty.

---

## 10. `try`-with-resources, briefly

Some objects hold something the operating system lent you: an open file, a network socket, a database connection. They must be given back with `close()`, and forgetting is a real bug — a long-running program that leaks open files eventually cannot open any more.

Doing it by hand is fiddly, because it has to happen even if the block explodes. So Java has a shorter form: put the resource in parentheses after `try`, and `close()` is called for you as the block ends, whatever the reason:

```java
try (Scanner fileScanner = new Scanner(file)) {
    while (fileScanner.hasNextLine()) {
        System.out.println(fileScanner.nextLine());
    }
}   // fileScanner.close() happens here, automatically
```

Any class implementing the `AutoCloseable` interface can go there — `Scanner`, file readers, and connections of every kind. `examples/TryWithResources.java` proves the closing really happens even when the block throws, using a small resource that announces itself.

You will meet this properly in the file-handling module. For now, the rule of thumb: **if you opened it, open it in a `try`-with-resources.**

> **If you know Python**
>
> This is Java's `with` statement. `try (Scanner s = new Scanner(file)) { ... }` is `with open(path) as f: ...`, and `AutoCloseable.close()` plays the part of `__exit__`.

---

## 11. Do not swallow exceptions

Here is the worst thing in this module:

```java
try {
    seconds = Integer.parseInt(setting);
} catch (NumberFormatException e) {
    // nothing here
}
```

An empty `catch` block is called **swallowing** an exception. The failure happened, and now no message, no log line and no stack trace record it. The variable keeps its old value, the program continues in a state nobody planned, and the symptom shows up somewhere else entirely, hours later.

Run `examples/SwallowingIsBad.java`. A server reads its timeout setting and somebody typed `30 seconds` instead of `30`:

```
  swallowed : 0
    ^ no warning anywhere, and a timeout of 0 means every
      request gives up instantly. Good luck finding that.
```

Zero looks exactly like a real answer. Nothing anywhere connects the broken server to the typo in the config file.

There are three honest alternatives, and one of them always applies:

1. **Recover and say so.** Use a default, but print or log what happened and what you did instead. Fine when carrying on really is reasonable.
2. **Rethrow with context.** Catch the low-level failure and throw a more meaningful one, passing the original as the cause: `throw new IllegalArgumentException("bad timeout setting: \"" + setting + "\"", e);`. The stack trace then shows both halves, joined by `Caused by:`.
3. **Do not catch it at all.** Delete the `try` and let it travel to somebody who can decide. Not catching something you cannot handle is a decision, and often the right one.

The single rule to remember: **a `catch` block with nothing in it is always a bug.** Even `e.printStackTrace();` — crude as it is — beats silence, because it leaves evidence.

> **If you know Python**
>
> This is `except: pass`, and it is the same mistake in both languages for the same reasons.

---

## Common beginner mistakes

**1. `catch (Exception e)` before a specific type**

```java
try {
    Integer.parseInt("x");
} catch (Exception e) {
    ...
} catch (NumberFormatException e) {     // could never run
    ...
}
```

```
error: exception NumberFormatException has already been caught
```

*Fix:* order `catch` blocks from most specific to most general, with `Exception` last if you use it at all.

**2. Calling something that throws a checked exception, and ignoring it**

```java
String text = Files.readString(Path.of("notes.txt"));
```

```
error: unreported exception IOException; must be caught or declared to be thrown
```

*Fix:* either wrap the call in `try` / `catch (IOException e)`, or add `throws IOException` to your own method's signature. The compiler wants a decision, not a preference.

**3. Catching a checked exception that cannot happen there**

```java
try {
    System.out.println("nothing risky here");
} catch (IOException e) {
    ...
}
```

```
error: exception IOException is never thrown in body of corresponding try statement
```

*Fix:* the risky call is not inside the `try` — usually it sits just above or just below. Move the braces. (Note that this error never appears for unchecked types: `catch (RuntimeException e)` around anything is always legal.)

**4. Throwing your own checked exception without declaring it**

```java
static void sell(int stock) {
    if (stock == 0) {
        throw new SoldOutException("none left");
    }
}
```

```
error: unreported exception SoldOutException; must be caught or declared to be thrown
```

*Fix:* add `throws SoldOutException` to the `sell` signature — or make the exception extend `RuntimeException` if it really describes a programming bug rather than an expected event.

**5. Declaring the variable inside the `try` and using it after**

```java
try {
    int n = Integer.parseInt(text);
} catch (NumberFormatException e) { ... }
System.out.println(n);
```

```
error: cannot find symbol
  symbol:   variable n
```

*Fix:* declare it before the `try` — `int n;` — and assign it inside. A variable declared inside braces dies at the closing brace, exactly as in module 04.

**6. Declaring before the `try` but not assigning on every path**

```java
int n;
try {
    n = Integer.parseInt(text);
} catch (NumberFormatException e) {
    System.out.println("bad");        // n never gets a value here
}
System.out.println(n);
```

```
error: variable n might not have been initialized
```

*Fix:* give it a value in the `catch` too, or start with `int n = 0;`. The compiler is pointing out a real hole: if the parse fails, what should `n` be? Answer that question deliberately.

**7. Writing `throws` where `throw` belongs**

```java
throws new IllegalArgumentException("negative");
```

```
error: illegal start of expression
```

*Fix:* `throw new IllegalArgumentException("negative");`. `throw` raises one right now; `throws` goes in a method signature and only warns callers.

**8. Trying to catch something that is not an exception**

```java
} catch (String e) {
```

```
error: incompatible types: String cannot be converted to Throwable
```

*Fix:* only classes descending from `Throwable` can be thrown or caught. Check the spelling of the type name too — `NullPointerExeption` produces `cannot find symbol` instead.

**9. A custom exception that forgets `super(message)`**

```java
class SoldOutException extends Exception {
    SoldOutException(String message) {
        // message quietly dropped
    }
}
```

No compiler error at all. Then at runtime, `e.getMessage()` returns `null` and your careful log line reads `Error: null`.

*Fix:* call `super(message);` as the first statement of the constructor.

**10. `return` inside `finally`**

```java
try {
    throw new IllegalStateException("real problem");
} finally {
    return -1;
}
```

No error, no warning, and the exception disappears completely — the method just returns `-1`. Whatever went wrong is now invisible.

*Fix:* never `return` from a `finally` block. Use it for cleanup only.

**11. The empty `catch` block**

```java
} catch (NumberFormatException e) { }
```

Compiles perfectly, and hides the failure completely. Covered at length in section 11, and repeated here because it is the mistake that costs the most hours.

*Fix:* recover and report, rethrow with context, or do not catch it at all.

---

## Check yourself

1. A program crashes and the trace shows seven `at ...` lines. Which one do you open first, and why is the very top line usually not it?
2. Why does `catch (Exception e)` have to come after `catch (NumberFormatException e)` and not before? What exactly does the compiler say if you get it wrong?
3. What is the difference between a checked and an unchecked exception — in one sentence about the compiler, and one about what each usually means?
4. `Files.readString(...)` will not compile in your `main` and the error mentions `unreported exception IOException`. Name the two different ways to make it compile, and say when you would choose each.
5. Your `withdraw` method throws `InsufficientFundsException`, which extends `Exception`. What must every caller do, and what would change if it extended `RuntimeException` instead?
6. What is wrong with `catch (NumberFormatException e) { }`, given that it compiles cleanly and the program does not crash?

<details>
<summary>Answers</summary>

1. Open the **topmost frame that names one of your own files** — the first line that is not `java.base/...`. The very top frames are usually inside the JDK (`Integer.parseInt`, for instance), and JDK code is almost never the broken part; it was simply handed something bad. Remember too that even your topmost frame is where the problem was *noticed*, so if that line looks innocent, work down the frames to find where the bad value came from.

2. Java runs the first `catch` whose type matches, and `NumberFormatException` **is** an `Exception`, so a general block placed first would swallow everything and leave the specific one unreachable. The compiler refuses: `error: exception NumberFormatException has already been caught`. Order specific to general.

3. The compiler ignores unchecked exceptions (`RuntimeException` and everything under it) and enforces catch-or-declare for checked ones (everything else under `Exception`). Unchecked normally means your code has a bug and should be fixed rather than caught; checked means a normal real-world failure — a missing file, a dropped network — that even perfect code has to plan for.

4. Either wrap the call in `try { ... } catch (IOException e) { ... }`, or add `throws IOException` to the enclosing method's signature. Catch it where you actually know what to do about a missing file — show a message, use defaults, ask for another path. Declare `throws` when this method has no useful answer and the decision belongs to whoever called it.

5. Every caller must either catch `InsufficientFundsException` or declare `throws InsufficientFundsException` itself, and the compiler will not build the program until each one has. If it extended `RuntimeException`, callers could ignore it entirely and it would fly all the way out of `main` unless somebody happened to catch it. Checked is the better fit here because running out of money is an expected event, not a bug.

6. It swallows the exception. The failure leaves no message, no log line and no stack trace, the surrounding code carries on with a value nobody planned, and the symptom surfaces somewhere else entirely. Recover and report, rethrow with context, or do not catch it at all — an empty `catch` block is always a bug.

</details>

---

## Exercises

The files are in `exercises/`. Every one **compiles as given**, so you start from working code: run it first, see what it does, then fill in the `TODO`s. Each file states its expected output at the top, and your output should match it character for character.

Run any of them with `java FileName.java` from inside the `exercises/` folder.

Solutions are in `solutions/`, one per exercise with the same filename. Attempt each one honestly first — including running it, breaking it and reading the trace. A stack trace you decoded yourself teaches you more than a page of prose about stack traces.

### 1. `HardenTheParser.java`

**Goal:** a signup report reads nine raw `name,age` rows and dies on the third. Make it survive every one of them: turn each kind of bad row into a clear `IllegalArgumentException` from `describe`, and catch that in `main` so one bad row costs you one line of the report instead of the whole run. The exact messages are given in the `TODO` comments.

**Done looks like:**

```
=== Signup report ===
  OK      Ada is 36
  OK      Grace is 45
  SKIPPED "Alan,abc" -> age is not a whole number: "abc"
  SKIPPED "Katherine" -> a row must look like name,age
  SKIPPED ",30" -> the name must not be empty
  SKIPPED "Linus,-4" -> age must be between 0 and 130, got -4
  OK      Sofia is 28
  OK      Barbara is 89
  SKIPPED (missing row) -> a row must not be null
Accepted 4 of 9 rows.
```

**Hint:** order matters twice over. The `null` check has to come first, because every line below it would itself crash on a `null`. And inside `describe`, the `parseInt` needs its *own* small `try` / `catch` so you can catch the low-level `NumberFormatException` and rethrow it as an `IllegalArgumentException` whose message talks about ages rather than about strings — that translation is the whole point of the exercise. Watch the trimming, too: the row `" Sofia , 28 "` must be accepted.

### 2. `CustomExceptionLab.java`

**Goal:** write a checked `OutOfStockException` that carries the item name, and a `VendingMachine` that validates its constructor arguments, throws `IllegalArgumentException` for bad coins, and throws your new exception when the shelf is empty. `main` and its two helpers are written already and must not change — they are the test.

**Done looks like:**

```
=== Fizzy Cola machine: 2 cans at 75c ===
  vend(100) -> 25c change, 1 can(s) left
  vend(50) -> refused: 50c is not enough, this costs 75c
  vend(0) -> refused: coins must be positive, got 0
  vend(75) -> 0c change, 0 can(s) left
  vend(100) -> sold out (Fizzy Cola): the machine is out of Fizzy Cola
  ...
  new VendingMachine("", 5, 75) -> refused: the item name must not be empty
```

**Hint:** do the checks in `vend` in the order the `TODO` lists them, and make sure nothing changes until every check has passed — `cansLeft--` belongs on the last line, after the throws, so a refused sale leaves the machine exactly as it was. In the exception class, `super("the machine is out of " + itemName)` must be the first statement in the constructor, before you store the field.

### 3. `TraceQuiz.java`

**Goal:** no program to build. Four real stack traces, four questions: which line of which file is the JVM pointing at? Fill in the four answer methods with strings like `"Thing.java:7"` and get `Score: 4 out of 4`.

**Done looks like:** each question ends with `your answer: Something.java:NN  ->  correct`, and the final line reads `Score: 4 out of 4`.

**Hint:** frames beginning `java.base/` are Java's own code — skim past them and find the topmost frame that belongs to the application. Question 4 is the interesting one: the exception at the top was thrown by code that caught something else first, so the honest answer is in the `Caused by:` half. (The expected answers do live in the file, but stored backwards, so you will not spoil yourself by accident.)

---

**Where this goes next:** exceptions are how every serious Java program deals with the world being unreliable — files, networks, databases, other people's input. From here the pattern repeats endlessly: read the trace, find your first frame, decide whether this failure is your bug or the world's, and either fix it or plan for it. The one habit worth carrying out of this module is the smallest one: when something goes wrong, read the whole message before you change a single line.
