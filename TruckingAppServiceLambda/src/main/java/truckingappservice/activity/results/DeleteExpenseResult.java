package truckingappservice.activity.results;

import java.util.List;

public class DeleteExpenseResult {
    private final List<String> expenseList;

    private DeleteExpenseResult(List<String> expenseList){
        this.expenseList = expenseList;
    }

    public List<String> getExpenseList() {
        return expenseList;
    }

    @Override
    public String toString() {
        return "DeleteExpenseResult{" +
                "expenseList=" + expenseList +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private List<String> expenseList;

        public Builder withExpenseList(List<String> expenseList){
            this.expenseList = expenseList;
            return this;
        }

        public DeleteExpenseResult build(){
            return new DeleteExpenseResult(expenseList);
        }
    }
}
