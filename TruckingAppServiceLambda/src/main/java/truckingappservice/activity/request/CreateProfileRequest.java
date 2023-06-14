package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateProfileRequest.Builder.class)
public class CreateProfileRequest {
    private final String id;
    private final String companyName;
    private final String firstName;
    private final String lastName;
    private final List<String> truckId;
    private final double startingBalance;


    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getTruckId() {
        return truckId;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    @Override
    public String toString() {
        return "CreateProfileRequest{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", truckId=" + truckId +
                ", startingBalance=" + startingBalance +
                '}';
    }

    private CreateProfileRequest(String id, String companyName, String firstName, String lastName,
                                 List<String>truckId, double startingBalance){

        this.id = id;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.truckId = truckId;
        this.startingBalance = startingBalance;
    }
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder{

        private String id;
        private String companyName;
        private String firstName;
        private String lastName;
        private List<String> truckId;
        private double startingBalance;


        public Builder withId(String id){
            this.id = id;
            return this;
        }
        public Builder withCompanyName(String companyName){
            this.companyName = companyName;
            return this;
        }
        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public Builder withTruckId(List<String> truckId){
            this.truckId = truckId;
            return this;
        }

        public Builder withStartingBalance(double startingBalance){
            this.startingBalance = startingBalance;
            return this;
        }



        public CreateProfileRequest build(){
            return new CreateProfileRequest(id, companyName, firstName, lastName, truckId, startingBalance);
        }
    }
}
