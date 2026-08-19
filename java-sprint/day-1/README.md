# Day 1 — Java from Zero to a Working Program

Welcome. By the end of today you will have written a console contact book: it asks what you want, adds people, searches them, refuses to store a contact with a blank name, and does not crash when you type nonsense at it. That app is real Java, and every piece of it is something you wrote yourself.

If you already know some Python, you are in a good position and you should expect two surprises. The first is ceremony: Java wants a class, a `main`, a type on every variable, and a semicolon on every statement. The second is that the ceremony buys you something. Java checks your types before your program runs, so a whole family of bugs that Python discovers at 2am in production, Java hands you as a red underline while you are still typing. Most of what feels like extra typing today is that trade being paid for.

Day 1 is nine modules. The first eight teach one idea each, and the ninth is where the eight stop being separate topics and turn into one thing you can do.

---

## Before you start: one-time setup

Do this once, ideally the evening before, so the day does not open with an install.

- [ ] **Install JDK 21.** Grab it from [Adoptium Temurin 21](https://adoptium.net/temurin/releases/?version=21) — free, no account. macOS shortcut: `brew install openjdk@21`. Ubuntu: `sudo apt install openjdk-21-jdk`. Java 21 is an LTS release, which is why the sprint targets it.
- [ ] **Verify it.** Open a *fresh* terminal and run both of these:
      ```
      java -version
      javac -version
      ```
      Both should print something containing `21`. If you get "command not found", the installer did not add Java to your PATH; closing and reopening the terminal fixes it most of the time, because PATH changes only apply to newly opened terminals.
- [ ] **Install IntelliJ IDEA Community Edition.** From [jetbrains.com/idea/download](https://www.jetbrains.com/idea/download/) — scroll past the paid Ultimate edition to Community, which is the free one.
- [ ] **Have a terminal open too.** Module 01 deliberately runs programs from the command line before you touch the IDE, so you can see that nothing is magic.

If `java -version` prints 21 and IntelliJ opens, you are ready.

---

## The day at a glance

Times assume a 9am start. They are the module authors' own estimates, and they assume you actually run the examples and attempt the exercises rather than reading straight through.

| Time | Module | Title | Est. | What it gives you |
|---|---|---|---|---|
| 09:00 | [01](01-getting-started/) | Getting Started | 45 min | What the JDK, the JVM and bytecode each do; Hello World taken apart word by word; how to read a compiler error and why you fix the topmost one first. |
| 09:45 | [02](02-variables-and-types/) | Variables and Types | 60 min | The six types you actually need, integer division, casts that truncate, and why `String` is the capital-S outlier. |
| 10:45 | — | **Break** | 15 min | Get off the chair. Nothing from module 02 needs rehearsing. |
| 11:00 | [03](03-control-flow/) | Control Flow | 60 min | `if`/`else if`/`else`, the `&&` `\|\|` `!` operators, the Java 21 arrow `switch`, all three loops, and `Scanner` input including the `nextInt`/`nextLine` trap. |
| 12:00 | [04](04-methods/) | Methods | 60 min | Every word of a method signature, parameters vs arguments, guard clauses, overloading, scope, pass-by-value, and refactoring as "change the shape, never the behaviour". |
| 13:00 | — | **Lunch** | 45 min | A real break away from the screen. The afternoon is the heavier half. |
| 13:45 | [05](05-classes-and-objects/) | Classes and Objects | 75 min | Fields, constructors, `this`, the full story on `static`, encapsulation as protecting an invariant, references and aliasing, and `null`. |
| 15:00 | [06](06-inheritance-and-interfaces/) | Inheritance and Interfaces | 75 min | `extends`, `super(...)`, `@Override`, polymorphism as the payoff, abstract classes, and interfaces as a capability contract. |
| 16:15 | — | **Break** | 15 min | Modules 05 and 06 are the densest stretch of the day. Take the whole quarter hour. |
| 16:30 | [07](07-equals-hashcode-tostring/) | toString, equals, hashCode | 45 min | The three methods every class inherits and why all three defaults are useless; writing `equals` correctly; the `String` pool; `record` as the modern shortcut. |
| 17:15 | [08](08-exceptions/) | Exceptions | 60 min | Reading a seven-frame stack trace line by line, `try`/`catch`/`finally`, checked vs unchecked, `throw`/`throws`, and writing your own exception. |
| 18:15 | [09](09-capstone-contact-book/) | Capstone: Contact Book | 2.5–3 h | Six milestones that assemble modules 01–08 into a working app, plus exactly six `ArrayList` operations. |

**Modules 01–08 are about 8 hours; the capstone adds 2.5–3 more.** That is a long day, and the honest advice is this: if you reach module 08 with your brain full, stop there and start the capstone fresh the next morning. The capstone is the part that most rewards being alert, and it is the part you will remember. Finishing modules 01–08 in one day is a genuinely good day's work.

If you are working through this part-time instead, the natural split is 01–04 in one session, 05–08 in another, and the capstone in a third.

---

## How to use each module

Every module folder has the same four things: a `LESSON.md`, an `examples/` folder, an `exercises/` folder, and a `solutions/` folder. Work them in this order.

**1. Read `LESSON.md` start to finish.** Do not skim the "Common beginner mistakes" section near the end. Those sections quote real compiler output captured from actual failing compiles, so when you hit one of those errors for real you will recognise the words instead of pasting them into a search engine.

**2. Run the examples yourself.** Every file in `examples/` compiles and runs. Reading code and running code are different activities and only one of them teaches you anything. Then change something — break a line on purpose, print an extra value, swap a `<` for a `<=` — and predict the new output *before* you rerun it. Being wrong here is cheap and it is where most of the learning actually happens.

**3. Do the exercises before you look at the solutions.** The files in `exercises/` are starters that compile from the first run, with `TODO` comments marking your work, so you never begin from a broken build. There is a matching file in `solutions/` for each one. Read a solution only after you have something working, or after you have been genuinely stuck for fifteen minutes — and when you do read it, compare it with yours and ask why the author made each different choice. A solution you read instead of attempting teaches you almost nothing.

**4. Take the "Check yourself" quiz** at the end of the lesson. The answers are hidden until you expand them. Answer out loud or in writing first; recognising an answer is not the same as knowing it. Anything you fumble, reread that section now rather than promising yourself you will come back.

One more rule worth adopting for the whole day: **type the code, do not copy-paste it.** The muscle memory for semicolons, braces and capital letters is real, and it is faster to build than to keep looking up.

---

## Day 1 completion checklist

Concrete things you should be able to do by the end. Go through it honestly — an unticked box is a section to reread, not a failure.

**Module 01 — Getting Started**
- [ ] I can explain in one sentence what the JVM does, and why code I compile on my laptop runs on a different machine.
- [ ] I can compile and run a Java file from the terminal both ways (`java Hello.java`, and `javac` followed by `java`), and when the compiler prints five errors I know to fix the top one and recompile.

**Module 02 — Variables and Types**
- [ ] I can choose between `int`, `long`, `double`, `boolean`, `char` and `String` for a given piece of data and say why.
- [ ] I can predict what `7 / 2` prints and what `(int) 3.9` gives without running it, and I know that casting truncates rather than rounds.

**Module 03 — Control Flow**
- [ ] I can write an `if`/`else if`/`else` chain and explain how reordering the branches changes the answer.
- [ ] I can read a number and then a line of text with `Scanner` without the leftover newline eating my input.

**Module 04 — Methods**
- [ ] I can read a method signature and say what every word in it means.
- [ ] I can take a long `main` and pull named methods out of it so that the program's output does not change at all.

**Module 05 — Classes and Objects**
- [ ] I can write a class with private fields, a constructor that establishes an invariant, and setters that refuse to break it.
- [ ] I can explain what `this` is for, why two variables can point at the same object, and what the "because ... is null" half of a `NullPointerException` message is telling me.

**Module 06 — Inheritance and Interfaces**
- [ ] I can write a subclass with `extends`, call `super(...)` correctly, and mark my overrides with `@Override` — and I know what silently goes wrong if I forget it.
- [ ] I can loop over a `Shape[]` holding mixed subtypes and total their areas with no type-checking `if`-chain anywhere.
- [ ] I can say when I would reach for an interface rather than an abstract class.

**Module 07 — toString, equals, hashCode**
- [ ] I can write `equals` and `hashCode` for a small class by hand and explain why overriding one without the other breaks a `HashSet`.
- [ ] I can explain why `==` on text appears to work at first and then silently stops.

**Module 08 — Exceptions**
- [ ] I can read a stack trace and point at the first frame that is my code rather than the JDK's.
- [ ] I can decide whether a situation calls for throwing `IllegalArgumentException` or for catching something, and I never leave a `catch` block empty.

**Module 09 — Capstone**
- [ ] I built a multi-class console app: a menu loop, a validating model class, a collection wrapper, and `try`/`catch` at the UI edge turning exceptions into one friendly line.
- [ ] I can use `ArrayList`'s six core operations, and I can explain why the one-line duplicate guard only worked because I had already written `equals` and `hashCode`.

---

## An honest note about what today gives you

Day 1 gives you **reading fluency**, not mastery. That distinction matters, so here is what it means in practice.

By tonight you will be able to open an unfamiliar Java file and follow it: you will know what the keywords are doing, where a method starts and ends, why the fields are private, what the `@Override` is for. That is a genuinely large thing to gain in a day, and it is the thing that makes everything after this possible.

What you will not have is recall. Tomorrow you will still type `==` where you meant `.equals`, forget a semicolon, and stare at a `NullPointerException` for a minute before remembering how to read it. That is not a sign the day failed. Nobody retains eleven hours of new syntax from one pass, and the modules were written expecting you not to.

Retention comes from the practice that follows: writing small programs from a blank file, without the lesson open. Twenty minutes a day of that will do more for you in a week than rereading today's material ever would. The capstone is your first taste of it — that feeling of holding a whole program in your head is the actual skill, and it is trained by repetition and nothing else.

So finish the day, tick what you can, and be relaxed about the boxes you cannot tick yet. Come back and write something small tomorrow.

Back to the [sprint overview](../README.md).
