# Module 09 — Capstone: Contact Book

**Estimated time:** ~2.5–3 hours

**What you will learn**

- How to build a real program in **milestones** — small steps that each run and each prove something
- Just enough `ArrayList` to hold a collection that grows: six operations and no more
- How a program made of **several classes** is put together, and how to decide what belongs where
- How everything from modules 01–08 clicks into one app: a menu loop, a validating constructor, `toString`, `equals`, and `try`/`catch`
- How to turn an exception into one friendly line instead of a crash that kills your program
- Which problems day 1 has left you unable to solve — and what day 2 hands you

There is almost nothing new here. One new tool (`ArrayList`), and the rest is assembly. That is the point of a capstone: the skills stop being eight separate topics and start being one thing you can do.

Be prepared for this to feel harder than the modules did, even though each individual piece is easier. Holding a whole program in your head is a separate skill from writing a method, and it is the one this module is really training.

---

## 1. What you are building

A console contact book. It asks what you want, does it, and asks again until you tell it to stop.

Here is the finished thing, mid-session:

```
=== Contact Book ===
  1) Add a contact
  2) List all contacts
  3) Search by name
  4) Delete by name
  5) Quit
Choose: 1
Name:  Ada Lovelace
Email: ada@calc.org
Phone: 555-0101
  Added Ada Lovelace.

=== Contact Book ===
  1) Add a contact
  2) List all contacts
  3) Search by name
  4) Delete by name
  5) Quit
Choose: 1
Name:  Alan Turing
Email: alan.bletchley.uk
Phone: 555-0103
  Not added: "alan.bletchley.uk" does not look like an email address

=== Contact Book ===
  ...
Choose: 3
Search for: ada
  1 contact matching "ada":
    1. Ada Lovelace <ada@calc.org> 555-0101
```

Notice the third block. A bad email did **not** crash the program. It printed one line and came straight back to the menu. Making that happen is a real chunk of the work, and it is the difference between a demo and a program.

Everything lives in one file, `exercises/ContactBookApp.java`, and it already compiles. Run it any time with:

```
cd exercises
java ContactBookApp.java
```

---

## 2. Three classes, three jobs

The file holds three classes. Not because three is a magic number, but because there are three genuinely different jobs here:

| Class | Its one job | Knows about |
|---|---|---|
| `Contact` | one person's data, and the rules for what counts as valid | names, emails, phone numbers |
| `ContactBook` | the collection, and the operations on it | that contacts live in an `ArrayList` |
| `ContactBookApp` | the conversation with the human | the `Scanner`, the menu, the wording of every message |

The value is in what each class **does not** know.

`Contact` has never heard of a keyboard. It cannot print a friendly error, because printing is not its job — it throws, and somebody upstairs decides what the human sees. `ContactBook` has never heard of a menu; it just holds contacts. And `ContactBookApp` has no idea that the contacts sit in an `ArrayList` — swap that for a file or a database later and `ContactBookApp` does not change by a single character.

That last sentence is most of what people mean by "good design", and you get it here almost for free just by putting things in the right class.

There is one practical wrinkle. `java ContactBookApp.java` compiles **only that file** — Java 21 will not go looking for `Contact.java` next door. So all three classes live in one file, and exactly one of them is `public` (the one whose name matches the file). The other two have no `public` in front of `class`. That is a rule of the language, not a style choice.

> **If you know Python**
> Python would let you put all three in one module and import freely. Java's one-public-class-per-file rule is stricter, and in a real project each class would get its own file compiled together with `javac *.java`. We are staying in one file so `java ContactBookApp.java` keeps working with no build setup at all.

---

## 3. Just enough `ArrayList`

You need somewhere to keep the contacts, and you do not know how many there will be. Someone might add three, or thirty.

An **`ArrayList`** is a list that grows as you add to it. You never state a size. You add, and it makes room.

```java
import java.util.ArrayList;

ArrayList<String> guests = new ArrayList<>();
guests.add("Ada");
guests.add("Grace");
System.out.println(guests.size());   // 2
System.out.println(guests.get(0));   // Ada
```

**Why it exists:** because almost every program has a pile of things whose size it cannot know in advance, and managing that by hand is tedious and easy to get wrong.

### The angle brackets

`ArrayList<String>` means "a list of `String`". The type in the brackets is a promise, and the compiler holds you to it — put an `int` in a list of `String` and it refuses to compile, so you never pull something surprising back out.

On the right-hand side, `new ArrayList<>()` has empty brackets. Java can see what the left side said, so you do not repeat it. That is all you need to know today; the general machinery, called **generics**, is day 2.

### The six operations

That is the entire vocabulary the capstone needs:

| Operation | What it does |
|---|---|
| `list.add(x)` | puts `x` on the end; the list grows by one |
| `list.get(i)` | the item at position `i`, **counting from 0** |
| `list.size()` | how many items there are right now |
| `list.remove(i)` | takes out the item at position `i` **and hands it back** |
| `list.contains(x)` | walks the list calling `equals` on each item |
| `for (String s : list)` | visits every item in order, no counting involved |

Run the demo and watch each one:

```
cd examples
java ArrayListPreview.java
```

Three things in that output are worth pausing on.

**Positions start at 0.** Three items live at 0, 1 and 2, so `get(3)` fails:

```
  get(3) -> refused: Index 3 out of bounds for length 3
```

**`remove` shifts everything down.** Remove position 1 and whatever was at 2 is now at 1. The list closes the gap.

**A list does not care about duplicates.** Add "Ada" twice and you have two Adas. If duplicates matter in your program, *you* check first — which is exactly what milestone 6 is about.

### Lists of your own objects

`ArrayList<String>` is the warm-up. The moment the list holds a class you wrote, two methods from module 07 start doing real work:

```
cd examples
java ListOfObjects.java
```

- **`toString`** decides what the list looks like when you print it. Without it you get `[Pet@2a139a55, Pet@14ae5a5]`.
- **`equals`** decides what `contains` means. `Pet` compares names ignoring case, so a brand-new `Pet("rex", "dog")` makes `contains` say `true` even though it is a different object.

The last block of that program is worth your full attention — it is a trap you will meet for the rest of your career:

```
  case 1 - it quietly gives you the wrong answer:
    before -> [Rex the dog, Milo the cat, Nala the cat]
    after  -> [Rex the dog, Nala the cat]
    We asked for every cat to go. Nala is still sitting there.

  case 2 - same code, one more pet, and now it throws:
    threw ConcurrentModificationException (getMessage() is null)
```

Identical code. One list gives a wrong answer in silence; the other crashes. **Never change a list while a `for`-each is walking it.** Loop by index and return the moment you remove something — which is what milestone 5 will have you do.

> **If you know Python**
> `ArrayList<String>` is close to a Python `list` that only accepts strings. `add` is `append`, `size()` is `len()`, `get(i)` is `[i]`, `contains` is `in`. Python has the same removing-while-iterating trap, and Java's is louder about it — sometimes.

**Arrays** — Java's fixed-size, lower-level `String[]` — and the rest of the collections (`HashMap`, `HashSet`, `List` as an interface) are all day 2. You do not need any of them today. Six operations is genuinely enough to build the whole app.

---

## 4. How to work through this

Six milestones. Each one has a goal, a list of what to write, a **checkpoint** you must be able to reproduce before moving on, and a hint box for when you get stuck.

Three rules that will save you the most time:

**Run after every change.** Not after every milestone — after every few lines. A program that broke ten seconds ago has one suspect. A program that broke twenty minutes ago has forty.

**Do the milestones in order.** Milestone 4 assumes milestone 3 works. Skipping ahead means debugging two things at once, which is more than twice as hard.

**Type your own checkpoint input the first few times**, so you feel the program. After that, drive it from the command line and save yourself the typing:

```
printf '1\nAda Lovelace\nada@calc.org\n555-0101\n2\n5\n' | java ContactBookApp.java
```

Each `\n` is one press of Enter. That line adds a contact, lists the book, and quits — a whole session in one command, repeatable as often as you like. Just remember to end with a `5`, or the program runs out of input and throws (mistake #3 below).

---

## Milestone 1 — a menu that keeps asking

**Goal:** a loop that shows the menu, reads a choice, does something, and comes back — until the user picks 5.

**What to create:** the body of `main`. Everything else stays stubbed — a **stub** being a placeholder method that compiles and prints something harmless, so the program keeps running while the real version is still missing.

You need a `boolean` flag, a `while` loop, and a `switch`. The flag is the whole trick: the quit branch does not stop the program, it sets `running` to `false`, and the loop notices on its next check and ends naturally.

```java
boolean running = true;

while (running) {
    printMenu();
    System.out.print("Choose: ");
    String choice = in.nextLine().trim();

    switch (choice) {
        case "1" -> addContact(in, book);
        // ...
        case "5" -> {
            System.out.println("  Bye!");
            running = false;
        }
        default -> System.out.println("  I do not know the option \"" + choice + "\". Pick 1 to 5.");
    }
}
```

Two details that matter more than they look:

`System.out.print` — no `ln` — leaves the cursor on the same line, so what the user types appears right after `Choose: `. Use `println` and the prompt sits on its own line looking odd.

`switch` on a `String`, not an `int`, and every case is `"1"` in quotes. Reading everything as text with `nextLine` and never mixing in `nextInt` avoids the single most common `Scanner` bug in existence (mistake #1).

**Why a `default` branch exists:** users type things you did not plan for. Without `default`, typing `9` does nothing at all and the menu just reappears, which reads like a broken program.

### Checkpoint 1

```
=== Contact Book ===
  1) Add a contact
  2) List all contacts
  3) Search by name
  4) Delete by name
  5) Quit
Choose: 2
  (list is not built yet)

=== Contact Book ===
  1) Add a contact
  2) List all contacts
  3) Search by name
  4) Delete by name
  5) Quit
Choose: 9
  I do not know the option "9". Pick 1 to 5.

=== Contact Book ===
  ...
Choose: 5
  Bye!
```

The menu repeats, unknown input is refused politely, and 5 ends the program cleanly. That is the whole milestone. The "not built yet" lines are the stubs doing their job.

> **Hint**
> If the menu scrolls forever and never stops, your quit branch is missing `running = false;` — press Ctrl+C and look there. If the program prints one menu and exits instantly, you probably wrote `if` where you meant `while`. If typing `5` lands in `default`, check you are comparing `"5"` and not `5`.

---

## Milestone 2 — a `Contact` that refuses to be wrong

**Goal:** a class that cannot exist in an invalid state. No blank names, no strings that are obviously not emails.

**What to create:** in `class Contact` — the three fields, the constructor with its validation, `looksLikeEmail`, the three getters, and `toString`.

The fields are `private final`. `private` means nothing outside the class can touch them; `final` means once set in the constructor they never change. Together they make a `Contact` **immutable**, which is a good default for a small bundle of data — nothing can quietly alter a contact behind the book's back.

The constructor is the gate, and this is the important idea:

```java
Contact(String name, String email, String phone) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("a name is required");
    }
    // ...email and phone checks...

    this.name = name.trim();
    this.email = email.trim();
    this.phone = phone.trim();
}
```

**Check for `null` first.** (`isBlank()` is a `String` method that is true when the text is empty *or* contains nothing but spaces — a stricter cousin of the `isEmpty()` you met in module 05.) `name.isBlank()` on a `null` throws `NullPointerException` before your careful message ever runs. `||` stops as soon as the left side is true, so `name == null ||` protects everything after it. Swap the two and you have planted a crash.

**Throwing from a constructor means the object is never handed back.** There is no half-built `Contact` for anyone to misuse. The caller gets an exception instead of a reference. That is why validation belongs here and not in the menu code — put it in the menu and a second caller added next year skips it entirely.

**Trim once, at the door.** After the constructor, nothing downstream has to wonder whether a name has a stray space on the end.

For the email, keep it deliberately loose:

```java
private static boolean looksLikeEmail(String candidate) {
    int at = candidate.indexOf('@');
    if (at < 1) {
        return false;                                  // no @, or nothing before it
    }
    int dot = candidate.indexOf('.', at + 2);          // a dot, not right after the @
    return dot > 0 && dot < candidate.length() - 1;    // and not the last character
}
```

`indexOf` returns the position of what you asked for, or `-1` if it is not there. The two-argument version starts searching from a position. `at < 1` therefore covers both "no `@` at all" and "`@` was the very first character".

**Why so loose?** Because real email validation is a famously miserable problem, and every "complete" regex you find online is wrong in some interesting way. (A *regex*, or regular expression, is a pattern-matching mini-language for text. Java has one; it is day 2 material, and you do not need it here.) Programs that must be certain send a confirmation message instead of guessing. You are catching typos here, and that is a perfectly respectable goal — say so in the comment so the next reader knows it is deliberate.

`toString` is your module 07 payoff:

```java
@Override
public String toString() {
    return name + " <" + email + "> " + phone;
}
```

### Checkpoint 2

Nothing calls `Contact` yet, so test it directly. Put these two lines at the top of `main`, temporarily:

```java
System.out.println(new Contact("Ada Lovelace", "ada@calc.org", "555-0101"));
System.out.println(new Contact("   ", "ada@calc.org", "555-0101"));
```

```
Ada Lovelace <ada@calc.org> 555-0101
Exception in thread "main" java.lang.IllegalArgumentException: a name is required
	at Contact.<init>(ContactBookApp.java:191)
	at ContactBookApp.main(ContactBookApp.java:25)
```

Both lines are a pass. The first proves a good contact builds and describes itself. The second proves a bad one is refused — loudly, with your message, and the program stops rather than carrying on with rubbish. Your line numbers will differ; `<init>` is how a stack trace spells "constructor".

Try a few more before moving on: `"ada.calc.org"`, `"@calc.org"`, `"ada@calc"`, `"ada@calc."`. All four should be refused. Then **delete the two scratch lines** — milestone 3 replaces them properly.

> **Hint**
> `NullPointerException: Cannot invoke "String.isBlank()" because "<parameter1>" is null` means your `null` check is on the wrong side of the `||`.
> `variable email might not have been initialized` means a `final` field is not assigned on every path through the constructor — usually a missing `this.email = ...`.
> If a bad email sails through, print what `looksLikeEmail` returns for it and check `at` and `dot` by hand.

---

## Milestone 3 — add and list

**Goal:** contacts you create actually go somewhere, and you can see them.

**What to create:** in `ContactBook` — the field, `size`, `add`, `all`. In `ContactBookApp` — `addContact` and `listContacts`.

`ContactBook` is thin on purpose:

```java
class ContactBook {

    private final ArrayList<Contact> contacts = new ArrayList<>();

    int size() {
        return contacts.size();
    }

    boolean add(Contact contact) {
        contacts.add(contact);
        return true;      // milestone 6 gives this a reason to sometimes be false
    }
}
```

`private final` again, and the two words mean different things here. `final` says the field can never point at a *different* list. The list itself still grows and shrinks — that is the entire point of it. `private` is what stops `ContactBookApp` from reaching in and adding contacts without going through your rules.

`all()` returns a **copy**:

```java
ArrayList<Contact> all() {
    ArrayList<Contact> copy = new ArrayList<>();
    for (Contact contact : contacts) {
        copy.add(contact);
    }
    return copy;
}
```

**Why copy?** Return the real list and any caller can add or remove behind your back, and every rule this class enforces becomes a suggestion. Handing out a copy keeps `ContactBook` in charge of its own contents. (`new ArrayList<>(contacts)` does this in one line; you will meet it on day 2.)

Now `addContact`, where module 08 earns its keep:

```java
try {
    Contact contact = new Contact(name, email, phone);
    if (book.add(contact)) {
        System.out.println("  Added " + contact.getName() + ".");
    } else {
        System.out.println("  There is already a contact called " + contact.getName()
                + ". Nothing changed.");
    }
} catch (IllegalArgumentException e) {
    System.out.println("  Not added: " + e.getMessage());
}
```

This is the join between the two halves of the design. `Contact` throws, because refusing bad data is its job and it has no idea who is asking. `ContactBookApp` catches, because it is the only class that knows there is a person sitting there who would rather see one line than a stack trace.

**Catch `IllegalArgumentException`, not `Exception`.** You want to handle the failure you planned for. Catching `Exception` also swallows the typos and null bugs you did not plan for, and turns them into a shrug.

### Checkpoint 3

```
Choose: 1
Name:  Ada Lovelace
Email: ada@calc.org
Phone: 555-0101
  Added Ada Lovelace.

Choose: 1
Name:  Grace Hopper
Email: grace@navy.mil
Phone: 555-0102
  Added Grace Hopper.

Choose: 1
Name:  Alan Turing
Email: alan.bletchley.uk
Phone: 555-0103
  Not added: "alan.bletchley.uk" does not look like an email address

Choose: 2
  2 contacts:
    1. Ada Lovelace <ada@calc.org> 555-0101
    2. Grace Hopper <grace@navy.mil> 555-0102
```

The bad email produced one line and **the menu came back**. If your program printed a stack trace and died, your `try` block does not wrap the `new Contact(...)` call.

Check the empty case too — pick 2 on a fresh run:

```
Choose: 2
  The book is empty. Add someone with option 1.
```

> **Hint**
> `printNumbered` and `contactWord` are already written for you; hand `book.all()` to the first and `book.size()` to the second.
> If every contact prints as `Contact@2a139a55`, your `toString` is missing its `@Override`, or is spelled `ToString`.
> If contacts vanish between adds, you created a new `ContactBook` inside the loop instead of once before it.

---

## Milestone 4 — search

**Goal:** typing `ada` finds `Ada Lovelace`.

**What to create:** `ContactBook.search`, and `ContactBookApp.searchContacts`.

```java
ArrayList<Contact> search(String term) {
    String needle = term.trim().toLowerCase();
    ArrayList<Contact> matches = new ArrayList<>();
    for (Contact contact : contacts) {
        if (contact.getName().toLowerCase().contains(needle)) {
            matches.add(contact);
        }
    }
    return matches;
}
```

Three decisions in six lines, and each one is a choice you could have made differently:

**Lower-case both sides.** Then `ada`, `Ada` and `ADA` all work. Lower-casing only one side is a bug that looks like it works, because half your test data happens to match.

**`contains`, not `equals`.** Users search for fragments. `String.contains` asks whether one piece of text appears inside another — a different method from `ArrayList.contains`, same friendly name.

**Return a new list, never `null`.** An empty list is a perfectly good answer to "who matches zzz". Return `null` for "nothing found" and every caller has to remember a null check, and one of them will not, and you get a `NullPointerException` somewhere unrelated.

`searchContacts` reads the term, guards against blank, asks the book, and prints. **The guard is not optional:** every name contains the empty string, so a blank search would match everyone. Technically correct, completely useless.

### Checkpoint 4

With Ada and Grace in the book:

```
Choose: 3
Search for: ada
  1 contact matching "ada":
    1. Ada Lovelace <ada@calc.org> 555-0101

Choose: 3
Search for: zzz
  Nothing matches "zzz".

Choose: 3
Search for:
  Type something to search for.
```

Search for `o` as well — it should find both Lovelace and Hopper, and print `2 contacts matching "o":`. If it says `2 contact`, you skipped `contactWord`.

> **Hint**
> Finding nothing when the case differs means one side is not lower-cased. Print `needle` and `contact.getName().toLowerCase()` and compare them by eye.
> `NullPointerException` right after a search usually means `search` returned `null` on the no-match path instead of an empty list.

---

## Milestone 5 — delete

**Goal:** remove a contact by name, and say something sensible when there is no such person.

**What to create:** `ContactBook.delete`, and `ContactBookApp.deleteContact`.

```java
Contact delete(String name) {
    String wanted = name.trim();
    for (int i = 0; i < contacts.size(); i++) {
        if (contacts.get(i).getName().equalsIgnoreCase(wanted)) {
            return contacts.remove(i);
        }
    }
    return null;
}
```

**A counted loop, not a for-each.** `remove` needs a position, and a for-each never gives you one. This is also the shape that avoids the trap from `ListOfObjects.java`: you `return` the instant you remove something, so the loop never runs again over a list that just changed shape.

**`equalsIgnoreCase`, not `contains`.** Deleting is destructive. Searching for a fragment is helpful; *deleting* everything matching a fragment is how you lose data. Exact name, ignoring case only.

**It returns the `Contact` it removed, or `null`.** More useful than `true`/`false`: the caller can print the contact's stored name, so deleting `ada lovelace` reports `Deleted Ada Lovelace.` and confirms exactly who left. `remove(i)` hands back the item it removed, so it costs nothing to pass along.

`null` as "I found nobody" is a real Java idiom and you will meet it constantly. It also means the caller **must** check before using the result — `removed.getName()` on a `null` is the `NullPointerException` from module 05.

### Checkpoint 5

```
Choose: 4
Name to delete: grace hopper
  Deleted Grace Hopper.

Choose: 4
Name to delete: bob
  No contact called "bob". Nothing changed.

Choose: 2
  1 contact:
    1. Ada Lovelace <ada@calc.org> 555-0101
```

Lower-case input deleted the properly-capitalised contact, and the confirmation showed the **stored** spelling. The missing name changed nothing and said so.

> **Hint**
> `NullPointerException: Cannot invoke "Contact.getName()" because "removed" is null` means you printed the removed contact's name before checking for `null`.
> If nothing is ever found, check you are calling `equalsIgnoreCase` on the *name* and not on the whole `Contact`.
> If the wrong contact disappears, you removed by loop counter after already changing the list — re-read the counted loop above.

---

## Milestone 6 — polish

**Goal:** the same name cannot be added twice, and nothing the user types can crash the program.

**What to create:** `equals` and `hashCode` in `Contact`, and the duplicate guard in `ContactBook.add`.

Ask first: **when are two contacts the same person?** Not when all three fields match — people change phone numbers. For this app, the name is the identity:

```java
@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    Contact other = (Contact) o;
    return name.equalsIgnoreCase(other.name);
}

@Override
public int hashCode() {
    return Objects.hash(name.toLowerCase());
}
```

The four steps are exactly module 07's, and note that `hashCode` lower-cases for the same reason `equals` ignores case. **Equal objects must produce equal hash codes.** Compare case-insensitively and hash case-sensitively and you have built a class that contradicts itself — one that works fine today and breaks on day 2 the moment a `HashSet` gets involved.

Now the guard. You could write it by hand:

```java
for (Contact existing : contacts) {
    if (existing.equals(contact)) {
        return false;
    }
}
```

Or let the list do it:

```java
boolean add(Contact contact) {
    if (contacts.contains(contact)) {
        return false;
    }
    contacts.add(contact);
    return true;
}
```

**These are the same code.** `contains` walks the list calling `equals` on each item — that is its entire implementation. Which is why this one line only works because you wrote `equals` first. Delete `equals` and `contains` falls back to the inherited version, which asks "is this literally the same object in memory?", answers `false` every time, and duplicates start piling up in silence.

That is the whole module-07 lesson landing in a real program: a method you never call by name, that a library calls for you, that decides whether your app is correct.

### Checkpoint 6

```
Choose: 1
Name:  Ada Lovelace
Email: ada@calc.org
Phone: 555-0101
  Added Ada Lovelace.

Choose: 1
Name:  ada lovelace
Email: other@example.com
Phone: 555-9999
  There is already a contact called ada lovelace. Nothing changed.

Choose: 1
Name:
Email: x@y.com
Phone: 555-0104
  Not added: a name is required

Choose: 2
  1 contact:
    1. Ada Lovelace <ada@calc.org> 555-0101
```

Different capitalisation, different email, different phone — refused, because the name is what identity means here. The book still holds exactly one contact.

Now go and be hostile to your own program. Empty menu choice, spaces only, `qqq`, a name of one letter, an email of `@`, deleting from an empty book, searching an empty book. Nothing should print a stack trace. If something does, you have found the last bug, and finding it yourself is worth more than the milestone was.

> **Hint**
> Duplicates still getting through? Print `contacts.contains(contact)` right before the check. If it is always `false`, your `equals` is not being called — the usual cause is the signature `equals(Contact other)` instead of `equals(Object o)`, which is an overload, not an override. Add `@Override` and the compiler will tell you off properly.

---

## Common beginner mistakes

### 1. Mixing `nextInt()` and `nextLine()`

```java
int choice = in.nextInt();
System.out.print("Name: ");
String name = in.nextLine();
```

```
Choose: Name: choice=1 name=[]
```

The name came back empty and the program never even paused. `nextInt` reads the digits and **leaves the Enter key sitting in the input queue**; the next `nextLine` finds that leftover newline, calls it an empty line, and returns instantly. This is the same trap module 03 section 11 walked through.

**Fix:** read everything with `nextLine()` and convert when you need a number: `int n = Integer.parseInt(in.nextLine());`. The menu switches on text anyway, so this app never needs a number at all. One rule, zero surprises.

### 2. The loop that never ends

```java
case "5" -> System.out.println("  Bye!");   // forgot running = false
```

No error. The menu prints forever and Ctrl+C is your only way out.

**Fix:** the quit branch must set `running = false;`. When a `case` needs two statements, wrap them in `{ }`.

### 3. `NoSuchElementException: No line found`

```
Choose: Exception in thread "main" java.util.NoSuchElementException: No line found
	at java.base/java.util.Scanner.nextLine(Scanner.java:1660)
	at ContactBookApp.main(ContactBookApp.java:31)
```

Almost always from a `printf` pipeline that ran out of input: the program asked another question and there was nothing left to read.

**Fix:** end your piped input with `5\n` so the program quits on its own. It is not a bug in your code.

### 4. Calling a method on a `null` before checking for `null`

```java
if (name.isBlank() || name == null) {      // backwards
```

```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.isBlank()" because "<parameter1>" is null
	at NpeDemo.make(NpeDemo.java:6)
```

The null check never runs, because the crash happens while evaluating the condition in front of it.

**Fix:** `if (name == null || name.isBlank())`. `||` stops the moment the left side is true, so the null check protects everything to its right. Null first, always.

### 5. A `final` field left unassigned

```java
Contact(String name, String email, String phone) {
    this.name = name.trim();
    // forgot email
    this.phone = phone.trim();
}
```

```
error: variable email might not have been initialized
```

A `final` field must be assigned exactly once, on **every** path out of the constructor. A `return` in the middle of your validation can cause this too.

**Fix:** assign all three at the end, after the checks, as in milestone 2. That shape has only one path.

### 6. Removing from a list a `for`-each is walking

```java
for (Contact contact : contacts) {
    if (contact.getName().equalsIgnoreCase(name)) {
        contacts.remove(contact);
    }
}
```

Sometimes `ConcurrentModificationException` (whose `getMessage()` is `null`, unhelpfully). Sometimes an item silently skipped and no error at all — `ListOfObjects.java` shows both from the same code.

**Fix:** a counted loop that `return`s immediately after removing, as in milestone 5.

### 7. `equals` with the wrong parameter type

```java
public boolean equals(Contact other) {      // not an override
    return name.equalsIgnoreCase(other.name);
}
```

No error without `@Override` — and no override either. You have added a *second* method that happens to share a name. Your own direct calls pick it and look fine, while `contains` calls the inherited one, gets `false`, and duplicates pile up in silence.

**Fix:** `public boolean equals(Object o)`, then check the type inside. Write `@Override` on it and this mistake becomes a compile error instead of a mystery.

### 8. Validating in the menu instead of in `Contact`

```java
System.out.print("Name:  ");
String name = in.nextLine();
if (name.isBlank()) {
    System.out.println("  Not added: a name is required");
    return;
}
Contact contact = new Contact(name, email, phone);   // no checks inside
```

This works. It is still a mistake. The rule now lives in the user interface, so the *class* has no rules at all — anybody who constructs a `Contact` from anywhere else gets no protection, and the second place that builds contacts (a file loader, a test) will forget.

**Fix:** rules live in the constructor. The menu's job is to catch what the constructor throws and phrase it kindly.

### 9. Catching `Exception`

```java
} catch (Exception e) {
    System.out.println("  Not added: " + e.getMessage());
}
```

Compiles, runs, and quietly hides every bug you did not plan for. A `NullPointerException` from a typo now prints `Not added: null` and you spend an evening hunting it.

**Fix:** catch the specific exception you expect — `IllegalArgumentException`. Let the ones you did not expect crash loudly, where you can see them.

---

## Check yourself

1. `ContactBookApp` never mentions `ArrayList` anywhere. Why is that worth caring about?
2. Why does the `Contact` constructor **throw** instead of printing a friendly message itself?
3. `ContactBook.all()` builds and returns a copy of its list rather than the list itself. What breaks if it returns the real one?
4. `contacts.contains(contact)` is the entire duplicate guard. What single method makes that line work, and what does the line do if you delete that method?
5. Why must `delete` use a counted `for` loop rather than a `for`-each?
6. `search` returns an empty list when nothing matches, instead of `null`. Why is that the better choice?

<details><summary>Answers</summary>

1. Because it means the storage decision is sealed inside `ContactBook`. Swap the `ArrayList` for a file, a database, or a `HashMap` on day 2 and `ContactBookApp` does not change by a character — it only ever asked for `add`, `all`, `search`, `delete` and `size`. When one class knows a fact that no other class knows, you can change that fact cheaply. That is what "separation of concerns" buys you, and it is not abstract: it is the difference between a two-line change and a two-hour one.

2. Because `Contact` does not know who is calling. Today it is a menu with a human in front of it; tomorrow it might be a file loader or a test, where printing to the console would be wrong or invisible. Throwing says "this cannot be built, and here is why" and lets the caller decide what that means. It also guarantees no half-valid `Contact` is ever handed back — the object simply does not come into existence. Printing and returning would leave a broken object in circulation.

3. Encapsulation. `contacts` is `private` so that every change goes through `add` and `delete`, where the duplicate rule lives. Hand out the real list and any caller can call `.add()` on it directly, bypassing the guard entirely, and your `private` field is private in name only. The copy costs a few microseconds and keeps `ContactBook` genuinely in charge of its own contents.

4. `Contact.equals`. `ArrayList.contains` walks the list calling `equals` on each item — that is all it does. Delete `Contact.equals` and the inherited `Object.equals` takes over, which is `this == o`: "are these literally the same object in memory?" A freshly built `Contact` is never the same object as one already stored, so `contains` returns `false` every time, `add` never refuses anything, and duplicates accumulate with no error at all.

5. Because `remove` needs a **position**, and a `for`-each never gives you one. There is a second reason too: changing a list while a `for`-each is walking it either throws `ConcurrentModificationException` or silently skips an item, depending on where in the list you removed from. The counted loop plus an immediate `return` sidesteps both — the loop stops the instant the list changes.

6. Because "nothing matched" is a normal, expected answer, and an empty list expresses it perfectly. Every caller can loop over it (zero times) and call `.size()` on it (getting 0) with no special case. Return `null` and every caller must remember a null check, forever, and the one that forgets throws `NullPointerException` somewhere far from the cause. **Return empty collections, not `null`** is a rule worth carrying with you.

</details>

---

## Exercises

Both files are in `exercises/`. Both compile and run exactly as given, so you always start green.

Solutions are in `solutions/`, one file per exercise with the same filename. Have an honest go first — and in this module especially, *honest* means finishing a milestone before you look, not finishing the whole app. If you are properly stuck on milestone 4, read the solution's `search` method, understand it, close the file, and write milestone 5 yourself. Reading the finished app before you start costs you the entire module.

### 1. `ListWarmup.java` — the tools, before the project

**Goal:** write five small methods over an `ArrayList<String>`. Each one is a miniature of something the contact book needs: counting, finding the best item, collecting matches into a new list, removing by name, and checking whether something is already there.

**Done looks like:** all twelve checks report `true`.

```
=== ArrayList warm-up ===
 1. countLongerThan(words, 5) is 3                     true
 ...
12. containsIgnoreCase(words, "grape") is false        true

12 of 12 checks passed.
```

The starter passes 6 of 12. Look at *which* six before you start — they are all the "should find nothing" ones. Placeholders are excellent at finding nothing.

**Hint:** do them in order and re-run after each one; the checks are ordered to match. Only `removeFirstMatch` needs a counted `for` loop — the other four are for-each. Give this twenty minutes. It is the warm-up, not the project.

### 2. `ContactBookApp.java` — the capstone

**Goal:** the six milestones above, in order. When you are done you have a program that adds, lists, searches and deletes contacts, refuses invalid data with a readable message, refuses duplicate names, and cannot be crashed by anything typed at the menu.

**Done looks like:** every checkpoint in this lesson reproduces, and the hostile-input pass at the end of milestone 6 produces no stack traces.

**Hint:** the TODOs are numbered by milestone (`TODO M1`, `TODO M2.1`, and so on), so you can always find the next thing to write. When something breaks, the fastest move is almost never to stare at the code — add a `System.out.println` showing the value you *think* you have. Nine times out of ten it is not that value.

---

## Stretch goals

The app works. Now make it nicer. In rough order of difficulty:

**Report the stored spelling on a duplicate.** Right now, adding `ada lovelace` when `Ada Lovelace` exists says `There is already a contact called ada lovelace` — echoing what you typed, not what is in the book. Give `ContactBook` a `find(String name)` that returns the matching contact or `null`, and use it for the message. Two lines, and the app immediately feels more finished.

**Edit a contact.** Add option 6. Find the contact by name, ask for a new email and phone, and replace it. Notice that `Contact` is immutable — its fields are `final` — so you cannot change one. You will need to build a new `Contact` and swap it into the list. That constraint is a feature: the validation runs again automatically on the new values.

**Sort by name.** Print the list alphabetically. With day-1 tools this is a hand-written sort over a copy of the list — a genuinely good exercise in loops and `compareToIgnoreCase`. Day 2 does it in one line, and you will appreciate that line more for having written the loop.

**Search email and phone too.** One extra `||` in `search`, and suddenly the app is properly useful.

**Count the book in the goodbye.** `Bye! 3 contacts forgotten.` — and that word *forgotten* is honest, which brings us to the last point.

---

## What day 2 unlocks

Your contact book has one real limitation and you already know what it is: **quit the program and everything is gone.** There is no `save`, and there could not be, because reading and writing files is day 2 material.

Here is what day 2 gives you, and what each thing fixes in the app you just built:

**Collections, properly.** `ArrayList` was one class from a large family. You will meet `List` as an interface — and learn why professionals write `List<Contact> contacts = new ArrayList<>();`, with the interface on the left. You will meet `HashMap`, which would let you find a contact by name **instantly** instead of walking the whole list, and `HashSet`, which would enforce the no-duplicates rule for you. Both lean entirely on the `equals`/`hashCode` pair you wrote in milestone 6 — that is why it mattered.

**Generics.** The `<Contact>` in the angle brackets has a whole system behind it. You will learn to write your own generic types, so you could build a `Repository<T>` that stores contacts today and invoices tomorrow with no changes.

**Streams.** Your `search` method is a loop that filters and collects. On day 2 it is one expression:

```java
contacts.stream()
        .filter(c -> c.getName().toLowerCase().contains(needle))
        .toList();
```

Sorting, counting, grouping, finding the first match — all the same shape. Having written the loops first is exactly why the one-liners will read as a relief rather than as magic.

**Files.** Save the book on quit, load it on start, and the word *forgotten* comes out of your goodbye message for good.

You have written a real program today. It has more than one class, it validates its input, it handles its errors, it holds a collection, and a stranger could use it without reading the source. That is not a toy — that is the shape of every application you will ever build, just smaller. Everything from here is more of it.
