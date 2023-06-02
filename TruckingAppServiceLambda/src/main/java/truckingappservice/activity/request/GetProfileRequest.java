package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetProfileRequest.Builder.class)
public class GetProfileRequest {
    private final String profileId;
    /**
     * Constructs a new GetProfileRequest with the given profile ID.
     *
     * @param profileId the ID of the profile to get
     */
    public GetProfileRequest(String profileId) {
        this.profileId = profileId;
    }
    /**
     * Returns the ID of the profile to get.
     *
     * @return the profile ID
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * Returns a string representation of this GetProfileRequest.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GetProfileRequest{" +
                "profileId='" + profileId + '\'' +
                '}';
    }
    /**
     * Builder class for constructing GetProfileRequest objects.
     */
    @JsonPOJOBuilder
    public static class Builder{

        private String profileId;


        public Builder withId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public GetProfileRequest build(){
            return new GetProfileRequest(profileId);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}
