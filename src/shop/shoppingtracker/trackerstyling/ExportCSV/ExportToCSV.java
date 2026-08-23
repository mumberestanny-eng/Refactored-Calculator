package shop.shoppingtracker.trackerstyling.ExportCSV;

import shop.shoppingtracker.Expense;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class ExportToCSV {

    public void saveToCSV(List<Expense> expenses, File destination) throws IOException {
        if (expenses == null || expenses.isEmpty()) {
            throw new IllegalArgumentException("Cannot export an empty expense list.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination, StandardCharsets.UTF_8))) {
            writer.write("Item,Price");
            writer.newLine();

            double total = 0.0;
            for (Expense ex : expenses) {
                writer.write(escapeCsv(ex.getName()) + "," + String.format(Locale.US, "%.2f", ex.getAmount()));
                writer.newLine();
                total += ex.getAmount();
            }

            writer.newLine();
            writer.write("TOTAL," + String.format("%.2f", total));
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}
