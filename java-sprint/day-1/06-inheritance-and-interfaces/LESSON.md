# Module 06 — Inheritance, Interfaces, and Polymorphism

**Estimated time:** ~75 minutes

**What you will learn**

- How `extends` lets one class reuse another class's fields and methods
- What `super(...)` does in a constructor, and why the compiler insists on it
- How to override a method, and why `@Override` saves you from a bug that is nearly invisible without it
- What `protected` means and when to reach for it
- **Polymorphism** — one variable type, many actual objects, one loop that handles them all
- Abstract classes and abstract methods: describing something real that is still too vague to build
- Interfaces: a contract a class promises to keep, and why one class can sign several
- A one-line rule for choosing between an interface and an abstract class

Every example in `examples/` is a complete program. Run one like this:

```
cd examples
java ShapesDemo.java
```

No compile step, no project setup — Java 21 will compile and run a single file for you.

One rule of that single-file mode to keep in mind from the start: the class holding `main` must be the **first** class in the file. That is why the demo class sits at the top of every example and the supporting classes come after. (Module 05's rule still applies too — only that first class may be `public`, and its name must match the filename.)

---

## 1. The problem inheritance solves

Imagine you are writing a payroll program. You have an `Employee` class with a name, a salary, and a `describe()` method. Now the company adds managers. A manager has a name, a salary, a `describe()` method... and also a team size and a bonus.

The tempting move is copy-paste: duplicate `Employee`, rename it `Manager`, add the two new bits. It works. It also means that every future fix — a typo in `describe()`, a new tax rule — has to be made twice, and one day you will forget one of them.

Inheritance is Java's answer: write the shared part once, and let `Manager` say *"I am an Employee, plus a bit more."*

## 2. `extends` — a subclass gets everything

```java
class Employee {
    protected final String name;
    private final int baseSalary;

    Employee(String name, int baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    int yearlyPay() {
        return baseSalary;
    }
}

class Manager extends Employee {   // Manager IS-A Employee
    private final int teamSize;
    // ...
}
```

Some vocabulary, because it comes up constantly:

- **Superclass** (or parent, or base class) — `Employee`, the one being extended.
- **Subclass** (or child) — `Manager`, the one doing the extending.
- **`extends`** — the keyword that creates the link.

(`final` on a field means what it meant for the constants in module 02: assigned once — here in the constructor — and never reassigned afterwards. `protected` gets its own section below.)

A `Manager` object now *contains* everything an `Employee` has. It has a `name`. It has a `yearlyPay()` method. You did not type them again; you inherited them.

A subclass can extend exactly one superclass. Java has no multiple inheritance of classes — if you try to write `extends A, B` you get a syntax error. (Interfaces, later in this lesson, are how Java gives you the useful part of that idea without the chaos.)

**Why it exists:** so shared behaviour lives in exactly one place. Fix it once, every subclass gets the fix.

> **If you know Python**
> `class Manager(Employee):` is the same idea. The keyword differs (`extends`), and Java allows only one superclass where Python allows several.

## 3. `super(...)` — build the inherited part first

An object is built from the inside out. Before `Manager` can set up its own fields, the `Employee` part of it has to exist. That is what `super(...)` does — it calls the superclass constructor.

```java
class Manager extends Employee {
    private final int teamSize;

    Manager(String name, int baseSalary, int teamSize) {
        super(name, baseSalary);   // must be the FIRST statement
        this.teamSize = teamSize;
    }
}
```

Two rules worth memorising:

1. `super(...)` must be the very first statement in the constructor. Nothing may come before it.
2. If you do not write it, Java quietly inserts `super()` — a call to the *no-argument* superclass constructor. If the superclass has no no-argument constructor, that silent insertion fails and you get a compiler error. This is mistake #1 below, and it catches everyone once.

There is also `super.methodName()`, which calls the superclass's version of a method you have overridden. `Manager.describe()` in the example uses it to reuse `Employee`'s sentence and add to it rather than rewriting it.

**Why it exists:** to guarantee that no object is ever half-built. By the time your constructor body runs, everything you inherited is already initialised.

## 4. Overriding, and why `@Override` earns its keep

**Overriding** means a subclass replaces a method it inherited with its own version. Same name, same parameters, new body.

```java
class Manager extends Employee {
    @Override
    int yearlyPay() {
        return getBaseSalary() + 1000 * teamSize;
    }
}
```

`@Override` is an **annotation** — a note to the compiler. It does not change what the code does. It makes one demand: *"this method had better be replacing something that exists in a supertype."* If it isn't, compilation fails. (A **supertype** is anything above you in the tree: a class you `extends`, or — from section 8 onward — an interface you `implements`. The word turns up in real compiler messages, so it is worth knowing.)

That sounds like a small favour. It is not. Watch what happens without it — this is `examples/OverrideTypoDemo.java`:

```java
class Account {
    private final String id;
    private final int balance;

    int interestRate() { return 0; }

    String summary() {
        return id + ": balance " + balance + ", rate " + interestRate() + "%";
    }
}

class BrokenSavings extends Account {
    int intrestRate() { return 4; }   // typo: intrest, not interest
}
```

Java compiles this without a murmur. It does not see a broken override; it sees a brand-new method called `intrestRate()` that happens to sit next to an inherited `interestRate()`. Nothing calls the new one. `summary()` keeps calling the inherited one. Your savings account silently pays 0% interest, and there is no error message anywhere to lead you to it.

Run the example and you can watch it happen:

```
A-1: balance 100, rate 0%
S-9: balance 100, rate 0%
S-10: balance 100, rate 4%

broken.interestRate() -> 0   (inherited from Account; the override never happened)
broken.intrestRate()  -> 4   (a separate method nobody ever calls)
fixed.interestRate()  -> 4   (a real override)
```

Add `@Override` above `intrestRate()` and the bug stops being a mystery and becomes a compiler error you fix in ten seconds.

**Why it exists:** to turn a silent wrong-answer bug into a loud compile-time failure. Put `@Override` on every override you write. It costs nine characters.

> **If you know Python**
> Python has the same trap — a misspelled method just becomes a new attribute — but no compiler to catch it, so you find out from a failing test or a confused user. `@Override` is Java handing you a check Python cannot offer.

## 5. `protected`, briefly

You have met `private` (only this class) and `public` (anybody). `protected` is the setting in between: **this class and its subclasses**.

```java
class Employee {
    protected final String name;   // Manager can read this
    private final int baseSalary;  // Manager cannot
}
```

In the `Manager` example, `name` is `protected` so subclasses can use it directly, while `baseSalary` stays `private` and subclasses go through `getBaseSalary()`. That is a normal, healthy mix.

**Why it exists:** to share a field with your subclasses without opening it up to the entire program. Use it sparingly — `private` plus a getter is usually the safer default, because a `protected` field is a promise you make to every subclass that will ever exist.

Run `java EmployeeDemo.java` to see sections 2 through 5 working together:

```
Ada takes home 50000 a year.
Grace takes home 94000 a year. (manages 4)

Ada's yearly pay:   50000
Grace's yearly pay: 94000
Grace leads 4 people.

Is Grace an Employee? true
Is Ada a Manager?     false
```

Look closely at line 2. `Manager.describe()` starts by calling `super.describe()`, which runs `Employee`'s code — and `Employee.describe()` says `name + " takes home " + yearlyPay() + " a year."`. Yet Grace's line shows **94000**, the *Manager* calculation, not the plain 90000 salary `Employee` knows about.

So even while executing a method written inside `Employee`, Java looked up `yearlyPay()` on the object it actually had — a `Manager` — and ran the override. The superclass does not get to call its own version just because it is the one asking. That is the idea the next section is built on.

## 6. Polymorphism — the payoff

**Polymorphism** means: a variable of a supertype can hold an object of any subtype, and when you call a method on it, Java runs the version belonging to the object that is *actually there* — not the version suggested by the variable's declared type.

This is where all the machinery above starts paying rent. From `examples/ShapesDemo.java`:

```java
Shape[] shapes = {
        new Circle(1.0),
        new Rectangle(3.0, 4.0),
        new Circle(2.5),
        new Rectangle(2.0, 2.0)
};

double total = 0.0;
for (Shape shape : shapes) {
    System.out.println(shape.describe());
    total += shape.area();
}
```

```
Circle     area =   3.14
Rectangle  area =  12.00
Circle     area =  19.63
Rectangle  area =   4.00
Total area of 4 shapes: 38.78
```

Read that loop again and notice what is *missing*. There is no `if (shape is a Circle) ... else if (shape is a Rectangle) ...`. The loop does not know how many kinds of shape exist. It asks each object for its area and each object answers in its own way.

Now the real payoff: **add a `Triangle` class and this loop does not change.** Not one character. That is exercise 2, and it is worth doing precisely because of how little you have to touch.

Compare it to the alternative, where a big `if/else` chain decides what to do based on a type code. Every new shape means finding and editing every one of those chains scattered through the program, and missing one is a bug. Polymorphism moves that decision into the objects themselves, where it can only be answered one way.

One limit to know about. The variable's declared type controls what you are *allowed* to call:

```java
Animal pet = new Dog();
pet.speak();   // fine - Animal has speak()
pet.fetch();   // COMPILE ERROR - Animal has no fetch(), even though Dog does
```

Java checks your call against `Animal`, because that is what the variable claims to be. If you need `fetch()`, declare the variable as a `Dog`.

**Why it exists:** so you can write code against a general idea ("a shape", "something payable") and add new specific cases later without editing the code that uses them.

## 7. Abstract classes — a `Shape` you cannot build

What is the area of "a shape"? The question has no answer. Every shape has an area, but you cannot compute one until you know *which* shape. Meanwhile `describe()` is perfectly writable in general terms.

An **abstract class** captures exactly that situation:

```java
abstract class Shape {
    abstract double area();      // no body - subclasses must supply one
    abstract String name();

    String describe() {          // a normal method, inherited by all shapes
        return String.format("%-10s area = %6.2f", name(), area());
    }
}
```

(One unfamiliar call in there: `String.format` builds a piece of text from a template and returns it instead of printing it. `%-10s` means "a string, padded out to ten characters, left-aligned" and `%6.2f` means "a decimal number in six columns, two places after the point". That is what lines the shape names and areas up into columns.)

Two new things:

- **`abstract class`** — a class that cannot be instantiated. `new Shape()` is a compile error. It exists to be extended.
- **`abstract` method** — a method with a signature and no body, ending in a semicolon. Any **concrete** subclass — meaning one that is not itself `abstract`, so you can actually `new` it — **must** implement it or refuse to compile.

That "must" is the valuable part. `abstract double area();` is the superclass saying: *I don't know how to do this, and I am not letting you create a shape that hasn't figured it out.* The compiler enforces it for you.

An abstract class is a normal class in every other respect — it can have fields, constructors, and ordinary methods with bodies. `Shape.describe()` is fully written even though it calls two methods that do not exist yet.

**Why it exists:** when several classes are genuinely the same kind of thing and share real code, but the base concept is too vague to instantiate on its own.

## 8. Interfaces — a contract, not a family

Inheritance is about **identity**: a `Manager` *is an* `Employee`. But sometimes what you want to say is much weaker: *these things can all be paid*, even though they are otherwise unrelated.

A contractor is a person. An invoice is a piece of paper. They have no meaningful common ancestor, and inventing one (`class PayableThing`) would be a lie. What they share is a capability.

An **interface** is a list of methods with no bodies — a contract. A class that says `implements Payable` promises to provide every method on the list, and the compiler holds it to that promise.

```java
interface Payable {
    String payee();
    int amountDue();
}

class Contractor implements Payable {
    @Override
    public String payee() { return name + " (contractor)"; }

    @Override
    public int amountDue() { return hoursWorked * hourlyRate; }
}

class Invoice implements Payable {
    @Override
    public String payee() { return supplier + " (invoice " + reference + ")"; }

    @Override
    public int amountDue() { return amount; }
}
```

An interface has **no state** — no instance fields to store data in. It describes what you can do, never what you are made of.

One syntax trap: methods coming from an interface must be declared **`public`** in the implementing class. Interface methods are public by definition, and Java will not let a class narrow that. Leave `public` off and you get the "weaker access privileges" error in mistake #5 below.

Interfaces are polymorphic in exactly the same way superclasses are — `examples/PayableDemo.java` loops over a `Payable[]` holding both kinds:

```
Nadia (contractor) -> 2200
Cloud hosting (invoice INV-2031) -> 320
Tom (contractor) -> 840
-----
Total to pay: 3360
```

**Why it exists:** to let unrelated classes be used interchangeably for one specific purpose, without forcing them into a family tree they don't belong in.

> **If you know Python**
> Python does this with duck typing: if it has the method, it works. Java wants the promise written down and checked at compile time. An interface is that written-down promise — closest to `abc.ABC` with `@abstractmethod`, but a Java class can implement many interfaces freely.

## 9. One class, several interfaces

A class extends **one** class but may implement **as many interfaces as it likes**, separated by commas:

```java
class ConcertTicket implements Describable, Refundable {
    // must provide every method from BOTH contracts
}
```

This is the practical reason interfaces exist alongside inheritance. A concert ticket can be describable *and* refundable *and* printable, all at once, without any awkward question about which one it "really is".

Each interface gives you a different view of the same object. In `examples/MultiInterfaceDemo.java`, a souvenir mug is `Describable` but not `Refundable`, so it appears in the first loop and not the second:

```
Everything in the bag:
  Concert: The Midnigh...
  Train: Berlin -> Pra...
  SOUVENIR: TOUR MUG
Refundable if the trip is cancelled: 81
```

## 10. Interface or abstract class?

Here is the beginner-sized rule of thumb. It will serve you well for a long time:

> **Interface = a capability.** Unrelated classes that can all *do* something. Reach for it by default.
>
> **Abstract class = a shared identity plus shared code.** Classes that genuinely *are* the same kind of thing and have real implementation to inherit.

Sanity-check it with the word "is": a `Circle` **is a** `Shape` — abstract class. An `Invoice` **is a** `Payable`? That reads wrong; an invoice **can be** paid — interface.

The other deciding factor is code and state. If you have actual fields and written method bodies to hand down, that is an abstract class. If you only have a list of promises, that is an interface.

When genuinely torn, pick the interface. It leaves your classes free to sign other contracts later; a superclass slot, once spent, is gone.

**One more thing, and then we move on:** interfaces may also contain `default` methods — methods that *do* have a body, which every implementing class gets for free and may override. `Describable.shortLabel()` in the multi-interface example is one. You will meet them in real libraries; you rarely need to write one early on.

---

## Common beginner mistakes

### 1. Forgetting `super(...)` when the superclass has no no-argument constructor

```java
class Animal { Animal(String n) { ... } }
class Dog extends Animal { Dog(String n) { this.x = 1; } }   // no super(...)
```

```
error: constructor Animal in class Animal cannot be applied to given types;
  required: String
  found:    no arguments
  reason: actual and formal argument lists differ in length
```

Confusing, because you never called `Animal()` anywhere. Java inserted `super()` for you, and `Animal` has no such constructor.
**Fix:** call it yourself as the first statement — `super(n);`

### 2. A typo'd override

```java
class Sub extends Base { @Override int ratee() { return 4; } }
```

```
error: method does not override or implement a method from a supertype
```

**Fix:** correct the spelling (or the parameter list — a different parameter type is also not an override). And note that this error is the *good* outcome. Without `@Override` there is no error at all, just a method nobody ever calls. See section 4.

### 3. Trying to instantiate an abstract class

```java
Shape s = new Shape();
```

```
error: Shape is abstract; cannot be instantiated
```

**Fix:** create a concrete subclass instead — `Shape s = new Circle(1.0);`. The variable can be typed `Shape`; only the `new` needs a real class.

### 4. Implementing an interface but missing a method

```java
interface Discountable { String label(); double basePrice(); }
class Book implements Discountable { public String label() { return "b"; } }
```

```
error: Book is not abstract and does not override abstract method basePrice() in Discountable
```

**Fix:** implement every method the interface lists. The phrase "is not abstract and does not override" always means: you signed a contract and left a clause unfulfilled. The same error appears when a subclass forgets an `abstract` method from its superclass.

### 5. Forgetting `public` on an interface method

```java
class Bill implements Payable { int amountDue() { return 5; } }
```

```
error: amountDue() in Bill cannot implement amountDue() in Payable
  attempting to assign weaker access privileges; was public
```

**Fix:** add `public`. Interface methods are public, and an implementation may never be less visible than the contract it fulfils.

### 6. Calling a subclass-only method through a superclass variable

```java
Animal pet = new Dog();
pet.fetch();
```

```
error: cannot find symbol
  symbol:   method fetch()
  location: variable pet of type Animal
```

The object really is a `Dog` at run time, but the compiler only trusts the declared type.
**Fix:** declare it as `Dog pet = new Dog();` if you need `fetch()`. This is why exercise 1's `pepper` variable is a `Dog` and not an `Animal`.

### 7. Reaching for a `private` field of the superclass

```java
class Employee { private int baseSalary = 10; }
class Manager extends Employee { int pay() { return baseSalary + 1; } }
```

```
error: baseSalary has private access in Employee
```

Subclasses inherit the field, but `private` means "this class only" — subclasses included.
**Fix:** use a getter (`getBaseSalary()`), or make the field `protected` if subclasses genuinely need direct access.

### 8. Giving an abstract method a body

```java
abstract class Shape { abstract double area() { return 0; } }
```

```
error: abstract methods cannot have a body
```

**Fix:** pick one. Either `abstract double area();` with a semicolon and no braces, or a normal method with a body and no `abstract` keyword.

---

## Check yourself

1. What does `super(name, salary)` do, and where in the constructor must it appear?
2. You override a method, misspell its name, and leave off `@Override`. What does the compiler say, and what does your program do when it runs?
3. `Shape` is abstract. Why is `new Shape()` an error, and what is the point of a class you can never instantiate?
4. Given `Animal pet = new Dog();` where `fetch()` exists only on `Dog` — does `pet.fetch()` compile? Why?
5. You are modelling `Circle`, `Rectangle`, and separately a `Loggable` capability that a `Circle`, a `DatabaseConnection`, and an `HttpRequest` all need. Which should be an abstract class and which an interface?
6. Can one class extend two classes? Can it implement two interfaces?

<details><summary>Answers</summary>

1. It calls the superclass constructor, building the inherited part of the object before the subclass's own fields are set. It must be the **first statement** in the constructor. If you omit it, Java inserts `super()` with no arguments, which fails to compile when the superclass has no no-argument constructor.

2. The compiler says **nothing** — it is a perfectly legal new method that happens to have a similar name. At run time your program calls the inherited original, so it silently does the old thing. Adding `@Override` turns this into `error: method does not override or implement a method from a supertype`, which is why you always add it.

3. Because `Shape` has abstract methods with no implementations — Java would not know what `area()` should return. The point is that it defines shared structure and shared code (like `describe()`) and *forces* every subclass to supply the missing pieces. It also gives you a type to declare variables and arrays with, which is what makes the polymorphic loop possible.

4. No. The compiler checks the call against the variable's **declared** type, `Animal`, which has no `fetch()`. You get `cannot find symbol`. The object really is a `Dog` at run time, but that is not what the compiler goes on. Declare the variable as `Dog`.

5. `Shape` is an abstract class — a circle genuinely **is a** shape, and they share real code. `Loggable` is an interface — a circle, a database connection, and an HTTP request have nothing else in common; they just share a **capability**.

6. No to classes — Java allows exactly one superclass. Yes to interfaces — as many as you like, comma-separated: `class Ticket implements Describable, Refundable`.

</details>

---

## Exercises

Work in `exercises/`. Each starter file **already compiles and runs** — it just prints placeholder values. That is deliberate: you start from green and stay green, running the program after each small change.

Solutions are in `solutions/`, one file per exercise with the same filename. Please have an honest go first — including getting the compiler errors and reading them. Struggling with `cannot find symbol` for four minutes teaches you more than reading the answer does.

### 1. `AnimalSounds.java` — override a method and watch polymorphism work

**Goal:** build an `Animal` / `Dog` / `Cat` hierarchy where `Dog` and `Cat` each override `speak()`, and give `Dog` a `fetch()` method that no other animal has.

**Done looks like:** running it prints exactly

```
Rex says Woof!
Whiskers says Meow!
Buddy says Woof!
Cleo says Meow!
Total animals: 4
Pepper runs after the ball and brings it back.
```

**Hint:** you never touch `describe()`. It already says `name + " says " + speak()`, and it will start producing the right sentences the moment your overrides are in place — that is the whole lesson in one line. Use `getName()` inside `fetch()`, since `name` is `private` in `Animal`.

### 2. `ShapeExtension.java` — add a subclass without touching existing code

**Goal:** add a working `Triangle` to the shape hierarchy. `Shape`, `Circle`, `Rectangle`, and `main` are marked do-not-edit, and you genuinely will not need to.

**Done looks like:** running it prints exactly

```
Circle     area =   3.14
Rectangle  area =  12.00
Triangle   area =  15.00
Total area: 30.14
```

**Hint:** a triangle's area is half the base times the height — but write it as `0.5 * base * height`, not `1 / 2 * base * height`. Dividing two whole numbers in Java throws the fraction away, so `1 / 2` is `0` and your triangle would have no area at all.

### 3. `DiscountDemo.java` — implement one contract from two unrelated classes

**Goal:** make `Book` and `GymMembership` both honour the `Discountable` interface, and write the `finalPrice` helper that works on anything discountable.

**Done looks like:** running it prints exactly

```
Clean Code                  45.00  -20%  ->     36.00
Annual Gym Pass            600.00  -15%  ->    510.00
The Pragmatic Programmer    52.00  -10%  ->     46.80
Best deal: Clean Code at 36.00
```

**Hint:** the `100` in your percentage maths must be `100.0`. `20 / 100` is `0` in Java, which would give every item a 0% discount and leave the prices suspiciously unchanged. There is also a stretch goal at the bottom of the file — adding a third, completely different `Discountable` class — which is worth doing to feel how little else has to change.

---

**Where this goes next:** every large Java program you meet is built from these pieces. Collections are interfaces (`List`, `Map`) with several implementations you swap freely. Frameworks hand you an interface and call your implementation. The loop from section 6 — one type, many objects, no `if` chain — is the shape of most good Java design.
