package truckingappservice.activity.results;

import truckingappservice.models.ExpenseModel;

public class UpdateExpenseResult {
    private final ExpenseModel expenseModel;

    public UpdateExpenseResult(ExpenseModel expenseModel) {
        this.expenseModel = expenseModel;
    }

    public ExpenseModel getExpenseModel() {
        return expenseModel;
    }

    @Override
    public String toString() {
        return "UpdateExpenseResult{" +
                "expensemodel=" + expenseModel +
                '}';
    }


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

