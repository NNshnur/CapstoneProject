package truckingappservice.activity.results;

import truckingappservice.models.IncomeModel;

public class CreateIncomeResult {
    private final IncomeModel income;

    private CreateIncomeResult(IncomeModel income) {
        this.income = income;
    }

    public IncomeModel getIncome() {
        return income;
    }

    @Override
    public String toString() {
        return "CreateIncomeResult{" +
                "income=" + income +
                '}';
    }


//CHECKSTYLE:OFF:Builder

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private IncomeModel income;

        public Builder withIncome(IncomeModel income) {
            this.income = income;
            return this;
        }

        public CreateIncomeResult build() {
            return new CreateIncomeResult(income);
        }
    }
}

