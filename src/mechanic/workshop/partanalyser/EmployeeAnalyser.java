package mechanic.workshop.partanalyser;

import mechanic.Employee;
import mechanic.workshop.WorkShopEmployeeAnalyser;
import mechanic.workshop.partanalyser.csvhandler.CsvFileParser;

import java.util.List;

public class EmployeeAnalyser {

    public static void main(String[] args){

        CsvFileParser fileParser = new CsvFileParser();
        WorkShopEmployeeAnalyser employeeAnalyser = new WorkShopEmployeeAnalyser();
        String path = "employees_100.csv";  // Path to the employee csv file

        List<String> employeesAsList = fileParser.csvParser(path);
        List<Employee> employeesAsEmployee = fileParser.employeeTransformer(employeesAsList);

        System.out.println("\n____List of all the employee of our workshop____\n");
        employeeAnalyser.listOfEmployees(employeesAsEmployee).forEach(System.out::println);

        System.out.println("\n____List of all the employee and their work type____\n");
        employeeAnalyser.groupByWork(employeesAsEmployee);

        System.out.println("\n____Get all the departments within our workshop____\n");
        employeeAnalyser.getAllDepartments(employeesAsEmployee).forEach(System.out::println);

        System.out.println("\n____Get all the roles within our workshop____\n");
        employeeAnalyser.getAllWorks(employeesAsEmployee).forEach(System.out::println);

        System.out.println("\n____Get the average salary by department____\n");
        employeeAnalyser.averageSalaryByDepartment(employeesAsEmployee);

        System.out.println("\n____Get the average salary by role____\n");
        employeeAnalyser.averageSalaryByWork(employeesAsEmployee);

        System.out.println("\n____Get all the employees earning under $85000.00____\n");
        employeeAnalyser.lowestSalary(employeesAsEmployee, 85000.00).forEach(System.out::println);

        System.out.println("\n____The statistics of our workshop employee____\n");
        employeeAnalyser.salaryStatistics(employeesAsEmployee);

    }
}
