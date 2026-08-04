package shop.shoppingtracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        if (expense != null) {
            expenses.add(expense);
        }
    }

    public double getTotal() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public List<Expense> getExpenses() {
        return Collections.unmodifiableList(expenses);
    }
}