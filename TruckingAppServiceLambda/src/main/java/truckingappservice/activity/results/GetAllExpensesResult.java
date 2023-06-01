package truckingappservice.activity.results;

import truckingappservice.dynamodb.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class GetAllExpensesResult {

    private final List<Expense> allExpenseList;

    private GetAllExpensesResult(List<Expense> allExpenseList) {
        this.allExpenseList = allExpenseList;
    }

    public List<Expense> getAllExpenseList() {
        return allExpenseList;
    }

    @Override
    public String toString() {
        return "GetAllExpensesResult{" +
                "allExpenseList=" + allExpenseList +
                '}';
    }




    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Expense> allExpenseList;

        public Builder withExpenseList(List<Expense> allExpenseList) {
            this.allExpenseList = new ArrayList<>(allExpenseList);
            return this;
        }

        public GetAllExpensesResult build() {
            return new GetAllExpensesResult(allExpenseList);
        }
    }
}

