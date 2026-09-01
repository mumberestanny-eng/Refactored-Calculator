package mechanic.workshop.partanalyser;

import mechanic.Employee;
import mechanic.workshop.WorkShopEmployeeAnalyser;
import mechanic.workshop.partanalyser.csvhandler.CsvFileParser;

import java.nio.file.Path;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class EmployeeAnalyser {

    public static void main(String[] args){

        CsvFileParser fileParser = new CsvFileParser();
        WorkShopEmployeeAnalyser employeeAnalyser = new WorkShopEmployeeAnalyser();
        String path = "employees_100.csv";  // Path to the employee csv file

        List<String> employeesAsList = fileParser.csvParser(Path.of(path));
        List<Employee> employeesAsEmployee = fileParser.employeeTransformer(employeesAsList);

        System.out.println("\n____List of all the employee of our workshop____\n");
        employeeAnalyser.listOfEmployees(employeesAsEmployee).forEach(System.out::println);

        System.out.println("\n____List of all the employee and their work type____\n");
        employeeAnalyser.groupByWork(employeesAsEmployee).forEach((workType, names) ->
                System.out.printf("%s : %s%n", workType, names));

        System.out.println("\n____Get all the departments within our workshop____\n");
        employeeAnalyser.getAllDepartments(employeesAsEmployee).forEach(System.out::println);

        System.out.println("\n____Get all the roles within our workshop____\n");
        employeeAnalyser.getAllWorks(employeesAsEmployee).forEach(System.out::println);

        System.out.println("\n____Get the average salary by department____\n");
        employeeAnalyser.averageSalaryByDepartment(employeesAsEmployee).forEach((department, employeeOptional) ->
                        System.out.printf("Department Name: %s, Average Salary: $%.2f%n", department, employeeOptional));

        System.out.println("\n____Get the average salary by work____\n");
        employeeAnalyser.averageSalaryByWork(employeesAsEmployee).forEach((work, empOptional) ->
                        System.out.printf("Work type: %s, Average Salary: $%.2f%n", work, empOptional));

        System.out.println("\n____Get all the employees earning under $85000.00____\n");
        employeeAnalyser.lowestSalary(employeesAsEmployee, 85000.00).forEach(System.out::println);

        System.out.println("\n____The statistics of our workshop employee____\n");
        DoubleSummaryStatistics empStats = employeeAnalyser.salaryStatistics(employeesAsEmployee);
        System.out.printf("Number of employees in our workshop: %d%n", empStats.getCount());
        System.out.printf("Highest salary in the workshop: $%.2f%n", empStats.getMax());
        System.out.printf("Lowest salary in the workshop: $%.2f%n", empStats.getMin());
        System.out.printf("Total amount spending on employee wages: $%.2f%n", empStats.getSum());

    }
}
