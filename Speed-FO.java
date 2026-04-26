/*
 * Name: Aubrey
 * Date: April 26,2026
 * Assignment: Course Project
 * Description: Main Application
 */

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        MotorcycleDB db = new MotorcycleDB();

        int choice;

        do {
            System.out.println("\nWelcome to Speed-FO!!");
            System.out.println("\nYour Sport Motorcycle Info System\n");
            System.out.println("1. Add Motorcycle");
            System.out.println("2. View Motorcycles");
            System.out.println("3. Delete Motorcycle");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Brand: ");
                    String brand = scanner.nextLine();

                    System.out.print("Model: ");
                    String model = scanner.nextLine();

                    System.out.print("Engine Size (cc): ");
                    int engineSize = scanner.nextInt();

                    System.out.print("Top Speed: ");
                    int topSpeed = scanner.nextInt();

                    System.out.print("Cylinder Count: ");
                    int cylinders = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Manufacturer: ");
                    String manufacturer = scanner.nextLine();

                    System.out.print("Type: ");
                    String type = scanner.nextLine();

                    Engine engine = new Engine(engineSize, cylinders);
                    SportMotorcycle moto = new SportMotorcycle(
                            brand, model, engineSize, topSpeed,
                            manufacturer, type, engine
                    );

                    db.createMotorcycle(moto);
                    break;

                case 2:
                    for (SportMotorcycle m : db.getMotorcycles()) {
                        m.displayInfo();
                    }
                    break;

                case 3:
                    System.out.print("Enter index to delete: ");
                    int index = scanner.nextInt();
                    db.deleteMotorcycle(index);
                    break;
            }

        } while (choice != 4);

        scanner.close();
    }
}

/*
 * Name: Aubrey
 * Date: April 26,2026
 * Assignment: Course Project
 * Description: Interface
 */

import java.util.List;

public interface DatabaseOperations {

    void createMotorcycle(SportMotorcycle moto);

    List<SportMotorcycle> getMotorcycles();

    void updateMotorcycle(int id, SportMotorcycle moto);

    void deleteMotorcycle(int id);
}

/*
 * Name: Aubrey
 * Date: April 26,2026
 * Assignment: Course Project
 * Description: Engine info
 */

public class Engine {

    private int engineSize;
    private int cylinderCount;

    // Constructor
    public Engine(int engineSize, int cylinderCount) {
        this.engineSize = engineSize;
        this.cylinderCount = cylinderCount;
    }

    // Getters and Setters
    public int getEngineSize() { return engineSize; }
    public void setEngineSize(int engineSize) { this.engineSize = engineSize; }

    public int getCylinderCount() { return cylinderCount; }
    public void setCylinderCount(int cylinderCount) { this.cylinderCount = cylinderCount; }

    @Override
    public String toString() {
        return engineSize + "cc, " + cylinderCount + " cylinders";
    }
}

/*
 * Name: Aubrey
 * Date: April 26,2026
 * Assignment: Course Project
 * Description: Abstract base class for all motorcycles
 */

public abstract class Motorcycle {

    private String brand;
    private String model;
    private int engineSize;
    private int topSpeed;

    // Constructor
    protected Motorcycle(String brand, String model, int engineSize, int topSpeed) {
        this.brand = brand;
        this.model = model;
        this.engineSize = engineSize;
        this.topSpeed = topSpeed;
    }

    // Getters and Setters
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getEngineSize() { return engineSize; }
    public void setEngineSize(int engineSize) { this.engineSize = engineSize; }

    public int getTopSpeed() { return topSpeed; }
    public void setTopSpeed(int topSpeed) { this.topSpeed = topSpeed; }

    // Abstract method (polymorphism)
    public abstract void displayInfo();
}

/*
 * Name: Aubrey
 * Date: April 26,2026
 * Assignment: Course Project
 * Description: Temporary Database
 */

import java.util.ArrayList;
import java.util.List;

public class MotorcycleDB implements DatabaseOperations {

    private List<SportMotorcycle> motorcycleList = new ArrayList<>();

    @Override
    public void createMotorcycle(SportMotorcycle moto) {
        motorcycleList.add(moto);
        System.out.println("Motorcycle added successfully.");
    }

    @Override
    public List<SportMotorcycle> getMotorcycles() {
        return motorcycleList;
    }

    @Override
    public void updateMotorcycle(int index, SportMotorcycle moto) {
        if (index >= 0 && index < motorcycleList.size()) {
            motorcycleList.set(index, moto);
            System.out.println("Motorcycle updated.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    @Override
    public void deleteMotorcycle(int index) {
        if (index >= 0 && index < motorcycleList.size()) {
            motorcycleList.remove(index);
            System.out.println("Motorcycle deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }
}

/*
 * Name: Aubrey
 * Date: April 26,2026
 * Assignment: Course Project
 * Description: A Sport Motorcycle
 */

public class SportMotorcycle extends Motorcycle {

    private String manufacturer;
    private String type;
    private Engine engine; // Composition

    // Constructor
    public SportMotorcycle(String brand, String model, int engineSize, int topSpeed,
                           String manufacturer, String type, Engine engine) {
        super(brand, model, engineSize, topSpeed);
        this.manufacturer = manufacturer;
        this.type = type;
        this.engine = engine;
    }

    // Getters and Setters
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Engine getEngine() { return engine; }
    public void setEngine(Engine engine) { this.engine = engine; }

    // Polymorphism (override)
    @Override
    public void displayInfo() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Motorcycle: " + getBrand() + " " + getModel() +
               "\nEngine: " + engine +
               "\nTop Speed: " + getTopSpeed() + " mph" +
               "\nManufacturer: " + manufacturer +
               "\nType: " + type;
    }
}
