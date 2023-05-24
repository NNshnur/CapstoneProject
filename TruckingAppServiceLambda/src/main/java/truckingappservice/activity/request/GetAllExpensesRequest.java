package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetAllExpensesRequest.Builder.class)
public class GetAllExpensesRequest {

    public GetAllExpensesRequest() {
    }
    /**
     * Returns a new builder for creating instances of GetAllExpensesRequest.
     *
     * @return a new builder for creating instances of GetAllExpensesRequest
     */
    public static Builder builder() {
        return new Builder();
    }
    /**
     * Builder class for creating instances of GetAllEventsRequest.
     */
    @JsonPOJOBuilder
    public static class Builder {
        /**
         * Creates a new instance of GetAllEventsRequest using the builder's current configuration.
         *
         * @return a new instance of GetAllEventsRequest
         */
        public GetAllExpensesRequest build() {
            return new GetAllExpensesRequest();
        }
    }

}

