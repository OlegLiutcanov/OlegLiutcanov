// EXERCISE 1 - Animal / Dog / Cat
//
// Run me with:  java AnimalSounds.java
// This file compiles and runs right now. Your job is to make it print the
// right thing.
//
// EXPECTED OUTPUT when you are done:
//
// Rex says Woof!
// Whiskers says Meow!
// Buddy says Woof!
// Cleo says Meow!
// Total animals: 4
// Pepper runs after the ball and brings it back.
//
// Do not change main. Everything you need to edit is marked TODO.

public class AnimalSounds {
    public static void main(String[] args) {
        // One array, four objects, two different classes. The loop below never
        // asks "are you a Dog or a Cat?" - that is polymorphism doing the work.
        Animal[] shelter = {
                new Dog("Rex"),
                new Cat("Whiskers"),
                new Dog("Buddy"),
                new Cat("Cleo")
        };

        for (Animal animal : shelter) {
            System.out.println(animal.describe());
        }
        System.out.println("Total animals: " + shelter.length);

        // fetch() only exists on Dog, so this variable is declared as a Dog,
        // not as an Animal.
        Dog pepper = new Dog("Pepper");
        System.out.println(pepper.fetch());
    }
}

class Animal {
    private final String name;

    Animal(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    // The generic sound. Dog and Cat should each replace this with their own.
    String speak() {
        return "...";
    }

    // Written once, inherited by every subclass. Leave it alone - notice that
    // it will start producing the right sentences as soon as speak() is
    // overridden properly.
    String describe() {
        return name + " says " + speak();
    }
}

class Dog extends Animal {

    Dog(String name) {
        super(name);
    }

    // TODO 1: override speak() so a Dog returns "Woof!"
    //         Remember the @Override annotation.

    // TODO 2: make fetch() return
    //         "<name> runs after the ball and brings it back."
    //         Use getName() to read the name - it is private in Animal.
    String fetch() {
        return null;
    }
}

class Cat extends Animal {

    Cat(String name) {
        super(name);
    }

    // TODO 3: override speak() so a Cat returns "Meow!"
}
