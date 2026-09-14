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
                .map(Employee::name)
                .sorted()
                .toList();
    }

    public Map<String, List<String>> groupByWork(List<Employee> employeesList) {
        if (employeesList == null || employeesList.isEmpty()) {
            System.out.println("Empty List");
            return Map.of();
        }

         return employeesList.stream()
                .collect(Collectors.groupingBy(
                        Employee::work,
                        Collectors.mapping(Employee::name, Collectors.toList())
                ));


    }

    public List<String> getAllDepartments(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }

        return employeesList
                .stream()
                .map(Employee::department)
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
                .map(Employee::work)
                .distinct()
                .sorted()
                .toList();
    }

    public Map<String, Double> averageSalaryByDepartment(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Map.of();
        }
         return  employeesList
                .stream()
                .collect(Collectors.groupingBy(Employee::department, Collectors.averagingDouble(Employee::salary)));


    }

    public Map<String, Double> averageSalaryByWork(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Map.of();
        }
        return employeesList
                .stream()
                .collect(Collectors.groupingBy(Employee::work, Collectors.averagingDouble(Employee::salary)));
    }

    public List<Employee> lowestSalary(List<Employee> employeesList, double threshold){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employeesList
                .stream()
                .filter(emp -> emp.salary() < threshold)
                .sorted(Comparator.comparingDouble(Employee::salary)
                        .thenComparing(Employee::name))
                .toList();
    }

    public Map<String, Optional<Employee>> highestSalaryByDepartment(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Map.of();
        }

        return employeesList
                .stream()
                .collect(Collectors.groupingBy(
                        Employee::department, Collectors.maxBy(Comparator.comparingDouble(Employee::salary))
                ));

    }

    public Map<String, Optional<Employee>> highestSalaryByWork(List<Employee> employeesList) {
        if (employeesList.isEmpty()) {
            System.out.println("Empty List");
            return Map.of();
        }

        return employeesList.stream()
                .collect(Collectors.groupingBy(
                        Employee::work,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::salary))
                ));
    }

    public List<Employee> sortedByNameSalary(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employeesList
                .stream()
                .sorted(Comparator
                        .comparing(Employee::name)
                        .thenComparing(Comparator.comparingDouble(Employee::salary).reversed())
                        .thenComparing(Employee::department).thenComparing(Employee::work)).toList();
    }

    public Optional<Employee> highestSalaryEmployee(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Optional.empty();
        }

        return employeesList
                .stream()
                .max(Comparator.comparingDouble(Employee::salary));
    }

    public Optional<Employee> lowestSalaryEmployee(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
            return Optional.empty();
        }
        return employeesList
                .stream()
                .min(Comparator.comparingDouble(Employee::salary));
    }

    public DoubleSummaryStatistics salaryStatistics(List<Employee> employeesList){
        if (employeesList.isEmpty()){
            System.out.println("Empty List");
        }
       return employeesList
                .stream()
                .mapToDouble(Employee::salary)
                .summaryStatistics();
    }
}