package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder= DeleteIncomeRequest.Builder.class)
public class DeleteIncomeRequest {
    private final String incomeId;

    public DeleteIncomeRequest(String incomeId) {
        this.incomeId = incomeId;
    }

    public String getIncomeId() {
        return incomeId;
    }


    @Override
    public String toString() {
        return "DeleteIncomeRequest{" +
                "incomeId='" + incomeId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder

    public static Builder builder() {
        return new Builder();
    }


    @JsonPOJOBuilder
    public static class Builder {
        private String incomeId;

        public Builder withIncomeId(String incomeId) {
            this.incomeId = incomeId;
            return this;
        }


        public DeleteIncomeRequest build() {
            return new DeleteIncomeRequest(incomeId);
        }
    }
}
