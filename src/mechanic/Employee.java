package mechanic;

public class Employee{
    private final String name;
    private final String work;
    private final String department;
    private final double salary;

    public Employee(String name, String work, String department, double salary) {
        this.name = name;
        this.work = work;
        this.department = department;
        this.salary = salary;
    }
    public String getName() {return name;}
    public String getWork() {return work;}
    public String getDepartment() {return department;}
    public double getSalary() {return salary;}
    @Override
    public String toString() {
        return String.format("Employee [Name: %s, Work: %s, Department: %s, Salary: %.2f]", name, work, department, salary);
    }
}
