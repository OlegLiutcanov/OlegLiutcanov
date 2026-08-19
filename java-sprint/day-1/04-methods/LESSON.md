# Module 04 — Methods: Organizing Your Code

**Estimated time:** ~60 minutes

**What you will learn**

- Why methods exist, and what you get the moment you name a chunk of work
- Every part of a method signature, one word at a time
- The difference between a parameter and an argument
- Returning a value, and what `void` really means
- What `static` is doing there (and the honest short answer for now)
- Calling methods from `main` — and from each other
- Method overloading: one name, several versions
- Scope: a variable lives inside the braces it was born in
- Pass by value: why changing a parameter cannot change the caller's variable
- Guard clauses and early returns
- How to take a messy `main` apart and turn it into methods

Four programs live in `examples/`. Open them beside this page and run them as you go:

```
java MethodBasics.java
```

---

## 1. Why methods exist

Here is a `main` that works perfectly:

```java
public static void main(String[] args) {
    double c1 = 12.4;
    double f1 = c1 * 9 / 5 + 32;
    System.out.println(c1 + " C is " + f1 + " F");

    double c2 = 21.3;
    double f2 = c2 * 9 / 5 + 32;
    System.out.println(c2 + " C is " + f2 + " F");

    double c3 = 7.2;
    double f3 = c3 * 9 / 5 + 32;
    System.out.println(c3 + " C is " + f3 + " F");
}
```

It runs. It prints the right answers. And it is already a problem.

The formula `c * 9 / 5 + 32` is written three times. If it is wrong, it is wrong in three places. If you need a fourth temperature, you copy, paste, and rename two variables — and copy-paste-rename is exactly how a `c4` ends up printed with `f3`.

A **method** is a named chunk of work that you write once and call as many times as you like:

```java
static double toFahrenheit(double celsius) {
    return celsius * 9 / 5 + 32;
}
```

Now the formula exists in one place, and that place has a name.

Methods buy you four things:

1. **A name.** `toFahrenheit(12.4)` says what is happening. `12.4 * 9 / 5 + 32` makes the reader work it out.
2. **Reuse.** Write it once, call it from anywhere, fix it in one spot.
3. **Testability.** You can check `toFahrenheit(100.0)` gives `212.0` on its own. You cannot check "the middle third of `main`".
4. **A smaller thing to think about.** While you are writing the method you think only about converting one temperature. While you are reading `main` you do not think about the formula at all — you trust the name.

That last one is the real prize. Programming is mostly about not having to hold everything in your head at once, and methods are the main tool for that.

> **If you know Python**
>
> You already know this idea as `def`. Java's version is the same concept with more words in the header and braces instead of indentation. The genuinely new parts are declaring a return type, the word `static`, and overloading — Python has no equivalent for that last one.

---

## 2. Your first method, taken apart

```java
static double toFahrenheit(double celsius) {
    return celsius * 9 / 5 + 32;
}
```

That first line is the **signature**. Read it left to right:

| Piece | What it is | What it means here |
|---|---|---|
| `static` | a modifier | this method belongs to the class itself — no object needed |
| `double` | the **return type** | calling this gives you back a `double` |
| `toFahrenheit` | the **name** | how you call it; `camelCase`, usually a verb |
| `(double celsius)` | the **parameter list** | it needs one `double`, known inside as `celsius` |
| `{ ... }` | the **body** | the work, between braces |

Two rules with no exceptions:

- **Every parameter needs its own type.** `(double a, double b)` is right; `(double a, b)` will not compile.
- **A method with a return type other than `void` must return that type on every path out.** The compiler checks this, and it is strict about it.

### Where methods go

Methods sit **inside the class, next to `main` — never inside `main`**. This is the single most common shape mistake in week one:

```java
public class Demo {
    static int twice(int n) {      // correct: a sibling of main
        return n * 2;
    }

    public static void main(String[] args) {
        System.out.println(twice(21));
    }
}
```

Order does not matter. A method defined below `main` can still be called from `main` — Java reads the whole class before running anything.

### What `static` means, for now

`static` means **"this belongs to the class itself, not to an individual object."** You call it by naming the method directly: `toFahrenheit(12.4)`.

The other kind of method belongs to a specific object and is called with a dot on that object — you have already used those: `name.toUpperCase()` runs on the particular String in `name`.

For this module every method you write is `static`, and here is the practical reason: `main` is `static`, and a `static` method can only call other `static` methods directly. Drop the word and you get an error we will look at properly in section 12.

**Module 05 introduces the other kind**, along with objects and the word `new`. Until then: write `static` on everything and it will all work.

Run `examples/MethodBasics.java` to see the anatomy in action:

```
=== Method basics ===
square(5)          = 25
square(12)         = 144
area               = 81
square(3) + 1      = 10
square(square(2))  = 16
biggerOf(4, 9)     = 9
sumOfSquares(3, 4) = 25
safeAverage(10, 4) = 2.5
safeAverage(10, 0) = 0.0   <- no crash, the guard caught it
=== Done ===
```

---

## 3. Parameters and arguments

These two words get used loosely in conversation, but they name different things and it is worth being precise once:

```java
static int square(int n) {     // n is a PARAMETER - a name in the definition
    return n * n;
}

square(5);                     // 5 is an ARGUMENT - a value at the call site
```

A **parameter** is the placeholder in the method's header. An **argument** is the actual value you hand over when calling. Parameters are written once; arguments are supplied fresh on every call.

Inside the method, a parameter behaves exactly like a local variable that arrived pre-filled.

Arguments are matched **by position**, not by name:

```java
static void report(String name, int score) {
    System.out.println(name + " scored " + score);
}

report("Ada", 95);    // Ada scored 95
report(95, "Ada");    // error: incompatible types: int cannot be converted to String
```

There is no `report(score = 95, name = "Ada")` in Java. Position is everything, which is a good reason to keep parameter lists short — three or four is plenty.

A method can take no parameters at all, and then the parentheses are simply empty:

```java
static void printSeparator() {
    System.out.println("--------------------");
}

printSeparator();     // still needs the () - that is what makes it a call
```

> **If you know Python**
>
> Java has no keyword arguments and no default values. Python's `def greet(name, salutation="Hello")` has no direct translation; the Java way is two overloaded methods, which is section 7.

---

## 4. Returning a value

`return` does two things at once, and beginners often notice only the first:

1. It hands a value back to whoever called the method.
2. **It ends the method immediately.** Nothing after it in that path runs.

```java
static int twice(int n) {
    return n * 2;
    System.out.println("done");   // error: unreachable statement
}
```

```
error: unreachable statement
```

The compiler will not even let you write code that could never run.

The value that comes back can be used anywhere a value of that type is welcome:

```java
int area = square(9);                        // stored in a variable
System.out.println(square(3) + 1);           // used in an expression
System.out.println(square(square(2)));       // fed into another call - inner runs first
```

If a method claims to return something, every route out of it must actually return something:

```java
static int half(int n) {
    int result = n / 2;
}
```

```
error: missing return statement
```

The fix is `return result;`. This error is nearly always a forgotten `return`, so read it as "you promised an `int` and never delivered one".

---

## 5. `void`: methods that do, rather than give

Some methods have no answer to hand back. They print something, or update something, and that is the whole job. Their return type is `void`:

```java
static void printBanner(String title) {
    System.out.println("=== " + title + " ===");
}

printBanner("Results");     // === Results ===
```

`void` is Latin for "empty", and here it means "there is nothing to collect". A `void` call is a complete statement on its own — you do not put it on the right of an `=`.

Try to use its result and the compiler stops you:

```java
System.out.println(printBanner("Results"));
```

```
error: 'void' type not allowed here
```

There is nothing to print, because `printBanner` gives back nothing.

You may still use a bare `return;` inside a `void` method to leave early — just with no value after it:

```java
static void greetIfNamed(String name) {
    if (name.isEmpty()) {
        return;            // nothing to do, get out
    }
    System.out.println("Hello, " + name);
}
```

And you may not return a value from one:

```java
static void log(String message) { return message; }
```

```
error: incompatible types: unexpected return value
```

**How to choose:** if the caller needs the answer, return it. If the method's whole purpose is a side effect — printing, writing to a file — `void` is right. When in doubt, returning a value is the more flexible choice, because the caller can always decide to print it.

---

## 6. Guard clauses and early returns

Because `return` exits immediately, you can use it to deal with awkward cases up front and then write the interesting part with a clear head.

Here is the version without that idea:

```java
static double average(int total, int count) {
    if (count != 0) {
        return (double) total / count;
    } else {
        return 0.0;
    }
}
```

And here is the same logic as a **guard clause** — check the bad case, get out, and let everything below assume things are fine:

```java
static double average(int total, int count) {
    if (count == 0) {
        return 0.0;         // guard: division by zero is not a thing we do
    }
    return (double) total / count;
}
```

With one condition the difference is cosmetic. With three it is not:

```java
static String describeScore(int score) {
    if (score < 0)   { return "invalid"; }
    if (score > 100) { return "invalid"; }
    if (score >= 90) { return "excellent"; }
    return "keep going";
}
```

(Braces on every `if`, even a one-line one — that is the rule from module 03, and a guard clause is no exception to it. Putting the whole guard on one line keeps the code just as flat.)

The alternative is a staircase of nested `if / else` that drifts further right with every case. Flat code is easier to read, and each guard sits next to the situation it handles.

Why it exists: most real methods have a couple of situations they simply refuse — empty input, zero, a negative count. Naming those at the top, and leaving, keeps the body of the method about the normal case.

---

## 7. Method overloading

Java lets several methods share one name, as long as their **parameter lists differ**. This is called **overloading**, and Java picks the right one at compile time from the arguments you pass.

```java
static int add(int a, int b)              { return a + b; }
static double add(double a, double b)     { return a + b; }
static int add(int a, int b, int c)       { return a + b + c; }
static String add(String a, String b)     { return a + b; }
```

```java
add(2, 3)          // 5      picks add(int, int)
add(2.5, 3.5)      // 6.0    picks add(double, double)
add(1, 2, 3)       // 6      picks add(int, int, int)
add("Ja", "va")    // Java   picks add(String, String)
add(2, 3.5)        // 5.5    no add(int, double) exists, so 2 widens to 2.0
```

What counts as "different" is only the **types and the number** of parameters. Two things do *not* count:

```java
static int add(int a, int b) { return a + b; }
static double add(int a, int b) { return a + b; }
```

```
error: method add(int,int) is already defined in class OverloadingDemo
```

**The return type is not part of the signature for this purpose.** Neither are the parameter names — renaming `a` and `b` to `first` and `second` gives the same error.

The most useful pattern is a short version and a long version, where the short one calls the long one:

```java
static String greet(String name) {
    return greet(name, "Hello");        // the default lives here
}

static String greet(String name, String salutation) {
    return salutation + ", " + name + "!";
}
```

The greeting logic exists once. The one-argument version just supplies a default and delegates. This is how Java does what other languages do with default parameter values.

You have been using overloads since your first line of Java: `System.out.println` has a version for `int`, `double`, `boolean`, `char`, `String` and more. One name, many methods.

Run `examples/OverloadingDemo.java` — each method announces itself, so you can see exactly which one Java chose:

```
add(2, 3.5):
  [add(double, double) ran]
  -> 5.5
```

---

## 8. Scope: a variable lives inside its braces

**Scope** is the region of code where a name means something. In Java the rule is refreshingly mechanical:

> A variable exists from its declaration until the closing brace `}` of the block it was declared in. Then it is gone.

```java
static void scopeTour() {
    int outer = 1;                 // lives until scopeTour ends

    if (outer == 1) {
        int insideIf = 2;          // lives until this if-block ends
    }
    System.out.println(insideIf);  // error: cannot find symbol

    for (int i = 0; i < 3; i++) {
        System.out.println(i);     // fine, i belongs to the loop
    }
    System.out.println(i);         // error: cannot find symbol
}
```

```
error: cannot find symbol
  symbol:   variable i
  location: class ScopeAndPassByValue
```

That error means "I have never heard of this name **here**". The variable may well exist five lines up, in a block that has already closed.

Three consequences worth internalising:

- **A method cannot see another method's local variables.** `main` declaring `int total` does not give `toFahrenheit` a `total`. This is a feature: it is why you can write a method without reading the rest of the program.
- **Parameters are locals too.** They live for exactly one call and vanish when it returns.
- **If you need a value after a block ends, declare it before the block.**

> **Two bits of notation before the next snippet, because they are about to keep appearing.**
>
> `int[] values` is an **array**: a numbered, fixed-size row of values that all share one type. It is the same `[]` you have been typing in `String[] args` since module 01. You build one with a list in braces — `int[] numbers = { 1, 2, 3 };` — read a slot by its position counting from zero — `numbers[0]` is `1` — and ask how many slots there are with `numbers.length` (no parentheses, unlike `String`'s `length()`).
>
> `for (int value : values)` is the **for-each** loop. It runs the body once for every item in the array and hands you each item in turn under the name you chose — here `value`. There is no counter, no test and no update, so there is no off-by-one to get wrong. Use it when you want every item and do not care about positions; use the counting `for` from module 03 when you need the index. Arrays get their full treatment on day 2; what is here is all this module needs.

```java
int biggest = 0;                   // declared out here, so it survives
for (int value : values) {
    if (value > biggest) {
        biggest = value;
    }
}
System.out.println(biggest);       // fine
```

### Fields: the variables methods do share

A variable declared inside the class but outside every method is a **field**. Every method in the class can see it and change it:

```java
public class Counter {
    static int callsMade = 0;      // a field

    static void ping() {
        callsMade++;               // works - fields are shared
    }
}
```

Use fields sparingly while you are learning. A value passed in as a parameter is easy to trace; a field that any method might have changed is not.

### Shadowing

If a local variable has the same name as a field, the local one wins inside that method:

```java
static int callsMade = 0;

static void confusing() {
    int callsMade = 99;                 // shadows the field
    System.out.println(callsMade);      // 99, not 0
}
```

This is legal and almost always an accident. Pick different names.

Run `examples/ScopeAndPassByValue.java` for all of this, with the errors left in as comments.

> **If you know Python**
>
> Python scopes to the function, so a variable created inside an `if` or a `for` is still readable afterwards, and `i` survives the loop. Java scopes to the block, so both of those are compile errors. Java is stricter, and it catches a real class of bug — a variable used far from where it was meant to be.

---

## 9. Pass by value: a method gets a copy

Here is a result that surprises everyone once:

```java
static void tryToChange(int number) {
    number = number + 100;
    System.out.println("inside: " + number);
}

public static void main(String[] args) {
    int score = 10;
    tryToChange(score);
    System.out.println("after:  " + score);
}
```

```
inside: 110
after:  10
```

The method changed `number`, and `score` did not budge.

**Java passes arguments by value.** When you call `tryToChange(score)`, Java copies the *value* `10` into the parameter `number`. From then on they are two unrelated boxes. Rewriting `number` rewrites the copy.

The same holds for a `String`:

```java
static void tryToChangeText(String text) {
    text = text + " (edited)";
}
// caller's String is untouched
```

Why this is good news: you can call any method with your variables and know that a plain assignment inside it cannot reach back and rewrite them. That guarantee makes code much easier to reason about.

**So how does a method give you something back? It returns it.**

```java
static int withBonus(int number) {
    return number + 100;
}

int score = 10;
score = withBonus(score);      // 110 - the caller decided to accept the new value
```

That is the whole pattern: a method computes, the caller assigns.

### The one exception to be aware of

Arrays behave differently, and it is better to meet this now than to be ambushed by it:

```java
static void changeFirstSlot(int[] values) {
    values[0] = 999;
}

int[] numbers = { 1, 2, 3 };
changeFirstSlot(numbers);
System.out.println(numbers[0]);    // 999 - it really changed
```

Java is still copying a value, but for an array the value being copied is the *arrow* pointing at the array, not the array itself. Both arrows lead to the same set of boxes, so a change made through either one is visible from both.

Reassigning the parameter, though, still only moves the local arrow:

```java
static void tryToReplace(int[] values) {
    values = new int[] { -1, -1, -1 };   // caller's array is unaffected
}
```

The full story — objects, references, and what `new` actually does — is module 05. For now: **primitives and Strings are safe from a method; the contents of an array are not.**

> **If you know Python**
>
> This will feel familiar in an unexpected way. Python behaves the same: rebinding a parameter never affects the caller, but mutating a list that was passed in does. Java's `int` and `String` behave like Python's immutable types; Java's arrays behave like Python's lists.

---

## 10. Taking a messy `main` apart

This is the skill the whole module is building towards. Here is a real `main` — it computes statistics for a week of temperature readings, and it is entirely correct:

```java
public static void main(String[] args) {
    double lowest = READINGS[0];
    double highest = READINGS[0];
    double total = 0.0;
    int warmDays = 0;

    for (double reading : READINGS) {
        if (reading < lowest)  { lowest = reading; }
        if (reading > highest) { highest = reading; }
        total = total + reading;
        if (reading >= WARM_THRESHOLD) { warmDays = warmDays + 1; }
    }

    double average = total / READINGS.length;
    double range = highest - lowest;

    System.out.println("Lowest  : " + Math.round(lowest * 10) / 10.0);
    System.out.println("Highest : " + Math.round(highest * 10) / 10.0);
    System.out.println("Average : " + Math.round(average * 10) / 10.0);
    System.out.println("Range   : " + Math.round(range * 10) / 10.0);
    System.out.println("Warm    : " + warmDays);
}
```

Nothing here is *wrong*. But one loop is doing four unrelated jobs, four variables are alive at once, and `Math.round(x * 10) / 10.0` appears four times.

### How to find the methods

Ask: **what would I say out loud if I were describing this to someone?**

You would say: *find the lowest, find the highest, average them, work out the range, count the warm days, round everything to one decimal.* That sentence is the method list. Six phrases, six methods.

Three signals that a chunk wants to be a method:

1. **It repeats.** Four copies of the rounding expression is four chances to get it wrong.
2. **You would give it a name in conversation.** "Count the warm days" is a name; the code should have it too.
3. **You wrote a comment explaining what a block does.** That comment is almost always a method name in disguise.

### After

```java
static double averageOf(double[] values) {
    if (values.length == 0) {
        return 0.0;
    }
    double total = 0.0;
    for (double value : values) {
        total = total + value;
    }
    return total / values.length;
}

static int countAtOrAbove(double[] values, double threshold) {
    int matches = 0;
    for (double value : values) {
        if (value >= threshold) {
            matches = matches + 1;
        }
    }
    return matches;
}

static double roundedToOneDecimal(double value) {
    return Math.round(value * 10) / 10.0;
}

// ...and lowestOf, highestOf, rangeOf, printStat
```

which makes the caller this:

```java
printStat("Lowest ", lowestOf(READINGS));
printStat("Highest", highestOf(READINGS));
printStat("Average", averageOf(READINGS));
printStat("Range  ", rangeOf(READINGS));
System.out.println("Warm days : " + countAtOrAbove(READINGS, WARM_THRESHOLD));
```

Yes, it is more lines overall. Look at what you gained:

- Every calculation has a name, so the caller is readable without scrolling.
- The rounding rule lives in one place.
- `averageOf` and `countAtOrAbove` work on **any** `double[]` — the example file reuses both on a list of grades, with no changes.
- If the average comes out wrong, you know which nine lines to look at.

Run `examples/TemperatureStats.java`. It prints both versions and they match exactly:

```
----- BEFORE: everything inside one main -----
Readings taken : 7
Lowest         : 7.2
Highest        : 24.1
Average        : 15.5
Range          : 16.9
Warm days      : 3  (18.0 C or above)

----- AFTER: small methods with names -----
Readings taken : 7
Lowest         : 7.2
Highest        : 24.1
Average        : 15.5
Range          : 16.9
Warm days      : 3  (18.0 C or above)
```

**That identical output is the definition of refactoring:** changing the shape of code without changing its behaviour. Which means you always have a test — run it before, run it after, compare.

---

## 11. Small habits that pay off

**Name methods with verbs.** `calculateTotal`, `isPrime`, `printReceipt`. A method *does* something, so its name should say what. Methods returning a `boolean` almost always start with `is`, `has` or `can` — `isPrime(7)` reads like a question with a yes-or-no answer, which is exactly what it is.

**One job per method.** If the name needs an "and" — `validateAndSaveAndPrint` — that is three methods wearing a trench coat.

**Keep them short.** No hard rule, but a method you cannot see all of at once is usually hiding a smaller method inside it.

**Prefer parameters over fields.** A method whose inputs all arrive as parameters can be understood, and tested, entirely on its own.

**Return values rather than printing them**, unless printing is the point. `double average = averageOf(data);` can be printed, compared or added to something else. A method that only prints can only print.

---

## 12. Common beginner mistakes

Every error below is exactly what `javac` prints.

**1. Defining a method inside `main`**

```java
public static void main(String[] args) {
    static int twice(int n) { return n * 2; }
}
```
```
error: illegal start of expression
        static int twice(int n) { return n * 2; }
        ^
```
Methods go inside the **class**, beside `main`, never inside it. Expect a small pile of follow-on errors from this one — fix the first and the rest usually vanish. **Fix:** move the whole method out past `main`'s closing brace.

---

**2. Forgetting `static`**

```java
int helper() { return 7; }

public static void main(String[] args) {
    System.out.println(helper());
}
```
```
error: non-static method helper() cannot be referenced from a static context
```
`main` is `static`, so it can only call other `static` methods by name. **Fix:** `static int helper() { return 7; }`

---

**3. Forgetting the `return`**

```java
static int half(int n) {
    int result = n / 2;
}
```
```
error: missing return statement
```
You promised an `int` and never handed one over. **Fix:** `return result;`

---

**4. Using the result of a `void` method**

```java
static void shout(String s) { System.out.println(s.toUpperCase()); }

System.out.println(shout("hi"));
```
```
error: 'void' type not allowed here
```
There is nothing to print — `shout` already did the printing. **Fix:** just call it: `shout("hi");`

---

**5. Returning a value from a `void` method**

```java
static void log(String message) { return message; }
```
```
error: incompatible types: unexpected return value
```
**Fix:** either change the return type to `String`, or drop the value and use a bare `return;`

---

**6. Wrong number of arguments**

```java
static int addUp(int a, int b) { return a + b; }

addUp(3);
```
```
error: method addUp in class Demo cannot be applied to given types;
  required: int,int
  found:    int
  reason: actual and formal argument lists differ in length
```
This error is friendlier than it looks: `required` is what the method wants, `found` is what you sent. **Fix:** `addUp(3, 0);`

---

**7. Wrong argument type**

```java
static int twice(int n) { return n * 2; }

twice(2.5);
```
```
error: incompatible types: possible lossy conversion from double to int
```
Java will widen an `int` into a `double` for you, never the reverse. **Fix:** `twice(2)`, or `twice((int) 2.5)` if you truly want to throw the decimals away.

---

**8. Two overloads differing only by return type**

```java
static int add(int a, int b) { return a + b; }
static double add(int a, int b) { return a + b; }
```
```
error: method add(int,int) is already defined in class Demo
```
Only the types and count of parameters distinguish overloads. **Fix:** change the parameters, or give one method a different name.

---

**9. A semicolon after the signature**

```java
static int twice(int n);
```
```
error: missing method body, or declare abstract
```
A slip of the finger, and the message is oddly specific. **Fix:** delete the `;` and add `{ ... }`

---

**10. Using a variable outside its block**

```java
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}
System.out.println("last i was " + i);
```
```
error: cannot find symbol
  symbol:   variable i
  location: class Demo
```
`i` was born in the loop header and died at the loop's closing brace. **Fix:** declare it before the loop if you need it afterwards.

---

**11. Code after a `return`**

```java
static int twice(int n) {
    return n * 2;
    System.out.println("done");
}
```
```
error: unreachable statement
```
`return` leaves immediately, so that line could never run. **Fix:** move it above the `return`.

---

**12. Expecting a method to change your variable** *(no error at all)*

```java
static void addBonus(int score) { score = score + 10; }

int myScore = 90;
addBonus(myScore);
System.out.println(myScore);   // 90
```
This compiles, runs, and quietly does nothing — the worst kind of bug. The method got a copy. **Fix:** return the new value and assign it:
```java
static int withBonus(int score) { return score + 10; }
myScore = withBonus(myScore);
```

---

**13. Calling a method without parentheses** *(a subtle one)*

```java
static void printSeparator() { System.out.println("-----"); }

printSeparator;
```
```
error: not a statement
```
The parentheses are what make it a call. **Fix:** `printSeparator();`

---

## Check yourself

1. In `static double half(int n)`, name each of the four parts of that signature and say what each one tells you.
2. What is the difference between a parameter and an argument?
3. Can you have two methods both called `scale`, one taking `(int)` and one taking `(double)`? What about one returning `int` and one returning `double`, both taking `(int)`?
4. This method does not compile. Why, and what is the one-word fix?
   ```java
   static int biggest(int a, int b) {
       if (a > b) {
           return a;
       }
   }
   ```
5. What does this print, and why?
   ```java
   static void bump(int n) { n = n + 1; }

   public static void main(String[] args) {
       int count = 5;
       bump(count);
       System.out.println(count);
   }
   ```
6. Rewrite this using a guard clause, and say why the guard version is easier to extend:
   ```java
   static String status(int stock) {
       if (stock > 0) {
           return "in stock";
       } else {
           return "sold out";
       }
   }
   ```

<details><summary>Answers</summary>

1. `static` — the method belongs to the class itself, so no object is needed to call it. `double` — the return type; calling it gives you back a `double`. `half` — the name you call it by. `(int n)` — the parameter list: it requires one `int`, referred to inside the body as `n`.

2. A parameter is the named placeholder in the method's definition (`n` in `static int square(int n)`). An argument is the actual value supplied at the call (`5` in `square(5)`). One is written once; the other is supplied fresh every call.

3. Yes to the first — `scale(int)` and `scale(double)` have different parameter types, which is a valid overload. No to the second: the return type is not part of what distinguishes overloads, so you get `error: method scale(int) is already defined in class ...`.

4. `error: missing return statement`. If `a > b` is false, the method falls off the end without returning anything, and every path out of a non-`void` method must return a value. The fix is to add `return b;` after the `if` block.

5. It prints `5`. Java passes by value: `bump` received a copy of the number, and `n = n + 1` changed only that copy. To actually update `count` you would write `static int bumped(int n) { return n + 1; }` and then `count = bumped(count);`

6. ```java
   static String status(int stock) {
       if (stock <= 0) {
           return "sold out";
       }
       return "in stock";
   }
   ```
   The guard version stays flat as cases are added — a new rule is one more `if` at the top, not another level of nesting inside an `else`. Everything below a guard can assume the guarded situation has been dealt with.

</details>

---

## Exercises

Three files are waiting in `exercises/`. All three **compile and run exactly as they are** — they just do the wrong thing until you fill in the `TODO`s. Run each one first so you can see the starting point, then run it again after every small change.

Full solutions live in `solutions/`, one file per exercise with the same filename. Give each one an honest attempt before you look. Getting stuck for five minutes and then working it out builds something that reading a finished answer does not. When you do compare, look at the shape of the solution rather than checking character by character — there is more than one good answer here.

### 1. `ReceiptRefactor.java`

**Goal:** a working but messy `main` prints a shop receipt. Extract seven methods, then rewrite `main` to call them — without changing the output by a single character.

**Done looks like:** the program prints exactly what it printed before you started, and `main` is a short list of named calls with no arithmetic left in it. The expected output is in the file's header comment.

**Hint:** work one method at a time and run the program after each. Start with `round2` — it is the smallest, and the expression `Math.round(amount * 100) / 100.0` appears six times, so extracting it alone already deletes five copies. Refactoring in small verified steps is the entire technique.

### 2. `PrimeTools.java`

**Goal:** write `isPrime`, then `countPrimesUpTo`, then `nthPrime`, with each one built on the one before.

**Done looks like:** `97 -> true`, `25 -> false`, 25 primes up to 100, 168 up to 1000, and the 100th prime coming out as 541.

**Hint:** `isPrime` starts with a guard clause — anything below 2 is not prime, so return `false` and get out. Then look for a divisor, and the moment you find one, `return false` immediately rather than setting a flag. Note that `countPrimesUpTo` should not contain a `%` anywhere: it asks `isPrime` and counts the yeses. That is the point of the exercise — small methods stacking up.

### 3. `Calculator.java`

**Goal:** one method per arithmetic operation, an overloaded three-number `add`, and an `apply` method that picks the right operation from a `char`.

**Done looks like:** the full table from the file's header, including `12.0 / 0.0 = NaN` instead of a crash, and `apply(9.0, '?', 3.0) = NaN` for an operator nobody recognises.

**Hint:** `divide` is where the guard clause goes — check `b == 0` first and return `Double.NaN` before touching the division. (`Double.NaN` is a built-in `double` value standing for "Not a Number"; it is what you hand back when there is no sensible answer, and printing it shows `NaN`.) For the three-number `add`, resist retyping `a + b + c`; call your own two-number `add` twice and the actual addition stays in exactly one place.

---

**Next up:** module 05 introduces classes and objects — and finally explains what that word `static` has been keeping from you.
