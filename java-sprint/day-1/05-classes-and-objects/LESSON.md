# Module 05 — Classes and Objects

**Estimated time:** ~75 minutes

**What you will learn**

- The big idea: a class is a blueprint, an object is one thing built from it
- Fields — the data each object carries around with it
- What `new` actually does, step by step
- Constructors: the free one, the ones you write, and why writing one takes the free one away
- `this`, and the silent bug you get without it
- Instance methods versus `static` ones — the full explanation you were promised
- Encapsulation: `private` fields, getters, setters, and the real reason for all three
- Many objects from one class, each minding its own state
- References: a variable holds an arrow, not an object — and what happens when two arrows point at the same thing
- `null`, the `NullPointerException`, and how to read the message
- Why `println(myObject)` prints something that looks like line noise

Seven programs live in `examples/`. Open them beside this page and run them as you go:

```
java BankAccountDemo.java
```

---

## 1. The big idea

Everything you have written so far has been loose parts. A `String` here, an `int` there, a few methods that pass values between them. It works, and for small programs it works well.

But look at what happens when the program grows. Say you are tracking bank accounts:

```java
String owner1 = "Alice";
double balance1 = 100.0;

String owner2 = "Bob";
double balance2 = 0.0;
```

Two accounts, four variables. Ten accounts, twenty variables. And nothing in that code says `owner1` and `balance1` have anything to do with each other — that connection lives only in your head, and in the `1` you remembered to type.

Objects fix this. The idea is to bundle the data that belongs together, plus the operations that act on it, into one thing.

Two words, and the difference between them is the whole module:

- A **class** is the blueprint. It describes what every account has and what every account can do. You write it once.
- An **object** is one actual thing built from that blueprint. Alice's account is an object. Bob's is another. You can build as many as you like.

A useful comparison: `int` is a type, and `42` is one value of that type. In exactly the same way, `BankAccount` is a type you invented, and Alice's account is one value of it. The difference is that you get to decide what a `BankAccount` is made of.

An object built from a class is also called an **instance** of that class. "Instance" and "object" mean the same thing; you will see both.

> **If you know Python**
>
> You have met `class` already, and the idea is identical. What is new in Java is that everything gets declared up front: the fields are listed at the top of the class with their types, rather than appearing whenever `__init__` happens to assign them. The payoff is that the compiler can catch a typo in a field name; the cost is more typing.

---

## 2. Fields: what each object carries

Here is a complete class:

```java
class Dog {
    String name;
    int ageYears;
    boolean goodBoy;
}
```

That is it — no `main`, nothing else required. Those three variables are **fields**, also called **instance variables**. They are declared inside the class but outside every method, and that position is what makes them fields rather than local variables.

The important part: *every object gets its own full set*. Build two dogs and there are two `name` fields, two `ageYears` fields, holding whatever each dog holds.

To use the class, build one with `new` and reach into it with a dot:

```java
Dog rex = new Dog();
rex.name = "Rex";
rex.ageYears = 4;

System.out.println(rex.name);      // Rex
```

The dot means "reach into this object and get the thing called...". You already use it constantly — `name.toUpperCase()`, `System.out.println` — and it has always meant this.

**Fields get default values.** This is a real difference from the local variables you have been writing. A local variable must be given a value before you read it or the compiler refuses. A field does not: Java fills it in automatically.

| Field type                        | Default |
| --------------------------------- | ------- |
| `int`, `long`, `short`, `byte`    | `0`     |
| `double`, `float`                 | `0.0`   |
| `boolean`                         | `false` |
| `char`                            | a blank |
| `String` and every other object   | `null`  |

That last row causes more crashes than anything else in Java, and section 10 is about it.

**Why fields exist:** they are the object's memory. A method's local variables vanish the moment the method returns, but a field lasts as long as the object does. That is what lets a bank account still know its balance ten minutes and forty method calls later.

Run `examples/FirstObject.java` to see all of this at once:

```
A brand-new Dog, before we touch it:
  name     = null
  ageYears = 0
  goodBoy  = false

After filling it in:
  Rex, aged 4, good boy
```

---

## 3. What `new` actually does

`new Dog()` looks like a method call and it is nearly one. Three things happen, in order:

1. Java sets aside a chunk of memory big enough for one `Dog`.
2. It fills every field with the default from the table above.
3. It runs the **constructor** (section 4), which is your chance to fill in real values.

Then it hands back a **reference** to the finished object — think of it as an arrow pointing at the thing in memory. That is what gets stored in your variable:

```java
Dog rex = new Dog();
```

Read that right to left. Build a `Dog`; get an arrow pointing at it; store the arrow in a variable called `rex` that is allowed to hold arrows-to-`Dog`s.

The distinction between the arrow and the object it points at seems fussy right now. In section 9 it becomes the difference between code that works and code that mystifies you, so it is worth planting the idea early.

Every `new` builds a brand-new object. Ten `new Dog()` calls give you ten separate dogs, no matter how identical the values you put in them.

---

## 4. Constructors

A **constructor** is a block of setup code that runs once, automatically, when an object is built. Its job is to leave the new object in a state that makes sense.

It looks like a method with two peculiarities: **its name is exactly the class name**, and **it has no return type** — not `void`, not anything.

```java
class Book {
    String title;
    String author;
    int year;

    Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
```

Now building a book fills it in on the spot:

```java
Book dune = new Book("Dune", "Frank Herbert", 1965);
```

Compare that to the three-lines-of-dots version from section 2. Besides being shorter, it is safer: there is no window in which the object exists half-filled, because the constructor runs before anybody else gets to see it.

### The free constructor

If you write no constructor at all, Java quietly provides one that takes no arguments and does nothing beyond the defaults. That is why `new Dog()` worked in section 2 even though `Dog` had no constructor. It is called the **default constructor**.

### Writing one takes the free one away

The moment you write any constructor of your own, the free one disappears. So with the `Book` above, this fails:

```java
Book b = new Book();
```

```
error: constructor Book in class Book cannot be applied to given types;
        Book b = new Book();
                 ^
  required: String,String,int
  found:    no arguments
  reason: actual and formal argument lists differ in length
```

Read the two middle lines as a pair — `required` is what the constructor wants, `found` is what you handed it. That pairing shows up in every argument-mismatch error in Java, so it is worth getting comfortable with now.

The fix is either to pass the arguments, or to write the no-argument constructor yourself.

### Several constructors

A class can have as many constructors as you like, as long as their parameter lists differ. This is **overloading**, the same rule you met for methods in module 04, and it lets callers build an object from whatever they happen to know.

```java
Book(String title, String author) {
    this(title, author, 0);
}
```

`this(...)` means "call another constructor of this same class first". It saves repeating the assignments, and it must be the **first statement** in the constructor.

**Why constructors exist:** they make it impossible to receive a half-built object. Anything the class needs in order to make sense is demanded up front, so no other method has to defend against a missing title.

Run `examples/ConstructorTour.java`:

```
=== Three ways to build a Book ===
  full    -> Dune by Frank Herbert (1965)
  no year -> Piranesi by Susanna Clarke (0)
  blank   -> Untitled by Unknown (0)
```

---

## 5. `this`

Look again at the line inside the constructor:

```java
this.title = title;
```

There are two things called `title` in scope: the field, and the parameter. The parameter is nearer, so a bare `title` means the parameter. **`this`** is a reference to the object currently being worked on, so `this.title` unambiguously means the field.

Naming the parameter the same as the field is normal, deliberate Java style. The parameter is the value coming in, the field is where it lands, and `this.` marks the boundary.

Now the important part. Get it wrong and there is **no error at all**:

```java
class Cat {
    String name;
    Cat(String name) {
        name = name;        // assigns the parameter to itself
    }
}
```

That compiles. It just does nothing — the field is never touched and stays `null`. The program fails later, somewhere else entirely:

```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke
"String.toUpperCase()" because "<local1>.name" is null
```

A missing `this.` is one of the classic beginner bugs precisely because the compiler cannot help you. It is also bug number one in the `FindTheNpe.java` exercise.

`this` has other uses — passing the current object to a method, or `this(...)` in a constructor — but disambiguating a field from a parameter is what you will use it for ninety percent of the time.

> **If you know Python**
>
> `this` is Java's `self`, with one difference: it is not a parameter. Python makes you write `def deposit(self, amount)`; Java hands `this` to every instance method invisibly. And because Java has real declarations, you only need `this.` when a local name is in the way — Python's `self.balance` is always required, while Java's plain `balance` usually works.

---

## 6. Instance methods versus `static`

Since module 01 you have written `static` on everything, on the promise that it would be explained. Here it is.

Classes hold methods as well as fields, and there are two kinds.

An **instance method** has no `static` on it. It belongs to an individual object, it is called with a dot on that object, and inside it the field names refer to *that object's* fields:

```java
class BankAccount {
    private double balance;

    void deposit(double amount) {
        balance = balance + amount;    // this object's balance
    }
}
```

```java
alice.deposit(50.0);      // adds to Alice's balance only
```

There is no `this.` in front of `balance` and none is needed — nothing else called `balance` is in scope, so Java knows you mean the field. `this.balance` would mean the same; write it when you want the emphasis.

A **static method** has `static` on it. It belongs to the class as a whole. There is no object involved, so it is called on the class name:

```java
Ticket.getIssuedCount()
Math.max(3, 7)
Integer.parseInt("42")
```

You have been calling static methods from other people's classes all along.

**Static fields** work the same way: one single copy shared by the entire class, rather than one per object.

```java
class Ticket {
    private static int issuedCount = 0;    // one, shared
    private String holder;                 // one per ticket
```

### How to choose

Ask one question: **does this method need to know which object it is working on?**

- Yes, it reads or changes fields of one particular thing → **instance method**. `deposit`, `withdraw`, `isFinished`.
- No, it works entirely from its arguments → **static**. `Math.max`, `parseInt`, and every method you wrote in module 04.

### Why `main` is static

`main` is where the program starts, so it runs before any object exists. There is nothing for it to be an instance method *of*. That is the whole reason.

And it explains the error that has been chasing you since module 04:

```java
public class DogRunner {
    void bark() { ... }

    public static void main(String[] args) {
        bark();
    }
}
```

```
error: non-static method bark() cannot be referenced from a static context
```

`main` is static, so it has no object in hand. `bark()` needs one. The compiler is asking "bark on *what*?" — and the answer is to build something first:

```java
DogRunner dog = new DogRunner();
dog.bark();
```

The same reasoning produces the variable version of the message:

```
error: non-static variable name cannot be referenced from a static context
```

and even this one, if you try to use `this` inside `main`:

```
error: non-static variable this cannot be referenced from a static context
```

All three are the same complaint. In a static context there is no current object, so anything that needs one is unavailable.

Run `examples/InstanceVsStatic.java`:

```
Tickets issued so far: 0
Tickets issued so far: 3

=== Instance methods run on one object ===
  #1 Ada (unused)
  #2 Grace (unused)
  #3 Alan (unused)
```

---

## 7. Encapsulation

Here is `BankAccount` written carelessly:

```java
class BankAccount {
    String owner;
    double balance;
}
```

And here is what anyone can now do to it:

```java
alice.balance = -99999.0;
```

No error. No complaint. The account is now impossible, and the bug is not in `BankAccount` — it is in whichever of the fifty files did that. Good luck.

The fix is to stop letting the outside world touch the data directly.

### `private` and `public`

These are **access modifiers**, words that control who may use a member of a class.

- **`private`** — only code inside this same class may touch it.
- **`public`** — anybody may.

The standard shape of a well-behaved class is: **fields private, methods public**.

One thing you will notice in this module's snippets and example files: the methods have no `public` in front of them. That is because every class here shares a single file, and a member written with no modifier at all is already visible to everything alongside it. Once your classes live in separate files — which is every real project — you write `public` on each method you want callers to have.

```java
class BankAccount {
    private String owner;
    private double balance;

    boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance = balance + amount;
        return true;
    }

    double getBalance() {
        return balance;
    }
}
```

Now `alice.balance = -99999.0;` does not compile:

```
error: balance has private access in BankAccount
        alice.balance = -99999.0;
             ^
```

The only way in is `deposit`, and `deposit` checks first. Bundling the data with the methods that guard it, and closing the direct route, is called **encapsulation**.

### Getters and setters

Since the fields are private, a class offers small public methods to reach them:

- A **getter** hands a value out: `getBalance()`, `getOwner()`.
- A **setter** takes a new value in: `setOwner(...)`.

At first glance a getter and setter pair looks like a pointless ritual around a public field. The difference is that a method gets to have an opinion:

```java
boolean setOwner(String newOwner) {
    if (newOwner == null || newOwner.isEmpty()) {
        return false;                 // refuse, and say so
    }
    owner = newOwner;
    return true;
}
```

(`isEmpty()` is a `String` method that is true when the text has no characters at all.)

Note the order of those two checks. `newOwner == null` comes first on purpose: `||` stops as soon as the left side is true, so when `newOwner` is null the `isEmpty()` call never runs. Swap them and you crash on exactly the input you were trying to reject.

Returning `boolean` lets the caller find out what happened. Some classes throw an exception instead — that is module 08 — but "return false and change nothing" is honest and needs no new syntax.

### The real reason: invariants

An **invariant** is something a class promises is always true of its objects, no matter what anyone does to them. `BankAccount` promises **the balance is never negative**.

An invariant is only worth anything if it cannot be broken. That means:

1. The constructor establishes it — a new account never starts out negative.
2. Every method that changes the balance checks before changing it.
3. The field is private, so there is no third route.

Miss any one of the three and the promise is just a comment. Notice what this buys you: to convince yourself the balance is never negative you read *one* class, not the whole program.

Look at `examples/BankAccountDemo.java` and you will see something missing — there is no `setBalance`. That is deliberate. The balance changes only through `deposit`, `withdraw` and `transferTo`, all of which validate. **Not writing a setter is a legitimate design decision**, and a common one. Getters and setters are not a rule that every field needs both; they are two tools you choose from.

```
=== The guards do their job ===
alice.deposit(-25.0) accepted? false
alice.withdraw(1000.0) accepted? false
Alice's balance is untouched by both attempts:
  Alice: 150.0
```

> **If you know Python**
>
> Python leans on convention: a leading underscore means "please don't", and nothing stops you. Java's `private` is enforced by the compiler. Python's `@property` is also the closest thing to a getter — but where Python lets you start with a plain attribute and add a property later without changing callers, Java asks you to decide up front. Which is why Java code has so many getters: they are the option that leaves the door open.

---

## 8. Many objects, one class

This is the payoff, and it is worth stating plainly because it is easy to skim past.

```java
BankAccount alice = new BankAccount("Alice", 100.0);
BankAccount bob = new BankAccount("Bob", 0.0);

alice.deposit(50.0);
```

Alice's balance is now 150.0. Bob's is still 0.0. There are two objects, each with its own `balance` field, and `alice.deposit(50.0)` could not touch Bob's if it tried — inside that call, `balance` means Alice's balance and nothing else.

One blueprint, any number of independent things. Writing `BankAccount` once gives you a hundred accounts for free, which is the same reason you wrote a method once in module 04 and called it a hundred times.

---

## 9. References: variables hold arrows

Time to take seriously the thing section 3 raised.

For an `int`, the variable holds the value:

```java
int a = 5;
int b = a;      // b gets its own copy of 5
b = 99;
// a is still 5
```

For an object, the variable holds a **reference** — an arrow pointing at an object that lives somewhere else. Copying the variable copies the arrow, **not** the object:

```java
Player p1 = new Player("Ada");
Player p2 = p1;          // copies the arrow

p1.addPoints(10);
p2.addPoints(5);

System.out.println(p1.getScore());    // 15
System.out.println(p2.getScore());    // 15
```

Fifteen from both, because there is only one `Player`. `p1` and `p2` are two names for it. When two references point at one object like this they are called **aliases**.

The rule that decides everything: **`new` is the only thing that creates an object.** No `new`, no new object. One `new` above, so one `Player`, however many variables end up pointing at it.

### `==` on objects

`==` compares the arrows, not what they point at. It asks "are these the very same object?" — never "do these hold equal values":

```java
Player a = new Player("Grace");
Player b = new Player("Grace");     // second new = second object

a == b        // false
```

Two objects, identical contents, different arrows. This is exactly the `String` trap from modules 02 and 03, and now you know why it exists: a `String` is an object like any other. To compare contents you compare the pieces yourself, or use `.equals(...)` — and module 07 shows how to teach your own class what `equals` should mean for it.

### Passing objects to methods

Module 04 told you Java is **pass by value**: a method gets a copy of what you hand it. That is still completely true. The subtlety is *what* gets copied.

When you pass an object, the copied value is the arrow. The copy points at the same object, so changes made through it are real:

```java
static void bonus(Player player) {
    player.addPoints(100);       // reaches the caller's Player
}
```

But pointing the copied arrow somewhere else changes only the copy:

```java
static void replace(Player player) {
    player = new Player("Someone else");    // caller sees nothing
}
```

The caller's variable still points where it always did. Both rules follow from one sentence — the arrow was copied, the object was not.

Run `examples/ReferencesAndAliasing.java`:

```
=== One object, two names ===
  p1 -> Ada 15
  p2 -> Ada 15
  p1 == p2 ? true   (same object)

=== Passing an object to a method ===
  before        -> Carol 0
  after bonus   -> Carol 100
  after replace -> Carol 100
```

> **If you know Python**
>
> Python works this way too — `b = a` on a list gives you two names for one list, and `==` versus `is` maps onto Java's `.equals` versus `==`. If you have ever been surprised by a function mutating the list you passed it, you already understand section 9 and just need the Java spelling.

---

## 10. `null` and the `NullPointerException`

**`null`** is the value a reference has when it points at nothing. Not an empty object, not a zero — no object at all.

You get one whenever an object field is left unset, and you can write it yourself:

```java
Contact nobody = null;
```

Holding a null is fine. Printing one is fine — you get the word `null`. Testing one is fine and useful:

```java
if (nobody == null) { ... }
```

What is not fine is **following** it. Calling a method or reading a field through a null reference is the single most common crash in Java:

```java
Contact ada = new Contact("Ada");     // constructor never sets email
System.out.println(ada.getEmail().toUpperCase());
```

```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke
"String.toUpperCase()" because the return value of "Contact.getEmail()" is null
	at CrashDemo.main(CrashDemo.java:5)
```

### Reading the message

Modern Java tells you a lot here. Take it in three pieces:

1. **`Cannot invoke "String.toUpperCase()"`** — what you were trying to do.
2. **`because the return value of "Contact.getEmail()" is null`** — *which thing* was null. This is the half that solves the bug, and it is precise: not "something on this line", but that specific value.
3. **`at CrashDemo.main(CrashDemo.java:5)`** — file and line where it blew up.

The wording of the middle part changes to fit the culprit. You will see all of these:

```
because "<local1>" is null                              a local variable
because "<parameter1>" is null                          a parameter
because "this.artist" is null                           a field of this object
because "<local1>.name" is null                         a field of some object
because the return value of "Song.getArtist()" is null  a method handed back null
```

`<local1>` and `<parameter1>` are placeholders Java uses when the real names were not kept in the compiled file. The position still narrows it down, and the line number does the rest.

**The most important habit:** the line that crashed is where the problem *surfaced*. The bug is usually earlier — wherever that value was supposed to be given something real. In the example above, line 5 is fine; the mistake is in a constructor that forgot to set `email`.

### Two ways to deal with it

**Check before you follow**, when null is a legitimate possibility:

```java
if (contact == null || contact.getEmail() == null) {
    System.out.println("no email on file");
    return;
}
```

**Or better, do not allow it.** Most nulls are avoidable. Have the constructor insist on a real value, or substitute a harmless one:

```java
Contact(String name, String email) {
    this.name = name;
    if (email == null) {
        this.email = "none@example.com";
    } else {
        this.email = email;
    }
}
```

Then no caller ever has to check. Prevention scales; checking everywhere does not.

One last distinction that catches people:

```java
String empty = "";        // a real String, with zero characters
String missing = null;    // no String at all
```

`empty.length()` is `0`. `missing.length()` crashes. They are not the same thing.

Run `examples/NullAndNpe.java` — it triggers three of these on purpose and prints each message for you to read.

> **If you know Python**
>
> `null` is `None`, and the crash you know as `AttributeError: 'NoneType' object has no attribute 'upper'` is the `NullPointerException`. Java's message names the culprit more precisely, which is a genuine improvement once you get used to the shape of it.

---

## 11. Printing an object

Try printing one of your own objects and the result is a let-down:

```java
Sticker star = new Sticker("star", "gold");
System.out.println(star);
```

```
Sticker@2a139a55
```

That is not a bug. It is two pieces joined by `@`: the class name, and the object's **hash code** in hexadecimal — roughly an id number for the object.

Those digits are arbitrary. They are not an address, and they have nothing to do with the fields you put in. They can differ between machines and between runs, so never write code that depends on them — though for a small single-threaded program like this one, you will often see the same value each time you run it.

The reason is simple. Java has no idea which of your fields you would want to see, or in what order, so it falls back on something that is true for every object ever made. Concatenating gives the same result, because `"" + star` asks the object to describe itself in exactly the same way.

For now, print the fields you want:

```java
System.out.println(star.getShape() + ", " + star.getColour());
```

That gets tedious quickly, and there is a proper fix: a class can be taught to describe itself by writing a method called `toString`. **Module 07 does that**, along with `equals`. Until then, `ClassName@hexdigits` means "this object has not been taught to introduce itself yet" — not that anything is wrong.

Run `examples/ToStringPreview.java`, which takes the string apart and shows where each half comes from.

---

## Common beginner mistakes

**1. Forgetting `new`**

```java
Dog d = Dog();
```

```
error: cannot find symbol
        Dog d = Dog();
                ^
  symbol:   method Dog()
  location: class DogDemo
```

Java looks for a *method* called `Dog` and finds none. Without `new` there is no object.

*Fix:* `Dog d = new Dog();`

---

**2. Forgetting `this.` in a constructor**

```java
Cat(String name) {
    name = name;
}
```

No error. No warning. The field stays `null` and you find out much later, in a `NullPointerException` somewhere else entirely.

*Fix:* `this.name = name;` — and when a constructor takes a parameter, check that every single one lands in a field.

---

**3. Giving a constructor a return type**

```java
class Bk {
    String title;
    void Bk(String title) { this.title = title; }
}
```

```
error: constructor Bk in class Bk cannot be applied to given types;
        Bk b = new Bk("Dune");
               ^
  required: no arguments
  found:    String
```

That `void` turned the constructor into an ordinary method that merely happens to be named `Bk`. The class is left with only the free no-argument constructor, so passing `"Dune"` fails. The error points at the `new` and never mentions the `void`, which makes this one genuinely nasty.

*Fix:* delete the `void`. Constructors have no return type.

---

**4. Expecting `new Book()` to still work**

```java
Book b = new Book();
```

```
error: constructor Book in class Book cannot be applied to given types;
  required: String,String,int
  found:    no arguments
```

Writing your own constructor removed the free one.

*Fix:* pass the arguments, or write a no-argument constructor yourself.

---

**5. Calling an instance method from `main` with no object**

```java
public static void main(String[] args) {
    bark();
}
```

```
error: non-static method bark() cannot be referenced from a static context
```

*Fix:* build an object and call it on that — `new Dog().bark()` — or make the method `static` if it never needed an object.

---

**6. Reading a field from a static method**

```java
private String name = "x";

public static void main(String[] args) {
    System.out.println(name);
}
```

```
error: non-static variable name cannot be referenced from a static context
```

Which object's `name`? There isn't one. Same complaint as mistake 5, different member.

*Fix:* go through an object, or make the field `static` if there really should be only one.

---

**7. Touching a private field from outside**

```java
alice.balance = 500.0;
```

```
error: balance has private access in BankAccount
        alice.balance = 500.0;
             ^
```

*Fix:* use the method the class provides — `alice.deposit(500.0)`. If no method does what you need, add one; do not make the field public to get around the guard you deliberately built.

---

**8. Calling an instance method on the class name**

```java
BankAccount.deposit(5.0);
```

```
error: non-static method deposit(double) cannot be referenced from a static context
```

`deposit` needs to know *which* account.

*Fix:* `myAccount.deposit(5.0);`

---

**9. Two public classes in one file**

```java
public class Main { ... }
public class Extra { }
```

```
error: class Extra is public, should be declared in a file named Extra.java
```

*Fix:* only one class per file may be `public`, and its name must match the filename. Extra helper classes in the same file are written without `public` — that is exactly what the examples in this module do.

---

**10. Comparing objects with `==`**

```java
if (a == b) { ... }      // two Players, same name, different objects
```

No error, and it is `false` even when the contents match.

*Fix:* compare the pieces you care about, or use `.equals(...)`. Module 07 covers writing your own.

---

**11. Expecting a copy when you assigned a reference**

```java
Player p2 = p1;
p2.addPoints(5);         // p1's score changes too
```

*Fix:* if you wanted a separate object, you need a second `new`. Assignment copies the arrow, never the object.

---

**12. Assuming a field is set because a parameter had a value**

```java
Playlist(String name, Song featured) {
    this.name = name;
    // featured quietly ignored
}
```

Compiles, runs, and hands you a `NullPointerException` the first time anyone calls `getFeatured().getTitle()`.

*Fix:* every constructor parameter should end up somewhere. If one does not, either assign it or stop accepting it.

---

## Check yourself

1. What is the difference between a class and an object? How many objects can one class produce?
2. Why does `this.balance = balance;` need the `this.`, and what exactly goes wrong if you leave it off?
3. You write `Book(String title, String author, int year)` and then `new Book()` stops compiling. Why — and what are your two options?
4. A method needs no fields at all and works purely from its arguments. Instance or static? What about a method that reads the object's `balance`?
5. `p2 = p1;` then `p2.addPoints(5);`, and `p1.getScore()` changed too. Explain what happened, and how you would get two independent players instead.
6. You see `Cannot invoke "String.toUpperCase()" because the return value of "Contact.getEmail()" is null` at line 5. What was null, and why is line 5 probably not where the bug is?

<details>
<summary>Answers</summary>

1. A class is the blueprint — it describes what every object of that type has and can do, and you write it once. An object is one concrete thing built from that blueprint with `new`, carrying its own copy of every field. One class can produce as many objects as you like, and they are fully independent of each other.

2. Inside the constructor there are two things called `balance`: the field and the parameter. The nearer one wins, so a bare `balance` means the parameter. `this.` says "the field of the object being built". Leave it off and you write `balance = balance;`, which assigns the parameter to itself and never touches the field. There is **no compiler error** — the field keeps its default of `0` or `null`, and you find out later via a wrong value or a `NullPointerException` in unrelated-looking code.

3. Java only supplies the free no-argument constructor when you write none of your own. The moment you write one, it goes away. Either pass the arguments — `new Book("Dune", "Frank Herbert", 1965)` — or write the no-argument constructor yourself, which can chain to the other with `this("Untitled", "Unknown", 0);`.

4. Works purely from its arguments → `static`. It belongs to the class and is called on the class name, like `Math.max`. Reads the object's `balance` → instance method, because it needs to know *which* account it is talking about. The test is always the same question: does this method need to know which object it is working on?

5. Assigning one object variable to another copies the **reference**, not the object. `p1` and `p2` became two names — aliases — for one single `Player`, so a change through either is visible through both. Only `new` creates an object, so for two independent players you need a second `new Player(...)`.

6. `getEmail()` handed back null — so the `email` field of that `Contact` was null. Line 5 is only where the program *followed* the null and crashed; the code there may be perfectly correct. The bug is wherever `email` should have been given a real value and wasn't, most likely a constructor that never assigned it (or assigned it without `this.`).

</details>

---

## Exercises

The files are in `exercises/`. Every one **compiles as given**, so you start from working code: run it first, confirm it is green, then fill in the `TODO`s. Each file states its expected output at the top, and your output should match it character for character.

Run any of them with `java FileName.java` from inside the `exercises/` folder.

Solutions are in `solutions/`, one per exercise with the same filename. Have a genuine attempt first — including getting it wrong and reading the errors. A `NullPointerException` you tracked down yourself is worth more than ten you read about.

### 1. `Student.java`

**Goal:** build a class that protects its own data. A `Student` has a name and a grade, and the class must guarantee that the grade is always between 0 and 100 and the name is never empty. You write the fields, the constructor, two getters, two validating setters and `isPassing()`. The test `main` is already written and must not be changed.

**Done looks like:**
```
=== Two students ===
  Ada (grade 91) passing? true
  Grace (grade 55) passing? false

=== The grade setter refuses bad values ===
  ada.setGrade(105) accepted? false
  ada.setGrade(-1)  accepted? false
  Ada's grade is still 91
  ...
=== Two students, two separate states ===
  x -> 95, y -> 70
```

**Hint:** have the constructor call your own `setName` and `setGrade` rather than assigning the fields directly, and check what they return. The validation then lives in exactly one place, and no `Student` can be born in a state the setters would have rejected. In `setName`, test `newName == null` **before** `newName.isEmpty()` — `||` stops at the first true test, so that order is what keeps a null from crashing the check meant to catch it.

### 2. `CountdownTimer.java`

**Goal:** a class that owns a number and counts it down without ever going below zero. You will also write your first static field — a counter of how many timers have ever been created, shared by the class rather than stored per object.

**Done looks like:**
```
Timers created so far: 0
Timers created so far: 2

=== Counting Tea down ===
  Tea: 3
  Tea: 2
  Tea: 1
  Tea: 0 - finished!

=== A finished timer refuses to go negative ===
  tea.tick() accepted? false
  tea.getSecondsLeft() = 0
```

**Hint:** you need **two** number fields, not one. `secondsLeft` changes as the timer runs, and `startSeconds` remembers where it began so `reset()` has somewhere to go back to — a good example of a field earning its place by being the object's memory. Also note that `isFinished()` starts as `return true` in the starter on purpose: `main` counts down with `while (!tea.isFinished())`, so a placeholder of `false` would loop forever.

### 3. `FindTheNpe.java`

**Goal:** no TODOs in this one. The file compiles perfectly and then crashes. Three separate bugs, each producing a `NullPointerException`, each a different way for a null to appear. Run it, read the crash, fix one bug, run again — and repeat until the expected output appears.

**Done looks like:**
```
=== Song ===
  title  : Blue Monday
  artist : NEW ORDER

=== Playlist ===
  name     : Party Mix
  featured : Blue Monday

=== Ratings ===
  5 stars: GREAT
  3 stars: OK
  1 star : POOR
```

**Hint:** none of the three fixes belong in `main` — each one is in a class further down, at the spot where a field or a return value should have been set and was not. Let the `because ... is null` half of each message point you at the culprit, then ask where *that* value was supposed to come from. The three causes are: a missing `this.`, a constructor parameter that never got stored, and a method with a path that returns `null`.

---

You can now design your own types, not just use the ones Java gave you — which is the point where Java stops feeling like a list of instructions and starts feeling like a language you build things in. Module 06 puts your classes into family trees with inheritance and interfaces, and module 07 teaches your classes to describe and compare themselves properly, finally fixing that `Sticker@2a139a55`.
