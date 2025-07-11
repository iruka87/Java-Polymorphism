import java.util.ArrayList;
import java.util.List;

// Custom exception for payroll issues
class PayrollException extends Exception {
    public PayrollException(String message) {
        super(message);
    }
}

// Abstract Employee class
abstract class Employee {
    protected String name;
    protected String id;
    protected double basePay;

    public Employee(String name, String id, double basePay) {
        this.name = name;
        this.id = id;
        this.basePay = basePay;
    }

    // Abstract method for salary calculation
    public abstract double calculateSalary() throws PayrollException;

    public String getName() {
        return name;
    }
}

// FullTime employee subclass
class FullTime extends Employee {
    private double benefits;

    public FullTime(String name, String id, double basePay, double benefits) {
        super(name, id, basePay);
        this.benefits = benefits;
    }

    @Override
    public double calculateSalary() throws PayrollException {
        try {
            if (basePay <= 0) {
                throw new PayrollException("Invalid base pay for full-time employee");
            }
            double salary = basePay + benefits;
            System.out.println("Calculating salary for full-time employee " + name + ": $" + String.format("%.2f", salary));
            return salary;
        } catch (Exception e) {
            throw new PayrollException("Error calculating salary for " + name + ": " + e.getMessage());
        }
    }
}

// PartTime employee subclass
class PartTime extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public PartTime(String name, String id, double hoursWorked, double hourlyRate) {
        super(name, id, hourlyRate);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() throws PayrollException {
        try {
            if (hoursWorked < 0 || hourlyRate <= 0) {
                throw new PayrollException("Invalid hours or rate for part-time employee");
            }
            double salary = hoursWorked * hourlyRate;
            System.out.println("Calculating salary for part-time employee " + name + ": $" + String.format("%.2f", salary));
            return salary;
        } catch (Exception e) {
            throw new PayrollException("Error calculating salary for " + name + ": " + e.getMessage());
        }
    }
}

// Intern employee subclass
class Intern extends Employee {
    private double stipend;

    public Intern(String name, String id, double stipend) {
        super(name, id, stipend);
        this.stipend = stipend;
    }

    @Override
    public double calculateSalary() throws PayrollException {
        try {
            if (stipend < 0) {
                throw new PayrollException("Invalid stipend for intern");
            }
            System.out.println("Calculating salary for intern " + name + ": $" + String.format("%.2f", stipend));
            return stipend;
        } catch (Exception e) {
            throw new PayrollException("Error calculating salary for " + name + ": " + e.getMessage());
        }
    }
}

// Main payroll system class
public class PayrollSystem {
    private List<Employee> employees;

    public PayrollSystem() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    // Polymorphic salary calculation for all employees
    public void processPayroll() {
        System.out.println("\nProcessing payroll for all employees:");
        double totalPayroll = 0.0;
        for (Employee employee : employees) {
            try {
                System.out.println("\nProcessing " + employee.getName());
                double salary = employee.calculateSalary();
                totalPayroll += salary;
            } catch (PayrollException e) {
                System.err.println("Payroll error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error processing " + employee.getName() + ": " + e.getMessage());
            } finally {
                System.out.println("Finished processing " + employee.getName());
            }
        }
        System.out.println("\nTotal payroll amount: $" + String.format("%.2f", totalPayroll));
    }

    public static void main(String[] args) {
        PayrollSystem payroll = new PayrollSystem();

        // Adding employees
        try {
            payroll.addEmployee(new FullTime("John Kamau", "FT001", 5000, 1000));
            payroll.addEmployee(new PartTime("Jane Karen", "PT001", 20, 25));
            payroll.addEmployee(new Intern("Bob Caulimo", "IN001", 1000));
            payroll.addEmployee(new FullTime("Alice Bowen", "FT002", -100, 500)); // Invalid base pay
            payroll.addEmployee(new PartTime("Tom Holland", "PT002", -10, 20)); // Invalid hours
        } catch (Exception e) {
            System.err.println("Error adding employees: " + e.getMessage());
        }

        // Process payroll
        payroll.processPayroll();
    }
}
