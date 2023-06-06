package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAllExpensesRequest.Builder.class)
public class GetAllExpensesRequest {
    private String id;

    public GetAllExpensesRequest() {
    }

    public String getId() {
        return id;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;

        public Builder withId (String id) {
            this.id = id;
            return this;
        }

        public GetAllExpensesRequest build() {
            GetAllExpensesRequest request = new GetAllExpensesRequest();
            request.id = this.id;
            return request;
        }
    }

}

