package mechanic.workshop.partanalyser.csvhandler;

import mechanic.Employee;
import mechanic.Part;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvFileParser {

    public List<String> csvParser(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read CSV file: " + path, e);
        }
    }

    public <T> List<T> transform(List<String> lines, int expectedCols, Function<String[], T> mapper) {
        List<T> result = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            // Skip header (unconditionally index 0) or blank lines
            if (i == 0 || line.trim().isEmpty()) {
                continue;
            }

            String[] tokens = line.split(",");

            if (tokens.length != expectedCols) {
                System.err.println("Warning: Skipping malformed line " + (i + 1) + ": " + line);
                continue;
            }

            try {
                T item = mapper.apply(tokens);
                result.add(item);
            } catch (Exception e) {
                System.err.println("Error parsing row " + (i + 1) + ": " + line);
            }
        }

        return result;
    }

    public List<Employee> employeeTransformer(List<String> employees) {
        return transform(employees, 4, tokens -> new Employee(
                tokens[0].trim(),
                tokens[1].trim(),
                tokens[2].trim(),
                Double.parseDouble(tokens[3].trim())
        ));
    }

    public List<Part> partTransformer(List<String> parts) {
        return transform(parts, 5, tokens -> new Part(
                tokens[0].trim(),
                tokens[1].trim(),
                Double.parseDouble(tokens[2].trim()),
                Integer.parseInt(tokens[3].trim()),
                tokens[4].trim()
        ));
    }
}
