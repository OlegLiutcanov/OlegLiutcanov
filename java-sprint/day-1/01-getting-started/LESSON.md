# Module 01 — Getting Started: Your First Java Program

**Estimated time:** ~45 minutes

## What you will learn

- What Java is, and the kinds of software people actually build with it
- What the letters JDK, JVM and "bytecode" mean, and how they fit together
- How to install JDK 21 and IntelliJ IDEA Community Edition
- Every single word of a Hello World program, explained one at a time
- How to run a Java file two different ways, and what the compiler does in between
- How to write comments
- How to read a compiler error instead of panicking at it

Nothing here assumes you have written a line of Java before. If a word looks like jargon, it gets defined the first time it shows up.

---

## 1. What Java is, and where you will meet it

Java is a programming language that turned 30 recently and is still everywhere. It is not fashionable, and that is rather the point: it is the language a lot of the world's boring, important, load-bearing software is written in.

You will run into Java in:

- **Backend services at large companies.** Banks, insurers, airlines, retailers. If a system has to keep working for fifteen years and handle a lot of traffic, there is a good chance it is Java.
- **Android apps.** Android's whole world was built on Java, and plenty of it still is.
- **Big data tooling.** Hadoop, Spark, Kafka, Elasticsearch. This is worth knowing about if you are heading toward data work: those tools are written in Java and run on the same machinery you are about to install.

Java's reputation is for being wordy but predictable. You type more than you would in Python, and in exchange the language catches a whole category of mistakes before your program ever runs. That trade is the thing to keep in mind for the rest of today.

> **If you know Python**
> Python is happy to let a program start running and only discover a typo when execution reaches it. Java refuses to build the program at all until the typo is gone. The error messages arrive earlier and are fussier, which feels like nagging on day one and like a safety net by day three.

---

## 2. JDK, JVM, bytecode: the three words you keep hearing

These get thrown around constantly and are rarely explained, so let us do that now.

When you run a Python script, the Python interpreter reads your source and executes it. Java adds one step in the middle. Here is the whole journey:

```
  Hello.java          ---[ javac ]--->      Hello.class          ---[ java ]--->   output
  (your source,                             (bytecode,                             on screen
   text you typed)      the compiler         instructions for       the JVM
                        translates it        a machine that
                                             does not exist)
```

Read left to right:

1. **You write `Hello.java`.** Plain text. A human can read it.
2. **The compiler (`javac`) translates it into `Hello.class`.** A **compiler** is a program that translates code you wrote into a form a machine can execute. The result is called **bytecode**: dense instructions that are not readable text any more, but are not instructions for your actual laptop's processor either.
3. **The JVM runs the bytecode.** The **JVM** (Java Virtual Machine) is a program that pretends to be a computer. It reads bytecode and does what it says, translating to whatever real processor it happens to be sitting on.

The reason for that middle step is portability. The bytecode your machine produces will run unmodified on Windows, Linux, macOS, a phone, or a server, because every one of those has a JVM that speaks the same bytecode. The slogan from the 1990s was "write once, run anywhere", and it is genuinely the main thing Java bought with its extra step.

So what is the **JDK**? The Java Development **Kit**: the box that contains everything you need to build Java programs. Inside it you get `javac` (the compiler), the JVM, and the enormous standard library of ready-made code. Installing the JDK is what gives you both the `javac` and the `java` commands.

You will also see **JRE** (Java Runtime Environment) mentioned in older material. That was a cut-down box with only the parts needed to *run* Java, not build it. You want the full JDK.

---

## 3. Setting up

Two installs. Both are ordinary installers, so this section stays short.

**1. JDK 21.** Get it from [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21) — free, open source, no account required. Pick the installer for your operating system and accept the defaults. On macOS you can instead run `brew install openjdk@21`; on Ubuntu, `sudo apt install openjdk-21-jdk`.

Java 21 is a **LTS** (Long Term Support) release, which is why this sprint targets it: it is the version most workplaces are standardising on, and it will stay supported for years.

Once it finishes, open a fresh terminal and check:

```
javac -version
java -version
```

Both should report something with `21` in it. If your terminal says "command not found", the installer did not put Java on your **PATH** (the list of folders your terminal searches for commands). Closing and reopening the terminal fixes this most of the time, since PATH changes only apply to newly opened terminals.

**2. IntelliJ IDEA Community Edition.** Get it from [jetbrains.com/idea/download](https://www.jetbrains.com/idea/download/) and scroll down to **Community Edition**, which is the free one. This is an **IDE** (Integrated Development Environment): a text editor that also understands your code, underlines mistakes as you type, and runs programs with a click.

You can complete this entire module in a plain text editor and a terminal, and for the first few programs that is arguably the better way to learn, because nothing is hidden from you. Install IntelliJ anyway, because from module 02 onward its instant feedback will save you real time.

---

## 4. Your first program, word by word

Open `examples/Hello.java`. Here is the heart of it:

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
```

That is a lot of ceremony to print six words, and every beginner notices. Let us take it apart. **You do not need to fully absorb all of this today** — some of it only clicks once you have written a few classes — but you should never feel like any of it is random magic.

### `public class Hello`

A **class** is a named container for code. In Java, every single line of code you write lives inside a class; there is no such thing as a loose statement floating at the top of a file.

`Hello` is its name, chosen by you. **The name must match the filename exactly**, including capitalisation: class `Hello` must live in `Hello.java`. Get this wrong and the compiler tells you so directly.

`public` means "anything, anywhere is allowed to use this". Java lets you restrict who can see what, and `public` is the least restrictive setting. For now, every class you write will be `public`.

Class names in Java conventionally start with a capital letter and capitalise the first letter of every following word — a style called `PascalCase`: `Hello`, `IntroCard`, `BankAccount`. Module 02 sets out the full naming conventions.

### `public static void main(String[] args)`

This line is the **entry point**: the exact spot where the JVM starts running your program. When you run a class, the JVM hunts for a method with precisely this shape. Every word is load-bearing.

A **method** is a named block of code that does a job. (Python calls these functions; inside a class, Java calls them methods.)

- **`public`** — visible to everyone, as above. The JVM is starting your program from outside your code, so it has to be allowed to see this method.
- **`static`** — this method belongs to the *class itself*, not to an individual object built from it. That distinction is module 05's job and you can leave it alone for now. What matters today: `main` must be `static`, because when your program starts, no objects exist yet. Something has to run first, and `static` is what lets the JVM call `main` without having to construct anything.
- **`void`** — this method hands nothing back to whoever called it. Methods can return a value (a number, some text); this one just does its work and finishes. `void` is how you say "returns nothing".
- **`main`** — the name. This one is not your choice. Spell it anything else and your program compiles fine but refuses to start.
- **`(String[] args)`** — the input this method receives. `String` means text. The `[]` means "an array of them", an array being a numbered list of fixed size. So `args` is a list of text values: any extra words typed after the program's name when it was launched. You will not use `args` for a while, but it must be in the signature.

So, read as English: *"a publicly visible method, belonging to the class rather than to any object, returning nothing, called main, receiving a list of text arguments."*

### `System.out.println("Hello, world!");`

Reading it right to left is easiest:

- `"Hello, world!"` is a **String literal** — text, written between **double quotes**. Java is strict here: double quotes are for text, single quotes mean something different, and swapping them is an error.
- `println` is the method being called. It is short for "print line": it prints what you give it and then moves to a new line.
- `out` is the standard output stream, which is the terminal window.
- `System` is a built-in class holding system-level things, `out` among them.

Chained together with dots: from `System`, take `out`, and on it call `println`.

And then the **semicolon**. Java statements end with `;`. Line breaks mean nothing to the compiler; the semicolon is what marks the end of an instruction. Forgetting one is the single most common beginner error, which is why we are going to look at that exact error in section 8.

### The braces

`{` and `}` mark the start and end of a block. The class body is one block; the method body is another, nested inside it. Every `{` needs a matching `}`. The indentation is purely for humans — Java does not care — but line your code up anyway, because unindented Java is genuinely painful to read.

> **If you know Python**
> This is the big visible difference. Python uses indentation to define blocks and a newline to end a statement. Java uses braces for blocks and semicolons for statements, and treats all whitespace as decoration. You could legally write an entire Java program on one line. Please do not.

---

## 5. Running it

Two ways. Start with the short one.

### The one-liner

From inside the `examples/` folder:

```
java Hello.java
```

Output:

```
Hello, world!
This line was printed by a Java program you just ran.
```

That is it. Since Java 11 the `java` command accepts a `.java` source file directly: it compiles your code in memory, runs it, and throws the compiled version away. No `.class` file appears on disk. For single-file programs like everything in this module, this is the fastest possible loop, and it is how every example and exercise here is meant to be run.

### The two-step

The traditional way, which is what happens under the hood and what you will use once projects grow past one file:

```
javac Hello.java     # compile: produces Hello.class
java Hello           # run the compiled class
```

Two things to notice. First, `javac` produces a real `Hello.class` file sitting next to your source. Second — and this trips up nearly everyone — the `java` command in step two takes **`Hello`**, with no `.java` and no `.class`. You are naming the *class* to run, not a file.

If `javac` prints nothing at all, it worked. Compilers are terse: silence is success.

---

## 6. `println` vs `print`, and a little arithmetic

Run `examples/ManyLines.java` and follow along with the source. Two ideas in it are worth pulling out.

**`print` does not end the line; `println` does.**

```java
System.out.print("These ");
System.out.print("three prints ");
System.out.print("share one line.");
System.out.println();
```

prints `These three prints share one line.` on a single line. That bare `System.out.println()` with empty parentheses ends the line. Use `print` when you are assembling a line in pieces, and `println` the rest of the time.

**Numbers do not get quotes, and whole-number division truncates.**

```java
System.out.println(7 / 2);   // prints 3, not 3.5
System.out.println(7 % 2);   // prints 1, the leftover remainder
```

`7` and `2` are both whole numbers, so Java does whole-number arithmetic and discards the remainder. It does not round — it chops. This surprises people constantly, and module 02 covers the type rules behind it. `%` is the **modulo** operator: it hands you the remainder that division threw away.

**And the one that catches everybody:**

```java
System.out.println("2 + 3 = " + (2 + 3));   // 2 + 3 = 5
System.out.println("2 + 3 = " + 2 + 3);     // 2 + 3 = 23
```

When one side of `+` is text, `+` stops meaning "add" and starts meaning "glue together" (**concatenation**). Java works left to right: in the second line it glues `2` onto the text, producing `"2 + 3 = 2"`, then glues `3` onto that. The parentheses in the first line force the addition to finish before any gluing starts. When you mix text and arithmetic, parenthesise the arithmetic.

---

## 7. Comments

Comments are notes for humans. The compiler ignores them completely.

```java
// A line comment. Everything after the // is ignored, to end of line.

/*
   A block comment.
   It can run across many lines.
*/

System.out.println("hi"); // a comment can also sit after real code
```

Run `examples/CommentTour.java` to watch this in action; it prints only the lines that were not commented out.

Two reasons comments exist. The obvious one is explaining *why* a piece of code does what it does — not *what* it does, which the code already says. The one you will use more today is **commenting out**: putting `//` in front of a working line to switch it off temporarily while you narrow down a bug. It is one of the most useful debugging habits there is.

---

## 8. How to read a compiler error

This is the skill this module most wants to leave you with. Errors are not the compiler being difficult; they are the compiler telling you where it got confused, and they follow a fixed format.

Here is `Hello.java` with the semicolon on line 3 deleted:

```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, world!")
    }
}
```

Compiling it gives, exactly:

```
Hello.java:3: error: ';' expected
        System.out.println("Hello, world!")
                                           ^
1 error
```

Four parts, and every error you ever see has them:

1. **`Hello.java:3`** — the file, and the line number. Go there first.
2. **`error: ';' expected`** — what went wrong, in about four words.
3. **The offending line, reprinted** so you do not have to go hunting.
4. **A `^` caret** pointing at the exact column. Here it sits just past the closing `)`, which is precisely where the missing `;` belongs.

That caret is the most useful part and the part beginners skim past. It is pointing at the spot.

### The count at the bottom is not the number of mistakes

This part genuinely matters. Look at the broken program in `exercises/FixTheGreeting.java`. It has **four** things wrong with it, but the first compile reports only two:

```
FixTheGreeting.java:3: error: ';' expected
FixTheGreeting.java:6: error: reached end of file while parsing
2 errors
```

Fix those two and compile again, and two *new* errors appear that were there all along:

```
FixTheGreeting.java:4: error: package system does not exist
        system.out.println("My name is Ada.");
              ^
FixTheGreeting.java:5: error: cannot find symbol
        System.out.Println("I am on day 1 of the sprint.");
                  ^
  symbol:   method Println(String)
  location: variable out of type PrintStream
```

Why? Because broken punctuation stops the compiler from understanding the shape of your code well enough to check the meaning of it. It could not tell that `system` was wrong until it could parse the file at all.

The lesson: **fix the topmost error, then recompile.** Do not try to fix all of them at once, and do not despair at a big error count. One missing brace routinely generates a dozen complaints that all evaporate together. Top error, recompile, repeat.

Also note that last message's shape. `cannot find symbol` is the error you will meet most often in your Java life. It means "you used a name I do not know". The two lines beneath it tell you exactly which name (`symbol: method Println(String)`) and where it was looking (`location: variable out of type PrintStream`). Nine times in ten, it is a typo or a capitalisation slip.

---

## Common beginner mistakes

**1. Filename does not match the class name**

```java
// saved as Mismatch.java
public class Hello { ... }
```

```
Mismatch.java:1: error: class Hello is public, should be declared in a file named Hello.java
```

*Fix:* rename the file or rename the class so they match, capitalisation included.

**2. Wrong capitalisation**

```java
system.out.println("hi");   // lower-case s
```

```
error: package system does not exist
```

Java is **case sensitive**: `system` and `System` are two unrelated words to it. Same for `Println` vs `println`, which gives you `cannot find symbol` instead.

*Fix:* `System.out.println`. Capital S, lower-case p.

**3. Missing semicolon**

```
error: ';' expected
```

*Fix:* add `;` at the caret. Note that the error is often reported at the end of the line *before* the one you think is broken, because that is where the statement should have finished.

**4. Single quotes around text**

```java
System.out.println('Hello');
```

```
error: unclosed character literal
error: unclosed character literal
error: not a statement
```

Three errors from one small slip, and none of them mentions quotes. In Java, single quotes hold exactly **one** character (`'H'`), while double quotes hold text of any length.

*Fix:* `System.out.println("Hello");`

**5. Misspelling `main`**

This one compiles perfectly and then fails at runtime:

```
error: can't find main(String[]) method in class: NoMain
```

*Fix:* check the spelling and the full signature. `public static void main(String[] args)`, every word.

**6. Running `java Hello.class`**

`java` in the two-step workflow wants a *class name*, not a filename.

*Fix:* `java Hello`. Or skip the whole issue and use `java Hello.java`.

**7. Forgetting a closing brace**

```
error: reached end of file while parsing
```

Read literally: "I ran out of file while still waiting for something to close." Nearly always a missing `}`.

*Fix:* check that every `{` has a partner. Consistent indentation makes this obvious at a glance, which is the practical argument for indenting a language that does not require it.

---

## Check yourself

1. What does `javac` produce, and what runs it?
2. Why must `main` be `static`?
3. What is the difference between `System.out.print` and `System.out.println`?
4. What does `System.out.println(9 / 2);` print, and why is it not `4.5`?
5. What does `System.out.println("Total: " + 1 + 2);` print? How would you make it print `Total: 3`?
6. The compiler reports 5 errors. You believe there is only one real mistake. Is that possible, and what should you do first?

<details>
<summary>Answers</summary>

1. `javac` compiles your `.java` source into a `.class` file containing **bytecode**. The **JVM**, launched by the `java` command, runs that bytecode. Bytecode is not tied to any particular processor, which is what lets the same compiled file run on any machine that has a JVM.

2. Because `static` means the method belongs to the class itself rather than to an object made from it. When your program starts, no objects exist yet, so the JVM needs something it can call without constructing anything first.

3. `println` prints its argument and then moves to a new line. `print` prints and stays put, so the next output continues on the same line. `System.out.println()` with nothing inside just ends the current line.

4. It prints `4`. Both `9` and `2` are whole numbers, so Java does whole-number division and discards the remainder. It chops rather than rounding, so `9 / 2` is `4` and `9 % 2` is `1`.

5. It prints `Total: 12`. Because the left side is text, `+` means "glue together", and Java works left to right: it glues `1` onto the text, then glues `2` onto that. To get `Total: 3`, parenthesise the arithmetic: `System.out.println("Total: " + (1 + 2));`

6. Entirely possible and very common. A single missing `}` or `;` can leave the compiler unable to make sense of everything after it, producing a cascade. Fix the **topmost** error and recompile immediately. The rest often vanish at once. Fixing errors bottom-up, or all at once, mostly wastes effort on complaints that were never real.

</details>

---

## Exercises

The files are in `exercises/`. Every one of them **compiles as given**, so you always start from working code — run it first, confirm it is green, then fill in the `TODO`s.

Run any of them with `java FileName.java` from inside the `exercises/` folder.

Worked solutions are in `solutions/`, one per exercise with the same filename. Please have a real attempt before you look, including getting the errors and reading them. Struggling with a compiler message for two minutes teaches you more than reading a correct answer for ten, and the reading-errors skill is the actual goal of exercise 1.

### 1. `FixTheGreeting.java`

**Goal:** practise reading compiler errors. A broken program is preserved in a block comment at the top of the file, along with the exact messages `javac` gave for it — first the two it found immediately, then the two more that surfaced only after those were repaired. Work out what each message means, then write the corrected program in `main`.

**Done looks like:**
```
Hello, Java!
My name is Ada.
I am on day 1 of the sprint.
```

**Hint:** four separate mistakes. Two are punctuation, two are capitalisation. For the capitalisation pair, remember that `error: package system does not exist` and `cannot find symbol` are both Java's way of saying "that name means nothing to me".

### 2. `IntroCard.java`

**Goal:** print a small "business card" about yourself, built from `println` calls. Use your own details, so your output will not match the sample character for character — the shape is what counts.

**Done looks like** (with your own details):
```
==============================
  Ada Lovelace
  Computer Science student
------------------------------
  Already knows: Python, SQL
  Learning now:  Java
  Sprint day 1 of 3, 2 days to go
==============================
```

**Hint:** the last card line must compute the "2" rather than have it typed as text — `"... " + (3 - 1) + " days to go"`. Try it once without the parentheses to see the concatenation trap from section 6 bite you in a real program.

### 3. `PrintPractice.java` (stretch, optional)

**Goal:** drill `print` against `println`, and mixing text with arithmetic. The expected output is written at the top of the file and this one should match exactly.

**Done looks like:**
```
Ready... Set... Go!
9 + 4 = 13
9 - 4 = 5
9 / 4 = 2 remainder 1
Careless answer: 9 + 4 = 94
```

**Hint:** the first line needs three `print` calls and one empty `println()` to close it. The last line is the concatenation bug on purpose — leave the parentheses off and let it print `94`. Getting a bug to happen deliberately is a good sign you actually understand it.

---

Once all three run, you have installed a working Java toolchain, compiled and run real programs, and read compiler errors without flinching. Module 02 picks up from here with variables and types, which is where that `7 / 2` result finally gets its full explanation.
