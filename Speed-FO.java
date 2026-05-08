/*
 * Name: Aubrey Soule
 * Date: May 8,2026
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
            System.out.println("3. Update Motorcycle");
            System.out.println("4. Delete Motorcycle");
            System.out.println("5. Exit");
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

                    System.out.print("Enter Motorcycle ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("New Brand: ");
                    String newBrand = scanner.nextLine();

                    System.out.print("New Model: ");
                    String newModel = scanner.nextLine();

                    System.out.print("New Engine Size: ");
                    int newEngineSize = scanner.nextInt();

                    System.out.print("New Top Speed: ");
                    int newTopSpeed = scanner.nextInt();

                    System.out.print("New Cylinder Count: ");
                    int newCylinders = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("New Manufacturer: ");
                    String newManufacturer = scanner.nextLine();

                    System.out.print("New Type: ");
                    String newType = scanner.nextLine();

                    Engine updatedEngine = new Engine(newEngineSize, newCylinders);

                    SportMotorcycle updatedMoto = new SportMotorcycle(
                            newBrand,
                            newModel,
                            newEngineSize,
                            newTopSpeed,
                            newManufacturer,
                            newType,
                            updatedEngine
                    );

                    db.updateMotorcycle(updateId, updatedMoto);

                    break;

                case 4:
                    System.out.print("Enter id to delete: ");
                    int id = scanner.nextInt();
                    db.deleteMotorcycle(id);
                    break;
            }

        } while (choice != 5);

        scanner.close();
    }
}

/*
 * Name: Aubrey Soule
 * Date: May 8,2026
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
 * Name: Aubrey Soule
 * Date: May 8,2026
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
 * Name: Aubrey Soule
 * Date: May 8,2026
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
 * Name: Aubrey Soule
 * Date: May 8, 2026
 * Assignment: Course Project
 * Description:
 * This class manages the SQLite database for the
 * Sport Motorcycle Management System. It performs
 * CRUD operations using JDBC.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotorcycleDB implements DatabaseOperations {

    private Connection conn;

    public MotorcycleDB() {

        try {

            conn = DriverManager.getConnection(
                    "jdbc:sqlite:motorcycles.db");

            createTable();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void createTable() {

        String sql =
                "CREATE TABLE IF NOT EXISTS motorcycles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "brand TEXT," +
                "model TEXT," +
                "engine_size INTEGER," +
                "top_speed INTEGER," +
                "manufacturer TEXT," +
                "type TEXT," +
                "cylinder_count INTEGER" +
                ");";

        try (Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void createMotorcycle(SportMotorcycle moto) {

        String sql =
                "INSERT INTO motorcycles " +
                "(brand, model, engine_size, top_speed, " +
                "manufacturer, type, cylinder_count) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, moto.getBrand());
            pstmt.setString(2, moto.getModel());
            pstmt.setInt(3, moto.getEngine().getEngineSize());
            pstmt.setInt(4, moto.getTopSpeed());
            pstmt.setString(5, moto.getManufacturer());
            pstmt.setString(6, moto.getType());
            pstmt.setInt(7,
                    moto.getEngine().getCylinderCount());

            pstmt.executeUpdate();

            System.out.println(
                    "Motorcycle added to database.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<SportMotorcycle> getMotorcycles() {

        List<SportMotorcycle> list = new ArrayList<>();

        String sql = "SELECT * FROM motorcycles";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Engine engine = new Engine(
                        rs.getInt("engine_size"),
                        rs.getInt("cylinder_count")
                );

                SportMotorcycle moto =
                        new SportMotorcycle(
                                rs.getString("brand"),
                                rs.getString("model"),
                                rs.getInt("engine_size"),
                                rs.getInt("top_speed"),
                                rs.getString("manufacturer"),
                                rs.getString("type"),
                                engine
                        );

                moto.setId(rs.getInt("id"));

                list.add(moto);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateMotorcycle(
            int id,
            SportMotorcycle moto) {

        String sql =
                "UPDATE motorcycles SET " +
                "brand=?, " +
                "model=?, " +
                "engine_size=?, " +
                "top_speed=?, " +
                "manufacturer=?, " +
                "type=?, " +
                "cylinder_count=? " +
                "WHERE id=?";

        try (PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setString(1, moto.getBrand());
            pstmt.setString(2, moto.getModel());
            pstmt.setInt(3,
                    moto.getEngine().getEngineSize());
            pstmt.setInt(4, moto.getTopSpeed());
            pstmt.setString(5, moto.getManufacturer());
            pstmt.setString(6, moto.getType());
            pstmt.setInt(7,
                    moto.getEngine().getCylinderCount());

            pstmt.setInt(8, id);

            pstmt.executeUpdate();

            System.out.println(
                    "Motorcycle updated.");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void deleteMotorcycle(int id) {

        String sql =
                "DELETE FROM motorcycles WHERE id=?";

        try (PreparedStatement pstmt =
                     conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            System.out.println(
                    "Motorcycle deleted.");

        } catch (SQLException e) {

            e.printStackTrace();
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

    private int id;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return "ID: " + id +
                "\nMotorcycle: " + getBrand() + " " + getModel() +
               "\nEngine: " + engine +
               "\nTop Speed: " + getTopSpeed() + " mph" +
               "\nManufacturer: " + manufacturer +
               "\nType: " + type;
    }
}
