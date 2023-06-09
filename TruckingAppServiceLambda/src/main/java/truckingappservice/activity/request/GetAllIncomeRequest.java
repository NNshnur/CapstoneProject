package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = GetAllIncomeRequest.Builder.class)
public class GetAllIncomeRequest {
    private String id;

    public GetAllIncomeRequest() {
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

        public GetAllIncomeRequest build() {
            GetAllIncomeRequest request = new GetAllIncomeRequest();
            request.id = this.id;
            return request;
        }
    }
}

