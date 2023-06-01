package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAllExpensesRequest.Builder.class)
public class GetAllExpensesRequest {

    public GetAllExpensesRequest() {
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        public GetAllExpensesRequest build() {
            return new GetAllExpensesRequest();
        }
    }

}

