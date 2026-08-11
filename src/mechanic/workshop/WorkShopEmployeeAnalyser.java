package mechanic.workshop;

import mechanic.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class WorkShopEmployeeAnalyser {

    public List<String> listOfEmployees(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employeesList
                .stream()
                .map(Employee::getName)
                .sorted()
                .toList();
    }

    public void groupByWork(List<Employee> employeesList) {
        if (employeesList == null || employeesList.isEmpty()) {
            System.out.println("Empty List");
            return;
        }

        Map<String, List<String>> employeesByWork = employeesList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getWork,
                        Collectors.mapping(Employee::getName, Collectors.toList())
                ));

        employeesByWork.forEach((workType, names) ->
                System.out.printf("%s : %s%n", workType, names)
        );
    }

    public List<String> getAllDepartments(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }

        return employeesList
                .stream()
                .map(Employee::getDepartment)
                .distinct()
                .sorted()
                .toList();
    }
    public List<String> getAllWorks(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employeesList
                .stream()
                .map(Employee::getWork)
                .distinct()
                .sorted()
                .toList();
    }

    public void averageSalaryByDepartment(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return;
        }
        Map<String, Double> avgDep = employeesList
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));

        avgDep.forEach((department,salary) -> System.out.printf("Department Name: %s, Average Salary: $%.2f\n", department, salary));
    }
    public void averageSalaryByWork(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return;
        }
        Map<String, Double> avgWork = employeesList
                .stream()
                .collect(Collectors.groupingBy(Employee::getWork, Collectors.averagingDouble(Employee::getSalary)));

        avgWork.forEach((work, salary) -> System.out.printf("Work Name: %s, Salary: $%.2f\n", work, salary));
    }

    public List<Employee> lowestSalary(List<Employee> employeesList, double threshold){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employeesList
                .stream()
                .filter(emp -> emp.getSalary() < threshold)
                .distinct()
                .sorted(Comparator.comparingDouble(Employee::getSalary)
                        .thenComparing(Employee::getName))
                .toList();
    }

    public void highestSalaryByDepartment(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return;
        }
        Map<String, Optional<Employee>> highByDep = employeesList
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment, Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
                ));

        highByDep.forEach((department, employeeOptional) ->
              employeeOptional.ifPresent(emp ->
                      System.out.printf("Department Name: %s, Highest Salary: %.2f%n", department, emp.getSalary())));

    }

    public void highestSalaryByWork(List<Employee> employeesList) {
        if (employeesList.isEmpty()) {
            System.out.println("Empty List");
            return;
        }

        Map<String, Optional<Employee>> higByWork = employeesList.stream()
                .collect(Collectors.groupingBy(
                        Employee::getWork,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
                ));

        higByWork.forEach((work, empOptional) ->
                empOptional.ifPresent(emp ->
                        System.out.printf("Work type: %s, Highest Salary: %.2f%n", work, emp.getSalary())
                )
        );
    }

    public List<Employee> sortedByNameSalary(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employeesList
                .stream()
                .sorted(Comparator
                        .comparing(Employee::getName)
                        .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                        .thenComparing(Employee::getDepartment).thenComparing(Employee::getWork)).toList();
    }
    public Optional<Employee> highestSalaryEmployee(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Optional.empty();
        }

        return employeesList
                .stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }

    public Optional<Employee> lowestSalaryEmployee(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Optional.empty();
        }
        return employeesList
                .stream()
                .min(Comparator.comparingDouble(Employee::getSalary));
    }

    public void salaryStatistics(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return;
        }
        DoubleSummaryStatistics doubleStatistics = employeesList
                .stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();
        System.out.println("Number of Employees: " + doubleStatistics.getCount());
        System.out.println("Salary Average : $" + doubleStatistics.getAverage());
        System.out.println("Lowest Salary : $" + doubleStatistics.getMin());
        System.out.println("Highest Salary $: " + doubleStatistics.getMax());
    }
}
