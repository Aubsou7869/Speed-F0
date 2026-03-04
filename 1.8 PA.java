// EMPLOYEE.JAVA//
public class Employee {
    
    private String firstName;
    private String lastName;
    private double monthlySalary;

    public Employee(String firstName, String lastName, double monthlySalary) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (monthlySalary >= 1000) {
            this.monthlySalary = monthlySalary;
        } else {
            this.monthlySalary = 0.0;
        }
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public double getMonthlySalary() {
        return monthlySalary;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary >= 1000) {
            this.monthlySalary = monthlySalary;
        }
    }
}

/////////////////////////////////////////////// APP.JAVA///////////////////////////////////////////////////
public class App { 
    public static void main(String[] args) { 
        System.out.println("Aubrey - Week 1 PA Classes"); 

        Employee emp1 = new Employee("Jane", "Jamison", 2500); 
        Employee emp2 = new Employee("John", "jones", 500); 

        System.out.println("\n--- Initial Employee Information ---");  
        System.out.println("Employee 1:"); 
        System.out.println(emp1.getFirstName() + " " + emp1.getLastName()); 
        System.out.println("Monthly Salary: $" + emp1.getMonthlySalary()); 
        System.out.println("\nEmployee 2:"); 
        System.out.println(emp2.getFirstName() + " " + emp2.getLastName()); 
        System.out.println("Monthly Salary: $" + emp2.getMonthlySalary());

        emp1.setFirstName("Janey"); 
        emp2.setLastName("Smith"); 
        emp1.setMonthlySalary(2750); 
        emp2.setMonthlySalary(1500); 

        System.out.println("\n--- Updated Employee Information ---"); 
        System.out.println("Employee 1:"); 
        System.out.println(emp1.getFirstName() + " " + emp1.getLastName()); 
        System.out.println("Monthly Salary: $" + emp1.getMonthlySalary());
        System.out.println("\nEmployee 2:"); 
        System.out.println(emp2.getFirstName() + " " + emp2.getLastName()); 
        System.out.println("Monthly Salary: $" + emp2.getMonthlySalary()); 
    } 
} 
