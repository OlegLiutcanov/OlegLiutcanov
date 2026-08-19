// EXERCISE 1 - SOLUTION

public class AnimalSounds {
    public static void main(String[] args) {
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

    String speak() {
        return "...";
    }

    String describe() {
        return name + " says " + speak();
    }
}

class Dog extends Animal {

    Dog(String name) {
        super(name);
    }

    @Override
    String speak() {
        return "Woof!";
    }

    // A method that exists only on Dog. Calling it needs a Dog-typed variable.
    String fetch() {
        return getName() + " runs after the ball and brings it back.";
    }
}

class Cat extends Animal {

    Cat(String name) {
        super(name);
    }

    @Override
    String speak() {
        return "Meow!";
    }
}
