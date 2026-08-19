# Module 02 — Variables and Types

**Estimated time:** ~60 minutes

**What you will learn**

- What a variable is, and what "declaring" one actually does
- Why Java insists you name a type, and what you get in return
- The six types a beginner really needs: `int`, `long`, `double`, `boolean`, `char`, and `String`
- The difference between declaring a variable and initializing it
- Arithmetic in Java, including the integer division trap that catches everybody
- How Java converts between types: automatic widening, and casting for narrowing
- Building and inspecting text with `String` and its methods
- The `var` keyword, naming conventions, and `final` constants

Everything in this module is runnable. The three programs in `examples/` are worth typing or opening side by side as you read. Run any of them with:

```
java VariablesPlayground.java
```

---

## 1. A variable is a named box

A variable is a name you attach to a value so you can use it later.

```java
int score = 10;
System.out.println(score);   // prints 10
```

Read that line right to left: the value `10` goes into a box, and the box is labelled `score`. From then on, writing `score` means "whatever is in that box right now".

Variables **vary** — that is the whole point. You can put a new value in later:

```java
int score = 10;
score = 25;
System.out.println(score);   // prints 25
```

Why they exist: without variables you would have to repeat the same literal value everywhere, and change it in twenty places when it changes once.

---

## 2. Java makes you name the type

Look at that line again. There is a word in front of the variable name:

```java
int score = 10;
```

`int` is the **type**. It tells Java what kind of value this box is allowed to hold — here, a whole number. Once a box is declared as `int`, it holds `int` values forever. Try to put text in it and the program will not run at all:

```java
int score = 10;
score = "ten";   // error: incompatible types: String cannot be converted to int
```

This is called **static typing**: types are checked before your program runs.

Two words worth defining now, since they will come up constantly:

- The **compiler** (`javac`) is the program that reads your `.java` file, checks it for mistakes, and translates it into instructions the Java Virtual Machine can execute.
- The **JVM** (Java Virtual Machine) is the program that actually runs those instructions. When you type `java Something.java`, both steps happen for you in one go.

The type check happens in that first step, at **compile time** — before a single line of your program has run.

That is the payoff. A whole category of bug ("this was supposed to be a number but somehow it became text") gets caught in a second, on your machine, with the exact line number, instead of at three in the morning in production. The cost is that you type a few more words. It is a good trade, and after a day of it you stop noticing.

> **If you know Python**
>
> In Python you write `score = 10` and the name can hold anything later — a string, a list, `None`. Java's `int score = 10;` locks the box to whole numbers permanently. Python's optional type hints (`score: int = 10`) look similar but are not enforced; Java's are, and your program will not compile if you break them.

---

## 3. The types you actually need

Java has eight *primitive* types. You need five of them right now, plus `String`.

### `int` — the everyday whole number

```java
int studentCount = 24;
int temperature = -7;
```

Holds whole numbers from about -2.1 billion to +2.1 billion. This is your default choice for counting things.

### `long` — when `int` is not big enough

```java
long worldPopulation = 8_100_000_000L;
```

Two things to notice. The `L` on the end tells Java "this literal is a long, not an int" — without it, Java reads the digits as an `int` first, sees the number is too big, and refuses. The underscores are purely for human eyes; Java ignores them, and they make long numbers readable.

Use `long` for populations, timestamps in milliseconds, byte counts, IDs — anything that could plausibly exceed two billion.

### `double` — numbers with a decimal point

```java
double averageGrade = 87.5;
double price = 19.99;
```

This is your default for anything measured rather than counted: money, temperatures, averages, distances.

### `boolean` — true or false, nothing else

```java
boolean coursePassed = true;
boolean isEmpty = false;
```

Lowercase `true` and `false`. There is no "truthy" in Java: a `boolean` is exactly one of those two values. It exists because decisions in code are yes-or-no, and this type says so precisely.

### `char` — exactly one character, in single quotes

```java
char letterGrade = 'B';
char firstInitial = 'A';
```

**Single quotes.** `'B'` is a char; `"B"` is a String. They are different types and Java will not silently swap them.

### `String` — text, and the odd one out

```java
String courseName = "Intro to Java";
```

Two things make `String` different from the five above:

1. **Capital S.** All the primitives are lowercase (`int`, `double`); `String` is capitalised. That capital letter is your clue that it is not a primitive.
2. **It is an object**, which means it comes with built-in *methods* — little functions you call on the value itself, like `courseName.length()`. Primitives have no methods; you cannot write `24.length()`.

Double quotes for `String`, always.

Run `examples/VariablesPlayground.java` to see all six side by side:

```
Course:        Intro to Java
Students:      24
World pop:     8100000000
Average grade: 87.5
Letter grade:  B
Passed?        true
```

> **If you know Python**
>
> Python has one integer type that grows without limit, so `int` vs `long` is a new decision for you. Java's `int` genuinely overflows at about 2.1 billion — you will see this happen in section 7. Python's `str` and Java's `String` are close cousins, but Python's single and double quotes are interchangeable while Java's are not: single means char, double means String.

---

## 4. Declaring is not the same as initializing

These are two separate steps, and Java lets you do them separately.

```java
int seatsLeft;          // DECLARE: the box exists and is labelled, but it is empty
seatsLeft = 30 - 24;    // INITIALIZE: now it holds a value
System.out.println(seatsLeft);   // 6
```

Most of the time you do both at once, which is what you have seen so far:

```java
int seatsLeft = 6;
```

The reason this distinction matters is an error you will definitely meet. If you declare a variable and then try to read it before giving it a value, the compiler stops you:

```java
int total;
System.out.println(total);
```

```
error: variable total might not have been initialized
```

That is Java protecting you from reading garbage. The fix is always the same: give it a starting value.

---

## 5. Arithmetic

The operators look like you would expect:

```java
int a = 7;
int b = 2;

System.out.println(a + b);   // 9
System.out.println(a - b);   // 5
System.out.println(a * b);   // 14
System.out.println(a / b);   // 3   <-- look again
System.out.println(a % b);   // 1
```

### The integer division trap

`7 / 2` is `3`. Not `3.5`, not rounded to `4` — just `3`.

Here is the rule: **when both sides of `/` are whole numbers, Java does whole-number division and throws the remainder away.** It does not round; it truncates.

This is the single most common source of "my calculation is silently wrong" in beginner Java, because nothing warns you. The code compiles, it runs, it prints a number, and the number is wrong.

Three ways to get `3.5`:

```java
System.out.println(7 / 2.0);           // 3.5  - one side is a double
System.out.println((double) 7 / 2);    // 3.5  - convert first, then divide
System.out.println((double) (7 / 2));  // 3.0  - TOO LATE, 7/2 already became 3
```

That third line is the trap inside the trap. The parentheses made Java do the int division first, and converting `3` to a double afterwards just gives you `3.0`. The damage was already done.

### `%` is the remainder, and it is more useful than it looks

`a % b` gives what is left over after dividing.

```java
int minutes = 137;
System.out.println(minutes / 60);   // 2   (whole hours)
System.out.println(minutes % 60);   // 17  (leftover minutes)
```

Splitting a total into units like that is the classic use. The other is testing divisibility — `n % 2 == 0` is how you ask "is this even?".

Run `examples/IntegerDivisionAndCasting.java` for all of this in one place.

> **If you know Python**
>
> Java's `/` on two ints behaves like Python's `//`, and Java has no separate `/` that gives you a float — the types of the operands decide. One real difference: with negatives, Python's `//` rounds down (`-7 // 2` is `-4`) while Java truncates toward zero (`-7 / 2` is `-3`). The same goes for `%`: Python's `-7 % 3` is `2`, Java's is `-1`.

---

## 6. Converting between types

Sooner or later you will want a value of one type where another is expected. Java has two rules, and which one applies depends on the direction.

### Widening is automatic

Moving a value into a *roomier* type is always safe, so Java just does it:

```java
int score = 42;
double preciseScore = score;   // fine, prints 42.0
long bigScore = score;         // also fine
```

Nothing can be lost — every `int` fits comfortably inside a `double` or a `long` — so no ceremony is required.

### Narrowing needs a cast

Going the other way can lose information, so Java refuses unless you say explicitly that you accept the loss:

```java
double price = 19.99;
int wholeEuros = price;          // error: incompatible types:
                                 // possible lossy conversion from double to int
int wholeEuros = (int) price;    // fine: 19
```

`(int)` in front of a value is a **cast**. It means "I know this might lose something; do it anyway."

Note carefully what it did: `19.99` became `19`. **A cast truncates, it never rounds.** And it truncates toward zero, so `(int) -3.99` is `-3`, not `-4`. If you want real rounding, ask for it:

```java
System.out.println(Math.round(19.99));   // 20
```

### Mixing int and double in one expression

When an expression contains both, Java widens the `int` to a `double` and the result is a `double`:

```java
int quantity = 3;
double unitPrice = 2.50;
double total = quantity * unitPrice;   // 7.5
```

This is why `7 / 2.0` works: the `7` gets widened to `7.0` before the division happens. The rule of thumb — **if you want a decimal answer, make sure at least one value in the calculation is already a decimal.**

---

## 7. Two things about numbers that will bite you eventually

Neither of these is a Java quirk; they are true of nearly every language. Better to meet them now.

### `double` is fast, not exact

```java
System.out.println(0.1 + 0.2);   // 0.30000000000000004
```

A `double` stores numbers in binary, and `0.1` has no exact binary representation — in the same way `1/3` has no exact decimal representation. Tiny errors accumulate.

For grades, temperatures and averages this is invisible and fine. For money, professionals reach for `BigDecimal` instead. Just do not be shocked when a total ends in `...0004`.

### `int` has a ceiling, and it wraps around

```java
System.out.println(Integer.MAX_VALUE);       // 2147483647
System.out.println(Integer.MAX_VALUE + 1);   // -2147483648
```

Add one to the biggest `int` and you land on the smallest. No error, no warning — it just silently wraps. This is called **overflow**, and the cure is to use a `long` when the numbers might get large.

---

## 8. Working with `String`

### Gluing text together with `+`

The `+` operator does double duty. Between two numbers it adds; when either side is a `String`, it joins text:

```java
String firstName = "Ada";
String lastName = "Lovelace";
String fullName = firstName + " " + lastName;   // "Ada Lovelace"

int year = 1843;
System.out.println("Published in " + year + ".");   // Published in 1843.
```

Notice the number turned into text automatically. That is `+` being helpful — but it means order matters, because `+` works left to right:

```java
System.out.println(1 + 2 + " points");     // 3 points
System.out.println("Points: " + 1 + 2);    // Points: 12
```

On the first line, `1 + 2` happens while both are still numbers, so you get `3`. On the second, `"Points: " + 1` produces text immediately, and then `+ 2` just tacks another character on the end. When in doubt, add parentheses around the arithmetic.

### A sampler of String methods

Because `String` is an object, you call methods on it with a dot:

```java
String language = "Java";

language.length()             // 4
language.toUpperCase()        // "JAVA"
language.toLowerCase()        // "java"
language.contains("av")       // true
language.startsWith("J")      // true
language.charAt(0)            // 'J'
language.repeat(3)            // "JavaJavaJava"
"  hi  ".trim()               // "hi"
```

One rule that surprises people: **methods never change the original String.** They return a *new* one. So this does nothing useful:

```java
String name = "ada";
name.toUpperCase();               // result thrown away
System.out.println(name);         // still "ada"

String shouted = name.toUpperCase();   // this is what you meant
```

### Comparing text: use `.equals()`, not `==`

This one matters enough to flag now, even though the full explanation belongs to a later module.

```java
String a = "java";
String b = new String("java");

System.out.println(a == b);        // false
System.out.println(a.equals(b));   // true
```

Both hold the same four letters, yet `==` says they are different. The short version: for objects, `==` asks *"are these the exact same object in memory?"* while `.equals()` asks *"do these have the same contents?"* For text you almost always want the second question.

**Rule for now: compare Strings with `.equals()`.** Also handy is `.equalsIgnoreCase()`, which ignores capitalisation — exactly what you want when comparing something a user typed.

The complete story of `==` versus `.equals()`, and why `String` sometimes seems to break the rule, comes in **module 07**. Until then, trust the rule and you will never be wrong.

Run `examples/StringMethodsDemo.java` to watch all of this happen.

---

## 9. The `var` keyword

Since Java 10 you can let the compiler work out the type of a *local* variable from the value you assign:

```java
var instructor = "Ada";     // compiler sees a String literal, so this is a String
var roomNumber = 214;       // this is an int
```

This is **not** Python-style dynamic typing. The type is still fixed and still checked — you just did not type it out. `instructor` is a `String` permanently, and `instructor = 5;` will still fail to compile.

Because the type comes from the value, `var` needs a value immediately:

```java
var x;   // error: cannot infer type for local variable x
```

Use it when the type is already obvious from the right-hand side and repeating it adds nothing. Skip it when spelling out the type makes the code clearer — which, while you are learning, is most of the time. This module writes types out in full on purpose.

---

## 10. Naming things

Java has strong conventions. They are not enforced by the compiler, but every Java codebase follows them, so start now:

| Thing | Style | Example |
|---|---|---|
| Variable | `camelCase` | `studentCount`, `averageGrade` |
| Constant | `SCREAMING_SNAKE_CASE` | `DAYS_IN_WEEK`, `MAX_RETRIES` |
| Class | `PascalCase` | `ProfileCard`, `TemperatureConverter` |

`camelCase` means: start lowercase, then capitalise the first letter of each following word. No underscores, no spaces.

Beyond style, pick names that say what the value *means*. `int d = 7;` tells the next reader nothing. `int daysInWeek = 7;` tells them everything. The next reader is usually you, three weeks later.

> **If you know Python**
>
> This is the main cosmetic adjustment. Python's `snake_case` variables become `camelCase` in Java; `PascalCase` for classes is the same in both.

---

## 11. `final` for values that must not change

Put `final` in front of a declaration and the value can be set once and never reassigned:

```java
final int DAYS_IN_WEEK = 7;
DAYS_IN_WEEK = 8;   // error: cannot assign a value to final variable DAYS_IN_WEEK
```

Use it for genuine constants — a tax rate, a maximum, a conversion factor, a freezing point. Two benefits: the compiler guarantees nobody accidentally changes it, and the name documents the meaning of an otherwise mysterious number sitting in the middle of a formula.

---

## Common beginner mistakes

Each of these is something you will actually hit. The error text is exactly what `javac` prints.

**1. Forgetting the type on the first use**

```java
count = 5;
```
```
error: cannot find symbol
  symbol:   variable count
```
Java has never heard of `count` because you never declared it. **Fix:** `int count = 5;`

---

**2. Putting a decimal into an `int`**

```java
int x = 3.5;
```
```
error: incompatible types: possible lossy conversion from double to int
```
**Fix:** use `double x = 3.5;` if you want the decimals, or `int x = (int) 3.5;` if you genuinely want to throw them away.

---

**3. Reading a variable you never gave a value**

```java
int total;
System.out.println(total);
```
```
error: variable total might not have been initialized
```
**Fix:** `int total = 0;`

---

**4. Using double quotes for a `char`**

```java
char c = "a";
```
```
error: incompatible types: String cannot be converted to char
```
**Fix:** single quotes — `char c = 'a';`

---

**5. A `long` literal without the `L`**

```java
long big = 8100000000;
```
```
error: integer number too large
```
Java reads the digits as an `int` before ever looking at the left-hand side. **Fix:** `long big = 8_100_000_000L;`

---

**6. Declaring the same variable twice**

```java
int x = 1;
int x = 2;
```
```
error: variable x is already defined in method main(String[])
```
**Fix:** drop the second `int` — reassigning is just `x = 2;`

---

**7. Trying to reassign a `final`**

```java
static final int MAX = 10;
MAX = 11;
```
```
error: cannot assign a value to static final variable MAX
```
**Fix:** if it needs to change, it should not be `final`.

---

**8. `length` instead of `length()` on a String**

```java
String s = "hi";
int n = s.length;
```
```
error: cannot find symbol
  symbol:   variable length
  location: variable s of type String
```
`length()` is a method on String, so it needs parentheses. (Confusingly, arrays use `length` with no parentheses — arrays get a short introduction in module 04 and their full treatment on day 2.) **Fix:** `s.length()`

---

**9. `var` with nothing to infer from**

```java
var x;
x = 5;
```
```
error: cannot infer type for local variable x
  (cannot use 'var' on variable without initializer)
```
**Fix:** `var x = 5;` or just `int x;`

---

**10. Integer division, which produces no error at all**

```java
double half = 1 / 2;
System.out.println(half);   // 0.0
```
This one compiles cleanly and gives you a wrong answer. `1 / 2` is computed as ints (`0`) and *then* widened to `0.0`. **Fix:** `double half = 1 / 2.0;`

---

**11. Comparing text with `==`**

```java
String typed = new String("yes");
System.out.println(typed == "yes");        // false
System.out.println(typed.equals("yes"));   // true
```
Also no error — just a comparison that quietly fails. **Fix:** use `.equals()`.

---

## Check yourself

1. What does `System.out.println(9 / 4);` print, and why?
2. Why does `long distance = 5000000000;` fail to compile, and what is the fix?
3. What is the difference between `'x'` and `"x"` in Java?
4. What does `(int) -7.9` evaluate to? What about `Math.round(-7.9)`?
5. What does `System.out.println("Score: " + 3 + 4);` print? How would you make it print `Score: 7`?
6. Does `var total = 0;` mean `total` can later hold a String? Why or why not?

<details><summary>Answers</summary>

1. `2`. Both operands are `int`, so Java does integer division and discards the remainder. It truncates rather than rounding, so it is `2` and not `2.25` or `2`-rounded-up.

2. The literal `5000000000` is read as an `int` before Java looks at the type on the left, and it exceeds the `int` maximum of 2147483647 — the error is `integer number too large`. The fix is the `L` suffix: `long distance = 5_000_000_000L;`

3. `'x'` in single quotes is a `char`, a single character. `"x"` in double quotes is a `String`, an object that happens to contain one character. They are different types and cannot be assigned to each other.

4. `(int) -7.9` is `-7` — a cast truncates toward zero, it does not round. `Math.round(-7.9)` is `-8`, because that one actually rounds.

5. It prints `Score: 34`. Evaluation runs left to right: `"Score: " + 3` makes the String `"Score: 3"`, and then `+ 4` appends `4` as text. To get `Score: 7`, force the addition to happen first with parentheses: `System.out.println("Score: " + (3 + 4));`

6. No. `var` only asks the compiler to infer the type from the initial value — here, `int`. The type is fixed from then on, exactly as if you had written `int total = 0;`, and `total = "seven";` would not compile. `var` saves typing, not type safety.

</details>

---

## Exercises

Three files are waiting in `exercises/`. All of them **compile and run as they are** — they just do the wrong thing until you fill in the `TODO`s. Run each one before you start editing so you can see the starting point, then run it again after every change.

Full solutions are in `solutions/`, one file per exercise with the same filename. Please give each exercise an honest attempt before you open them — struggling with a wrong answer for five minutes teaches you more than reading a right one for thirty seconds. When you do peek, compare approaches rather than just checking that you matched.

### 1. `TemperatureConverter.java`

**Goal:** implement `celsiusToFahrenheit`, `fahrenheitToCelsius` and `isFreezing`.

**Done looks like:** the printed table matches the expected output in the file's header comment exactly — `37.0 C = 98.6 F`, `100.0 C = 212.0 F`, and `Is -5.0 C freezing? true`.

**Hint:** the formula is `celsius * 9 / 5 + 32`. Because `celsius` is a `double`, that whole expression stays in double arithmetic. If you were tempted to write `9 / 5` on its own somewhere, work out what that equals as ints first — it is not `1.8`.

### 2. `ProfileCard.java`

**Goal:** implement five small methods that format a profile card using String methods.

**Done looks like:** six lines of output, with the row of dashes exactly as long as the `=== ADA LOVELACE ===` line above it, and the final line reporting `equals -> false | equalsIgnoreCase -> true`.

**Hint:** for the divider, do not count the dashes by hand. Build the header, ask it for its `.length()`, and hand that number to `"-".repeat(...)` — then it stays correct even if the name changes.

### 3. `CastingQuiz.java`

**Goal:** predict the output of ten expressions *before* running the file. Replace each `"TODO"` with the exact text you expect Java to print.

**Done looks like:** all ten questions report `match? true`. But the real point is your first attempt — note which ones you got wrong, because those are the rules worth re-reading.

**Hint:** write down all ten predictions before you run it even once. Running early turns this from a quiz into a copying exercise, and you learn nothing from a copying exercise. Section 5 (integer division), section 6 (casting) and section 8 (`+` with text) cover every question here.

---

**Next up:** module 03 puts these variables to work with conditionals and loops.
