package truckingappservice.activity.results;

import truckingappservice.models.IncomeModel;

public class DeleteIncomeResult {
        private final IncomeModel income;

        private DeleteIncomeResult(IncomeModel income) {
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

            public DeleteIncomeResult build() {
                return new DeleteIncomeResult(income);
            }
        }
    }


