package truckingappservice.activity.results;

import truckingappservice.models.ExpenseModel;

public class CreateExpenseResult {

    private final ExpenseModel expense;

    private CreateExpenseResult(ExpenseModel expense) {
        this.expense = expense;
    }

    public ExpenseModel getExpense() {
        return expense;
    }


    @Override
    public String toString() {
        return "CreateExpenseResult{" +
                "expense=" + expense +
                '}';
    }


    //CHECKSTYLE:OFF:Builder

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ExpenseModel expense;

        public Builder withExpense(ExpenseModel expense) {
            this.expense = expense;
            return this;
        }

        public CreateExpenseResult build() {
            return new CreateExpenseResult(expense);
        }
    }
}

