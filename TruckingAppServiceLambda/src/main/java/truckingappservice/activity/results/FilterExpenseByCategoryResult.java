package truckingappservice.activity.results;

import truckingappservice.dynamodb.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class FilterExpenseByCategoryResult {
    private final List<Expense> allExpenseListByCategory;

    private FilterExpenseByCategoryResult(List<Expense> allExpenseListByCategory) {
        this.allExpenseListByCategory = allExpenseListByCategory;
    }

    public List<Expense> getExpenseListByCategory() {
        return allExpenseListByCategory;
    }

    @Override
    public String toString() {
        return "FilterExpenseByCategoryResult{" +
                "allExpenseListByCategory=" + allExpenseListByCategory +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Expense> allExpenseListByCategory;

        public Builder withExpenseListByCategory(List<Expense> allExpenseListByCategory) {
            this.allExpenseListByCategory = new ArrayList<>(allExpenseListByCategory);
            return this;
        }

        public FilterExpenseByCategoryResult build() {
            return new FilterExpenseByCategoryResult(allExpenseListByCategory);
        }
    }
}
