package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
@JsonDeserialize(builder = UpdateProfileRequest.Builder.class)
public class UpdateProfileRequest {
    private final String firstName;
    private final String lastName;
    private final String companyName;
    private final List<String> truckId;
    private final String profileId;

    private final double startingBalance;

    private UpdateProfileRequest(String firstName, String lastName, String companyName, List<String> truckId, String profileId, double startingBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.truckId = truckId;
        this.profileId = profileId;
        this.startingBalance = startingBalance;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<String> getTruckId() {
        return truckId;
    }

    public String getProfileId() {
        return profileId;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    @Override
    public String toString() {
        return "UpdateProfileRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", truckId=" + truckId + '\'' +
                ", profileId='" + profileId + '\'' +
                ", startingBalance='" + startingBalance +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder{
        private String firstName;
        private String lastName;
        private String companyName;
        private List<String> truckId;
        private String profileId;
        private double startingBalance;

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public Builder withCompanyName(String companyName){
            this.companyName = companyName;
            return this;
        }
        public Builder withTruckId(List<String> truckId){
            this.truckId = truckId;
            return this;
        }
        public Builder withId(String profileId){
            this.profileId = profileId;
            return this;
        }

        public Builder withStartingBalance(double startingBalance){
            this.startingBalance = startingBalance;
            return this;
        }
        public UpdateProfileRequest build(){
            return new UpdateProfileRequest(firstName,lastName, companyName, truckId,profileId, startingBalance);
        }
    }
}


