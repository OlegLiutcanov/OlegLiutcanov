# Module 07 — toString, equals, and hashCode

**Estimated time:** ~45 minutes

**What you will learn**

- What `System.out.println(myObject)` really does, and how to make it print something worth reading
- Why two objects holding identical data insist they are not equal — and the one-line reason
- How to write a correct `equals`, step by step, in the order the steps have to happen
- What `hashCode` is for and the contract that ties it to `equals`
- Why `String` seemed to break the "always use `.equals`" rule from modules 02 and 03
- `Objects.equals` and `Objects.hash`, the two helpers that do the fiddly parts for you
- `record` — one line that gives you all three methods for free, and when it fits

This is the shortest module of the day, and it punches far above its weight. *"Explain `equals` and `hashCode` and the contract between them"* is one of the most reliably asked questions in Java interviews, at every level. Forty-five minutes here buys you an answer you can give with confidence for years. It is also the module that stops a whole family of silent bugs, which matters more than the interviews do.

Every example in `examples/` is a complete program. Run one like this:

```
cd examples
java PointBefore.java
```

---

## 1. Three methods you already have

Here is a class with nothing in it:

```java
class Point {
    private final int x;
    private final int y;
}
```

That class already has a `toString()` method. It already has an `equals()` method. It already has a `hashCode()` method. You can call all three right now.

The reason: every class in Java, without writing anything, extends a built-in class called **`Object`**. If you do not say `extends` anything, Java quietly writes `extends Object` for you. Module 06 taught that a subclass inherits its superclass's methods — and `Object` sits at the top of every family tree, so its methods reach every class ever written.

`Object` gives you a handful of methods. These three are the ones you will meet constantly:

| Method | What `Object`'s version does |
|---|---|
| `toString()` | returns `ClassName@1b6d3586` |
| `equals(Object o)` | returns true only if it is *literally the same object* |
| `hashCode()` | returns a number that is different for each object |

Notice that all three defaults are *technically correct and practically useless*. `Object` has never seen your class and has no idea that `x` and `y` are what make a `Point` a `Point`. It cannot guess.

**This module is about replacing all three defaults.** That is the entire job.

> **If you know Python**
> Same idea as everything inheriting from `object`. `toString` is roughly `__repr__`, `equals` is `__eq__`, `hashCode` is `__hash__`. The defaults behave much the same way in both languages — but Python protects you from one mistake Java does not, which we get to at the end of section 7.

---

## 2. `toString` — teaching a class to describe itself

Run `java PointBefore.java` and look at the first block:

```
=== Surprise 1: printing tells you nothing ===
  println(a) -> PlainPoint@2a139a55
  println(b) -> PlainPoint@14ae5a5
```

Two points, both holding `(3, 4)`, both printed as line noise. (Those hex digits are arbitrary and will differ on your machine — nothing should ever depend on them.)

Here is the thing worth understanding: **`println` has no special power to print objects.** When you hand it an object, it calls `toString()` on that object and prints whatever comes back. Same when you use `+` to glue an object onto a string. These three lines all do the identical thing:

```java
System.out.println(a);
System.out.println("" + a);
System.out.println(a.toString());
```

So if the output is unhelpful, that is not `println`'s fault. It is your class's.

Fixing it takes four lines:

```java
@Override
public String toString() {
    return "Point(" + x + ", " + y + ")";
}
```

`@Override` is the annotation from module 06 — a note to the compiler saying "this had better be replacing something real". It costs nine characters and catches typos. Keep using it.

`public String` matters too: you are replacing `Object`'s method, so the signature has to match exactly. Return a `String`, take no parameters.

Now the same program (`java PointAfter.java`) prints:

```
  println(a)     -> Point(3, 4)
  "start: " + a  -> start: Point(3, 4)
  a.toString()   -> Point(3, 4)
```

**Why it exists:** so you can print an object and understand it. This pays off most when you are debugging at an awkward hour, staring at a log line or a debugger watch window. `Order@4f2410ac` tells you nothing. `Order(id=88, items=3, total=41.50)` tells you everything.

A few practical habits:

- Include the fields that identify the object. Leave out the noisy ones.
- Make it readable for a human. There is no required format.
- Never put a password, token, or anything secret in a `toString`. Those strings end up in log files.
- Do not write code that *parses* a `toString` to get data back out. Use the getters. `toString` is for eyes, not for logic.

> **If you know Python**
> Java has one method where Python has two. `toString` covers the ground of both `__str__` and `__repr__`.

---

## 3. `==` asks one question, and it is not the one you want

Module 05 introduced this and it is worth restating precisely, because everything in this module rests on it.

A variable does not hold an object. It holds a **reference** — an arrow pointing at one. And for any object, `==` compares the arrows:

> **`==` asks: are these two arrows pointing at the very same object?**

It never looks inside. It never compares fields. From `PointBefore.java`:

```
  a == b     -> false   (two separate objects)
  a == alias -> true    (one object, two names)
```

`a` and `b` were built by two separate `new` calls, so there are genuinely two objects sitting in memory. `==` reports that honestly. `alias` was assigned from `a`, so it is a second arrow to one object, and `==` reports that honestly too.

None of this is a bug. `==` is doing its job perfectly. It is simply answering a question you usually did not mean to ask.

> **If you know Python**
> Java's `==` on objects is Python's `is`. Python's `==` is Java's `.equals`. If you keep translating `==` to `is` in your head, this whole module gets easier.

---

## 4. The aha: `equals` starts life as `==` in disguise

So we use `.equals()` instead. Third block of `PointBefore.java`:

```
=== Surprise 3: equals says they are different TOO ===
  a.equals(b)     -> false
  a.equals(alias) -> true
```

`a.equals(b)` is **false**. Two points, both `(3, 4)`, and `equals` says no.

This is the moment that catches everybody, so let us be very clear about why. You never wrote an `equals` method for `PlainPoint`. So `a.equals(b)` runs the one it inherited from `Object`, and here is that method, in full, as it actually exists in the JDK:

```java
public boolean equals(Object obj) {
    return (this == obj);
}
```

That is it. **`Object.equals` is literally `==` with extra typing.** Until you override it, `.equals()` and `==` give the same answer for your classes, always.

So "use `.equals` for objects" was never the whole rule. The real rule is:

> **`.equals` compares contents — but only for classes where somebody wrote an `equals` that does.**

`String` has one. `Integer` has one. The classes you write have one when you write it. That is the job.

The last block of the example makes the sting clear:

```
=== The fields were equal the whole time ===
  a.getX() == b.getX() && a.getY() == b.getY() -> true
```

The data matched all along. Java just never looked, because nobody told it to.

---

## 5. A short detour: why `String` looked like an exception

Modules 02 and 03 both told you to compare text with `.equals` and never `==` — module 03 going as far as "no exceptions", while module 02 admitted that `String` sometimes *seems* to break the rule and promised the explanation here. Time to settle it. Run `java StringIdentityDemo.java`:

```
  s1 == s2      -> true    <- this is why people stop trusting the rule
  s1 == s3      -> false   (new String always makes a new object)
  s1.equals(s3) -> true    (equals looks at the characters)
  s1 == s4      -> true    ("ja" + "va" is worked out at compile time)
  s1 == s5      -> false   (a variable is involved, so joined at run time)
```

The explanation is a storage trick, not a special rule about `==`. Java keeps a **string pool**: a shared table of every text literal in your program. When two places in your code both write `"java"`, the pool hands out **the same object** to both. So `s1 == s2` is true — not because `==` compares characters, but because there is only one object involved and `==` is doing exactly what it always does.

`s4` is `"ja" + "va"`. Both halves are literals, so the compiler works the joining out before your program ever runs and puts the finished `"java"` in the pool. Same object again.

`s5` joins a *variable* to a literal. That happens while the program runs, producing a fresh object that is not in the pool. `==` is false.

Look at `s4` and `s5` side by side in the source. They look the same. One works, one does not, and the difference is invisible.

And that is the argument. Text that reaches your program from a `Scanner`, a file, or a network call is always built at run time, so `==` on it is false essentially always — and your `if` silently never fires, with no error to point at.

**`String` was never an exception.** It is an ordinary object whose `equals` compares characters, and `==` on it means what `==` always means.

---

## 6. Writing `equals`, step by step

Here is the whole method for `Point`. Read it once, then we will walk the four steps in order — because the order is the part that matters.

```java
@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    Point other = (Point) o;
    return x == other.x && y == other.y;
}
```

**Step 0 — the signature is `equals(Object o)`, not `equals(Point p)`.**

This is not negotiable, and getting it wrong is the single most common mistake in this whole area (mistake #1 below). You are replacing a method that takes an `Object`, so yours must take an `Object` too. Yes, that means you accept a parameter that could be anything at all. The next two steps are how you deal with that.

**Step 1 — the same-object shortcut.**

```java
if (this == o) {
    return true;
}
```

If the two arrows point at one object, we are done — no object is unequal to itself. This is an optimisation, not a requirement, but it is free and it is what everyone writes.

**Step 2 — null and type check.**

```java
if (o == null || getClass() != o.getClass()) {
    return false;
}
```

Two guards in one line.

`o == null` because `x.equals(null)` must return `false`, not throw. Somebody will pass you a null eventually.

`getClass() != o.getClass()` because somebody will pass you a `String`, or a `LocalDate`, or anything else. `getClass()` is another method from `Object`; it returns the actual class of an object at run time. If the classes differ, the answer is `false` and we stop.

That check has a second, load-bearing job: it makes step 3 safe.

**Step 3 — the cast.**

```java
Point other = (Point) o;
```

The parameter is typed `Object`, so the compiler will not let you write `o.x` — as far as it knows, `o` might be a `Thread`. A **cast** is you telling the compiler "trust me, this really is a `Point`". Module 02 introduced casts for numbers — `(int) price` — and this is the same notation doing the same job for object types.

The compiler takes your word for it, and if you are wrong your program throws `ClassCastException` at run time. Which is precisely why step 2 comes first: by the time you reach the cast, you have *proved* the types match, so it can never fail. Delete step 2 and you have planted a crash (mistake #4).

**Step 4 — compare the fields.**

```java
return x == other.x && y == other.y;
```

And here the rule splits in two, which is easy to remember once you have said it out loud:

- **Primitive fields** (`int`, `double`, `boolean`, `char`, …) — compare with `==`. There are no references involved, so `==` compares actual values. Correct.
- **Object fields** (`String`, `LocalDate`, another class of yours, …) — never `==`. Use `Objects.equals(a, b)`.

`Objects.equals` is a small static helper from `java.util.Objects`:

```java
import java.util.Objects;

return amountInCents == other.amountInCents
        && Objects.equals(currency, other.currency);
```

It calls `currency.equals(other.currency)` for you, except that it checks for null first and returns `false` rather than throwing. If you write `currency.equals(...)` yourself and `currency` happens to be null, you get a `NullPointerException` from inside your own `equals` (mistake #5) — a genuinely annoying bug to track down. Let the helper handle it.

Also note that once you are inside the method, `other.x` and `other.currency` work fine even though those fields are `private`. `private` means "this class", not "this object" — a `Point` may read another `Point`'s innards.

### The rules your `equals` has to obey

Java asks for a few properties. They sound formal; they are all common sense, and stating them cleanly is an interview answer in itself:

- **Reflexive** — `a.equals(a)` is true. (Step 1 gives you this.)
- **Symmetric** — if `a.equals(b)` then `b.equals(a)`.
- **Transitive** — if `a.equals(b)` and `b.equals(c)` then `a.equals(c)`.
- **Consistent** — same answer every time, as long as nothing changed.
- **Null-safe** — `a.equals(null)` is false, never an exception. (Step 2 gives you this.)

Follow the four-step recipe and you satisfy all five without thinking about it.

### `getClass()` or `instanceof`?

You will see a second style around, using Java's pattern matching:

```java
@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (!(o instanceof Point p)) {   // checks the type AND casts, in one go
        return false;
    }
    return x == p.x && y == p.y;
}
```

`o instanceof Point p` asks "is `o` a `Point`?" and, if so, hands you a ready-made `Point` variable called `p`. It handles null on its own (null is never an `instanceof` anything), so it collapses steps 2 and 3 into one line. It is idiomatic modern Java and you should be able to read it.

The one difference: `instanceof` accepts subclasses, `getClass()` demands the exact class. That distinction can break the symmetry rule when inheritance is in play. Use the `getClass()` version as your default — it is what IDEs generate — and know the other one when you meet it.

**Why `equals` exists:** because "is this the same object in memory" is almost never the question your program is actually asking. Your program wants to know whether two orders are the same order, whether a username is already taken, whether the price changed. All of those are questions about contents.

---

## 7. `hashCode` — the contract nobody can afford to break

`hashCode()` returns an `int`. `Object`'s version gives each object its own number, unrelated to any of its fields.

Only one rule really matters, and it is the interview answer:

> **If `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must also be true.**

And its two footnotes:

- The reverse is *not* required. Two unequal objects may share a hash code. That is called a collision and it is fine.
- Whatever you do, be consistent: the same object with the same field values must return the same number every time you ask.

Which leads straight to the practical rule:

> **Override `equals` and `hashCode` together, from the same fields, or not at all.**

Overriding one and not the other is a broken class. It compiles. It runs. It ruins your day later.

### Why the rule exists

Run `java HashCodeMatters.java`. It uses a `HashSet` (a bag that refuses duplicates) and a `HashMap` (a lookup table) — both covered properly on day 2, and both named "hash" for the reason we are about to see.

First, `BrokenCity`, which overrides `equals` and forgets `hashCode`:

```
  paris1.equals(paris2) -> true    (equals works perfectly)
  same hash code?       -> false   (and here is the crack)

  Added two equal cities to a HashSet.
  set size          -> 2   (should be 1)
  set contains an identical new BrokenCity? -> false
  look the population up with an equal key -> null
```

The set holds two copies of one city. A lookup with a perfectly equal key returns `null`. And nothing threw, nothing warned — the data is just quietly wrong, which is the worst kind of wrong.

The reason is how these collections find things. A `HashSet` does not compare your object against everything it holds; that would get slower and slower as it filled up. Instead it turns your object into a number, opens **only the drawer with that number**, and calls `equals` on the two or three things inside.

So: two equal objects with different hash codes go into **different drawers**. When the set goes looking, it opens the wrong drawer, finds nothing, and concludes the object is not there. `equals` is never even called. It never gets its chance to say yes.

Now `City`, with both methods written from the same two fields:

```
  set size     -> 1   (the duplicate was recognised)
  set contents -> [Paris (FR)]   (toString, doing its job)
  contains an identical new City? -> true
  population.get(new City("Paris", "FR")) -> 2100000
```

### `Objects.hash` does the mixing

You do not invent the number yourself:

```java
@Override
public int hashCode() {
    return Objects.hash(x, y);
}
```

List the same fields your `equals` reads. `Objects.hash` combines them with a fixed recipe, so equal fields always produce the same number — which is the contract, satisfied automatically. In `PointAfter.java` you can see it land:

```
  a.hashCode() -> 1058
  b.hashCode() -> 1058
  a.equals(b) && a.hashCode() == b.hashCode() -> true
```

A curiosity worth knowing, because interviewers like it: `return 1;` is a *legal* `hashCode`. Equal objects would certainly return equal numbers. But every object lands in drawer 1, every lookup degenerates into checking everything one at a time, and your fast collection becomes a slow list. Legal, correct, and terrible — a nice illustration that the contract is about correctness and the field-mixing is about speed.

**Why `hashCode` exists:** so hash-based collections can find your object almost instantly instead of scanning. You rarely call it yourself. You write it so that `HashMap` and `HashSet` work, and on day 2 you will use those constantly.

> **If you know Python**
> Python enforces this for you: define `__eq__` without `__hash__` and your class becomes unhashable, so `set()` and `dict` reject it loudly and immediately. Java does not protect you. It compiles the broken class, runs it, and lets you find out from wrong data. (`javac -Xlint:all` will mention it, but nothing makes you run that.)

---

## 8. Yes, your IDE writes these for you

In IntelliJ or Eclipse: right-click, *Generate*, *equals() and hashCode()*, tick the fields, done. You get the four-step `equals` and an `Objects.hash` call, near enough to what you just learned.

**Using that is completely normal professional practice.** Nobody types these by hand day to day, and nobody is impressed that you can. Hand-writing them is more error-prone, not more virtuous.

But you must be able to **read** what it generated, know **why** each line is there, and say what breaks if one goes missing — because that is what gets asked in code review and in interviews. That is exactly what section 6 and section 7 were for. Generate the code; understand every line of it.

---

## 9. `record` — all three, free

Java 16 added a shorter way to say "this type is just a bundle of values". Here is a complete, fully functional class:

```java
record RecordPoint(int x, int y) { }
```

That single line gives you:

- two `private final` fields, `x` and `y`
- a constructor taking `(int x, int y)`
- accessor methods `x()` and `y()` — note the naming, no `get` prefix
- `toString()`, printing `RecordPoint[x=3, y=4]`
- `equals()`, comparing every component
- `hashCode()`, consistent with that `equals`

Run `java PointRecord.java`:

```
  println(a) -> RecordPoint[x=3, y=4]
  a == b            -> false   (== is unchanged, as always)
  a.equals(b)       -> true
  a.equals(null)    -> false
  a.equals("three") -> false
  a.equals(b) && a.hashCode() == b.hashCode() -> true
```

The `x` and `y` in the parentheses are the record's **components**. Everything above is generated from them.

Note `a == b` is still `false`. Records change nothing about `==`. Two `new` calls are still two objects, forever.

Records are not limited to being empty. You can add methods:

```java
record RecordPoint(int x, int y) {
    double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    RecordPoint movedBy(int dx, int dy) {
        return new RecordPoint(x + dx, y + dy);   // a new record; this one is untouched
    }
}
```

And you can validate what comes in, using a **compact constructor** — no parameter list, no assignments, just your checks. Java still does the assigning. (`throw` raises an error object that abandons the constructor on the spot and travels back to whoever called it, so no object is handed over at all. Module 08 is entirely about that machinery; here, just read it as "refuse, and say why".)

```java
record Rating(int stars) {
    Rating {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("stars must be 1 to 5, got " + stars);
        }
    }
}
```

```
  new Rating(4) -> Rating[stars=4]
  new Rating(9) -> rejected: stars must be 1 to 5, got 9
```

### When a record fits

A record is **immutable**: its fields are `final` and there are no setters. Trying to write one is a compile error. That is a feature, not a limitation — and it quietly prevents a nasty bug. Put a mutable object in a `HashSet`, then change a field that `hashCode` uses, and the object goes missing from the set it is sitting in. The last block of `HashCodeMatters.java` does exactly that:

```
  contains before -> true
  contains after  -> false
  set still holds -> [Tag(blue)]
```

It is right there in the set — you can see it in the printed contents — and the set cannot find it, because it changed drawers without telling anyone. Immutable data cannot do that to you.

So:

> **Reach for a `record` when the type is a small, immutable bundle of values.** Coordinates, a money amount, a database row, a search filter, the result of a calculation — anything you would call a DTO (a *Data Transfer Object*: a plain carrier of values with no real behaviour of its own).
>
> **Write an ordinary class when** the object has real behaviour, needs mutable state, needs to extend another class (records cannot), or needs an `equals` that ignores some of its fields.

That last one comes up: a record's `equals` always uses *every* component. If two `User` objects should count as equal when their `id` matches regardless of `lastLoginTime`, a record cannot express that. Write the class and the method.

> **If you know Python**
> A record is close to a frozen `@dataclass`, or to `NamedTuple`. Same trade: declare the fields, get the boilerplate handed to you.

---

## Common beginner mistakes

### 1. Writing `equals(Point p)` instead of `equals(Object o)`

The classic. You want to compare two `Point`s, so you write the parameter type you actually care about:

```java
@Override
public boolean equals(Money other) { return amount == other.amount; }
```

```
error: method does not override or implement a method from a supertype
```

With `@Override` present, the compiler stops you — this is the annotation earning its keep, exactly as module 06 promised. Without `@Override`, **it compiles silently**, and you have not overridden anything. You have added a *second, unrelated* method that happens to share a name, an **overload**. Watch what that does:

```
direct x.equals(y)      -> true
x.equals((Object) y)    -> false
HashSet size            -> 2
```

Your own direct call picks the new method and looks fine. Every collection in the JDK holds your object as an `Object` and calls the inherited version, which is `==`, so everything quietly misbehaves. Hours vanish into this one.

**Fix:** the parameter is `Object`. Always. Then check the type inside, as in section 6. And put `@Override` on it so this can never happen to you.

### 2. Overriding `equals` and forgetting `hashCode`

```java
class BrokenCity {
    @Override public boolean equals(Object o) { /* correct */ }
    // no hashCode
}
```

No error. No warning. Nothing at all from the compiler by default. Then:

```
set size          -> 2   (should be 1)
look the population up with an equal key -> null
```

**Fix:** write both, from the same fields, at the same time. If you remember one sentence from this module, make it that one. (`javac -Xlint:all` will warn — `Class BrokenCity overrides equals, but neither it nor any superclass overrides hashCode method` — but no one runs that by default.)

### 3. Comparing object fields with `==` inside `equals`

```java
return amount == other.amount && currency == other.currency;   // currency is a String
```

No error, and it may even work in your first test, because short literal strings come from the pool (section 5). Then real data arrives from a file or a database, the strings are built at run time, and `equals` starts returning `false` for things that are obviously equal.

**Fix:** `Objects.equals(currency, other.currency)` for every object field. `==` is only for primitives.

### 4. Casting before checking the type

```java
@Override
public boolean equals(Object o) {
    Money other = (Money) o;      // no null check, no class check
    return amount == other.amount;
}
```

Compiles fine. Then someone calls `m.equals("hello")`:

```
Exception in thread "main" java.lang.ClassCastException: class java.lang.String cannot be cast to class Money4
	at Money4.equals(P4.java:12)
```

**Fix:** the `o == null || getClass() != o.getClass()` line, *before* the cast. That is the reason the steps are in that order.

### 5. `NullPointerException` from inside your own `equals`

```java
return name.equals(other.name);   // fine until name is null
```

```
Exception in thread "main" java.lang.NullPointerException:
    Cannot invoke "String.equals(Object)" because "this.name" is null
	at City5.equals(P5.java:15)
```

Note *whose* field is null: `this.name`, your own object's. Guarding the parameter did not help.

**Fix:** `Objects.equals(name, other.name)`, which is null-safe on both sides.

### 6. `public void toString()`

```java
@Override
public void toString() { System.out.println("hi"); }
```

```
error: toString() in Thing cannot override toString() in Object
    public void toString() { System.out.println("hi"); }
                ^
  return type void is not compatible with String
```

`toString` **returns** text; it does not print it. `println` prints the string you hand back.

**Fix:** `public String toString() { return "hi"; }`

### 7. `hashcode()` with a lowercase `c`

```java
@Override
public int hashcode() { return name.hashCode(); }
```

```
error: method does not override or implement a method from a supertype
```

Java is case-sensitive and the real name is `hashCode`. Same trap as mistake #1: with `@Override` you get this clear error, without it you get a silently ignored method and a class that is quietly broken.

**Fix:** correct the spelling — or better, let the IDE generate it and never type it at all.

### 8. Expecting `==` to compare contents

```java
if (point1 == point2) { ... }
```

No error. Just an `if` that is false whenever the two objects were created separately — which is most of the time.

**Fix:** `if (point1.equals(point2))`, and make sure `Point` actually has an `equals`. Both halves are required.

---

## Check yourself

1. `System.out.println(myOrder)` prints `Order@4f2410ac`. What method is `println` calling, and what do you have to do about it?
2. You build two objects with identical field values and `a.equals(b)` comes back `false`. What is `equals` doing, and what did you forget?
3. In the four-step `equals`, why must the `getClass()` check come *before* the cast? What happens if you swap them?
4. State the `equals`/`hashCode` contract in one sentence. If you override `equals` and skip `hashCode`, what does the compiler say, and what goes wrong?
5. `String a = "hi"; String b = "hi"; a == b` prints `true`. Does that mean `==` is safe for text? Why or why not?
6. `record Money(int cents, String currency) { }` — list what that line gives you. Name one situation where you should write the class out by hand instead.

<details><summary>Answers</summary>

1. It is calling `myOrder.toString()`, inherited from `Object`, which returns the class name plus a hash code in hex. `println` was never doing anything cleverer than asking the object to describe itself. Override `toString()` in `Order` to return something readable like `"Order(id=88, total=41.50)"`.

2. It is running `Object.equals`, which is `return (this == obj);` — a reference comparison. You forgot to override `equals` in your class. Until you do, `.equals` and `==` give identical answers for your own types.

3. Because the cast `(Point) o` throws `ClassCastException` if `o` is not a `Point`, and the parameter is declared `Object` so it could be anything. The `getClass()` check proves the type first, which makes the cast guaranteed safe. Swap them and `x.equals("hello")` crashes instead of returning `false`. (The same guard also handles `null`.)

4. **If two objects are equal, they must return the same hash code.** The compiler says nothing at all by default — it compiles and runs happily. What breaks is every hash-based collection: `HashSet` will store visible duplicates, and `HashMap.get` will return `null` for a key that is equal to one you stored, because the lookup opens the wrong drawer and never calls `equals`.

5. No. Both variables are literals, and Java's string pool hands out one shared object for identical literals — so there is genuinely only one object and `==` is answering "same object?" correctly, by coincidence. Change either side to something built at run time (user input, a file, `prefix + "va"`) and `==` becomes `false` while the characters are identical. Use `.equals` for text, always.

6. Two `private final` fields; a `Money(int, String)` constructor; accessors `cents()` and `currency()`; `toString()` printing `Money[cents=1250, currency=EUR]`; `equals()` comparing both components; and a matching `hashCode()`. Write it by hand when the type needs mutable state, needs to extend another class, has real behaviour beyond holding data, or needs an `equals` that ignores some fields (equality by `id` only, say) — a record's `equals` always uses every component.

</details>

---

## Exercises

Work in `exercises/`. Each starter **already compiles and runs** — it just gives wrong answers. You start green and stay green, running the program after each small change.

Solutions are in `solutions/`, one file per exercise with the same filename. Have an honest go first. In this module especially, the failing output *is* the lesson: watching `equals` say `false` about two identical objects lodges the idea far better than reading that it does.

### 1. `MoneyDemo.java` — write all three methods

**Goal:** give `Money` (an amount in cents plus a currency code) a proper `toString`, `equals`, and `hashCode`. `main` is a test harness of eleven checks; do not change it.

**Done looks like:** every check reports `true`:

```
1.  toString() is "12.50 EUR"                        true
2.  a.equals(a) - a Money equals itself              true
3.  a.equals(b) - same amount, same currency         true
...
11. a HashSet stores the two equal Moneys once       true

11 of 11 checks passed.
For the record, a prints as: 12.50 EUR
```

The starter passes 6 of 11. Look at *which* six before you start — they are all the "should be false" ones. The inherited `equals` is excellent at saying no and hopeless at saying yes.

**Hint:** work top to bottom and re-run after each method. `toString` first (check 1), then `equals` using the four steps from section 6 (checks 2–9), then `hashCode` last (checks 10 and 11). If 10 and 11 fail while 3 passes, you have written `equals` and skipped `hashCode` — mistake #2, live in your own code. `amountInCents` is an `int` so it compares with `==`; `currency` is a `String` so it needs `Objects.equals`.

### 2. `ConvertToRecord.java` — delete a class, keep the behaviour

**Goal:** replace a 25-line hand-written `Book` class with a one-line record, then add a compact constructor that rejects a year below 1. `main` does not change at all.

**Done looks like:**

```
1. book1                -> Book[title=Dune, author=Frank Herbert, year=1965]
2. book1.equals(book2)  -> true
3. book1 == book2       -> false
4. same hash code       -> true
5. book1.title()        -> Dune
6. book1.equals(book3)  -> false
7. new Book(..., 0)     -> rejected: year must be a real year, got 0
```

**Hint:** line 3 stays `false` and that is correct — a record does not change what `==` means. The reason `main` keeps compiling after you swap the class out is that its accessors are already named `title()`, `author()` and `year()`, which are exactly the names a record generates. There is a stretch goal at the bottom of the file: try adding a setter to the record and read the error you get. It is a good one.

### 3. `EqualityQuiz.java` — predict, then run

**Goal:** ten `==`-versus-`equals` expressions across `String`s and objects. Write your prediction as `"true"` or `"false"` before running anything.

**Done looks like:** all ten questions report `match? true`. The starter prints `match? false` ten times, which is correct for a file full of `TODO`s.

**Hint:** read the two classes at the bottom first — `Plain` has no `equals`, `Tag` has one — because that difference is most of the quiz. For the `String` questions, ask yourself of each one: *is this a literal the pool could have shared, or was it built while the program ran?* And commit to a wrong answer rather than working it out cautiously; being surprised is what makes it stick.

---

**Where this goes next:** day 2 opens with collections, and `HashMap` and `HashSet` are two of the first things you will meet. Everything you put in them leans on the contract from section 7. You will also find that most of the small types you write from here on — a coordinate, a price, a parsed row, a result object — want to be records, and you will get all three methods for free without thinking about it. Which is exactly why it was worth understanding what you are getting.
