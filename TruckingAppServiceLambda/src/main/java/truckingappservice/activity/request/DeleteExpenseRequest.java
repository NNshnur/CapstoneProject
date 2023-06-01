package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder= DeleteExpenseRequest.Builder.class)
public class DeleteExpenseRequest {
    private final String expenseId;

    private DeleteExpenseRequest(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    @Override
    public String toString() {
        return "DeleteExpenseRequest{" +
                "expenseId='" + expenseId + '\'' +
                '}';
    }
    //CHECKSTYLE:OFF:Builder

    public static Builder builder() {
        return new Builder();
    }


    @JsonPOJOBuilder
    public static class Builder {

        private String expenseId;

        public Builder withExpenseId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public DeleteExpenseRequest build() {
            return new DeleteExpenseRequest(expenseId);
        }
    }
}