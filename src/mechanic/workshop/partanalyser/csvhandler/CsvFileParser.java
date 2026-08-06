package mechanic.workshop.partanalyser.csvhandler;

import mechanic.Employee;
import mechanic.Part;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParser {

    public List<String> csvParser(String path){
        List<String> csvToList = new ArrayList<>();
        if (path == null|| path.isEmpty())
        {
            System.out.println("Enter a valid path of for the file");
            return List.of();
        }
        try{
            csvToList = Files.readAllLines(Paths.get(path));
            System.out.println("File read and parsed successfully");
        } catch (IOException e){
            System.out.println("Error while reading file: "+ e.getMessage());
        }
        return csvToList;
    }

    public List<Part> partTransformer(List<String> parts){
        if(parts.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return parts
                .stream()
                .filter(lines -> !lines.startsWith("Name,"))
                .map(lines -> lines.split(","))
                .filter(part -> part.length == 5)
                .map(part -> new Part(
                        part[0].trim(),
                        part[1].trim(),
                        Double.parseDouble(part[2].trim()),
                        Integer.parseInt(part[3].trim()),
                        part[4].trim()

                )).toList();
    }

    public List<Employee> employeeTransformer(List<String> employees){
        if (employees.isEmpty()){
            System.out.println("Empty List");
            return List.of();
        }
        return employees
                .stream()
                .filter(lines -> !lines.startsWith("Name,"))
                .map(lines -> lines.split(","))
                .filter(parts -> parts.length == 4)
                .map(parts -> new Employee(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        Double.parseDouble(parts[3].trim())

                )).toList();
    }
}
