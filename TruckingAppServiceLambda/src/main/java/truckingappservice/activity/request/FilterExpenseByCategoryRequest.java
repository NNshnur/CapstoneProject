package truckingappservice.activity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import truckingappservice.models.Category;

@JsonDeserialize(builder = FilterExpenseByCategoryRequest.Builder.class)
public class FilterExpenseByCategoryRequest {
        private final Category category;
        private final String id;

        public FilterExpenseByCategoryRequest(Category category, String id) {
            this.category = category;
            this.id = id;
        }
    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public static Builder builder() {
            return new Builder();
        }

        @JsonPOJOBuilder
        public static class Builder {
            private Category category;
            private String id;

            public Builder withCategory (Category category) {
                this.category = category;
                return this;
            }

            public Builder withId (String id) {
                this.id = id;
                return this;
            }

            public FilterExpenseByCategoryRequest build() {
                FilterExpenseByCategoryRequest request = new FilterExpenseByCategoryRequest(category, id);
                return request;
            }
        }
    }
