package truckingappservice.activity.results;

import truckingappservice.models.IncomeModel;

import java.util.List;

public class DeleteIncomeResult {
        private final List<String> incomeList;

        private DeleteIncomeResult(List<String> incomeList) {
            this.incomeList = incomeList;
        }

        public List<String> getIncomeList() {
            return incomeList;
        }

        @Override
        public String toString() {
            return "CreateIncomeResult{" +
                    "income=" + incomeList +
                    '}';
        }


//CHECKSTYLE:OFF:Builder

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private List<String> incomeList;

            public Builder withIncomeList(List<String> incomeList) {
                this.incomeList = incomeList;
                return this;
            }

            public DeleteIncomeResult build() {
                return new DeleteIncomeResult(incomeList);
            }
        }
    }


