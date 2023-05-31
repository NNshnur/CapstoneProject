package truckingappservice.activity.results;

import truckingappservice.models.ExpenseModel;

public class UpdateExpenseResult {
    private final ExpenseModel expenseModel;
    /**
     * Constructs a new UpdateEventResult object with the specified event model.
     * @param expenseModel The event model that was updated.
     */
    public UpdateExpenseResult(ExpenseModel expenseModel) {
        this.expenseModel = expenseModel;
    }
    /**
     * Returns the event model that was updated.
     * @return The updated event model.
     */
    public ExpenseModel getExpenseModel() {
        return expenseModel;
    }

    @Override
    public String toString() {
        return "UpdateExpenseResult{" +
                "expensemodel=" + expenseModel +
                '}';
    }

    /**
     * Builder class for constructing UpdateEventResult objects.
     */
    public static class Builder{
        private ExpenseModel expenseModel;

        public Builder withExpense(ExpenseModel expenseModel){
            this.expenseModel = expenseModel;
            return this;
        }

        public UpdateExpenseResult build(){
            return new UpdateExpenseResult(expenseModel);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}

