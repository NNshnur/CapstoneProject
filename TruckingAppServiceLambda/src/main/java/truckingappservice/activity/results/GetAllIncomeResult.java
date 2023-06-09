package truckingappservice.activity.results;

import truckingappservice.dynamodb.models.Income;

import java.util.ArrayList;
import java.util.List;

public class GetAllIncomeResult {
    private final List<Income> allIncomeList;

    private GetAllIncomeResult(List<Income> allIncomeList) {
        this.allIncomeList = allIncomeList;
    }

    public List<Income> getAllIncomeList() {
        return allIncomeList;
    }

    @Override
    public String toString() {
        return "GetAllIncomeResult{" +
                "allIncomeList=" + allIncomeList +
                '}';
    }




    //CHECKSTYLE:OFF:Builder
    public static GetAllIncomeResult.Builder builder() {
        return new GetAllIncomeResult.Builder();
    }

    public static class Builder {
        private List<Income> allIncomeList;

        public GetAllIncomeResult.Builder withIncomeList(List<Income> allIncomeList) {
            this.allIncomeList = new ArrayList<>(allIncomeList);
            return this;
        }

        public GetAllIncomeResult build() {
            return new GetAllIncomeResult(allIncomeList);
        }
    }
}

