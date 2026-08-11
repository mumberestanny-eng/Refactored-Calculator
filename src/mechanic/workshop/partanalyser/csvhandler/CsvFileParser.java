package mechanic.workshop.partanalyser.csvhandler;

import mechanic.Employee;
import mechanic.Part;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParser {

    public List<String> csvParser(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path must not be null or empty");
        }

        try {
            List<String> csvToList = Files.readAllLines(Paths.get(path));
            System.out.println("File read and parsed successfully");
            return csvToList;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read CSV file at path: " + path, e);
        }
    }

    public List<Part> partTransformer(List<String> parts) {
        if (parts == null || parts.isEmpty()) {
            System.out.println("Empty List");
            return List.of();
        }

        List<Part> parsedParts = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {
            String line = parts.get(i);

            // Skip header or blank lines
            if (line.trim().isEmpty() || line.startsWith("Name,")) {
                continue;
            }

            // Split CSV handling simple quoted values: splits on commas outside quotes
            String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (columns.length < 5) {
                System.err.printf("Skipping row %d: expected 5 columns but found %d -> %s%n", i + 1, columns.length, line);
                continue;
            }

            try {
                String name = cleanField(columns[0]);
                String category = cleanField(columns[1]);
                double price = Double.parseDouble(cleanField(columns[2]));
                int quantity = Integer.parseInt(cleanField(columns[3]));
                String location = cleanField(columns[4]);

                parsedParts.add(new Part(name, category, price, quantity, location));

            } catch (NumberFormatException e) {
                System.err.printf("Skipping row %d: invalid numeric value -> %s%n", i + 1, line);
            }
        }

        return parsedParts;
    }

    private String cleanField(String field) {
        String trimmed = field.trim();
        if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            return trimmed.substring(1, trimmed.length() - 1).replace("\"\"", "\"");
        }
        return trimmed;
    }

    public List<Employee> employeeTransformer(List<String> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("Empty List");
            return List.of();
        }

        List<Employee> parsedEmployees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            String line = employees.get(i);

            // Skip header or blank lines
            if (line.trim().isEmpty() || line.startsWith("Name,")) {
                continue;
            }

            // Split on commas while preserving quoted fields
            String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (columns.length < 4) {
                System.err.printf("Skipping row %d: expected 4 columns but found %d -> %s%n", i + 1, columns.length, line);
                continue;
            }

            try {
                String name = cleanField(columns[0]);
                String role = cleanField(columns[1]);
                String department = cleanField(columns[2]);
                double salary = Double.parseDouble(cleanField(columns[3]));

                parsedEmployees.add(new Employee(name, role, department, salary));

            } catch (NumberFormatException e) {
                System.err.printf("Skipping row %d: invalid salary value -> %s%n", i + 1, line);
            }
        }

        return parsedEmployees;
    }
}
