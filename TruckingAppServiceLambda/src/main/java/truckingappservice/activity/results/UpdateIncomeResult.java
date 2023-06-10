package truckingappservice.activity.results;

import truckingappservice.models.IncomeModel;

public class UpdateIncomeResult {
    private final IncomeModel incomeModel;

    public UpdateIncomeResult(IncomeModel incomeModel) {
        this.incomeModel = incomeModel;
    }

    public IncomeModel getIncomeModel() {
        return incomeModel;
    }

    @Override
    public String toString() {
        return "UpdateIncomeResult{" +
                "incomeModel=" + incomeModel +
                '}';
    }

    public static class Builder{
        private IncomeModel incomeModel;

        public Builder withIncome(IncomeModel incomeModel){
            this.incomeModel = incomeModel;
            return this;
        }

        public UpdateIncomeResult build(){
            return new UpdateIncomeResult(incomeModel);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}


