package truckingappservice.activity.results;

import truckingappservice.dynamodb.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class GetAllExpensesResult {

    private final List<Expense> allExpenseList;

    private GetAllExpensesResult(List<Expense> allExpenseList) {
        this.allExpenseList = allExpenseList;
    }
    /**
     * Returns a list of all the events in the DynamoDB table.
     *
     * @return A List object containing all the events.
     */
    public List<Expense> getAllExpenseList() {
        return allExpenseList;
    }

    @Override
    public String toString() {
        return "GetAllExpensesResult{" +
                "allExpenseList=" + allExpenseList +
                '}';
    }

    /**
     * Returns a string representation of the GetAllEventsResult object.
     *
     * @return A string containing the list of all events.
     */


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    /**
     * The Builder class for creating GetAllEventsResult objects.
     */
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

