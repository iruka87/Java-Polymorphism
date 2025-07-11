import java.util.ArrayList;
import java.util.List;

// Custom exception for treatment issues
class TreatmentException extends Exception {
    public TreatmentException(String message) {
        super(message);
    }
}

// Abstract Animal class
abstract class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Abstract method for treatment
    public abstract void treat() throws TreatmentException;

    public String getName() {
        return name;
    }
}

// Dog subclass
class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public void treat() throws TreatmentException {
        try {
            if (age > 15) {
                throw new TreatmentException("Elderly dog requires special care");
            }
            System.out.println("Treating " + name + ": Administering canine medication and checking for fleas.");
        } catch (Exception e) {
            throw new TreatmentException("Error treating dog " + name + ": " + e.getMessage());
        }
    }
}

// Cat subclass
class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public void treat() throws TreatmentException {
        try {
            if (age < 0) {
                throw new TreatmentException("Invalid age for cat");
            }
            System.out.println("Treating " + name + ": Providing feline vaccine and checking for hairballs.");
        } catch (Exception e) {
            throw new TreatmentException("Error treating cat " + name + ": " + e.getMessage());
        }
    }
}

// Bird subclass
class Bird extends Animal {
    public Bird(String name, int age) {
        super(name, age);
    }

    @Override
    public void treat() throws TreatmentException {
        try {
            System.out.println("Treating " + name + ": Clipping wings and checking beak condition.");
        } catch (Exception e) {
            throw new TreatmentException("Error treating bird " + name + ": " + e.getMessage());
        }
    }
}

// Main hospital management class
public class AnimalHospital {
    private List<Animal> animals;

    public AnimalHospital() {
        animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // Polymorphic treatment of all animals
    public void treatAllAnimals() {
        System.out.println("\nStarting treatment for all animals:");
        for (Animal animal : animals) {
            try {
                System.out.println("\nAttempting to treat " + animal.getName());
                animal.treat();
            } catch (TreatmentException e) {
                System.err.println("Treatment failed: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error treating " + animal.getName() + ": " + e.getMessage());
            } finally {
                System.out.println("Finished processing " + animal.getName());
            }
        }
    }

    public static void main(String[] args) {
        AnimalHospital hospital = new AnimalHospital();

        // Adding animals
        try {
            hospital.addAnimal(new Dog("Rex", 3));
            hospital.addAnimal(new Cat("Whiskers", 5));
            hospital.addAnimal(new Bird("Tweety", 2));
            hospital.addAnimal(new Dog("Oldie", 17)); // Dog with potential treatment issue
            hospital.addAnimal(new Cat("Mittens", -1)); // Cat with invalid age
        } catch (Exception e) {
            System.err.println("Error adding animals: " + e.getMessage());
        }

        // Treat all animals
        hospital.treatAllAnimals();
    }
}