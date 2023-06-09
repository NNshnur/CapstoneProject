package truckingappservice.models;

import java.util.List;
import java.util.Objects;

public class ProfileModel {
    private String profileId; // that is emailAddress
    private String companyName;
    private String firstName;
    private String lastName;
    private List<String> truckId;

    private double startingBalance;

    public ProfileModel (String profileId, String companyName, String firstName, String lastName,List<String> truckId, double startingBalance) {
        this.profileId = profileId;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.truckId = truckId;
        this.startingBalance = startingBalance;
    }

    public String getProfileId() {
        return profileId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileModel that = (ProfileModel) o;
        return Double.compare(that.startingBalance, startingBalance) == 0 && Objects.equals(profileId, that.profileId) && Objects.equals(companyName, that.companyName) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(truckId, that.truckId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, companyName, firstName, lastName, truckId, startingBalance);
    }

    public static ProfileModel.Builder builder() {
        return new ProfileModel.Builder();
    }

    public static class Builder {
        private String userId; // that is emailAddress
        private String companyName;
        private String firstName;
        private String lastName;
        private List<String> truckId;
        private double startingBalance;

        public Builder withProfileId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }


        public Builder withTruckId(List<String> truckId) {
            this.truckId = truckId;
            return this;
        }

        public Builder withStartingBalance(double startingBalance) {
            this.startingBalance = startingBalance;
            return this;
        }

        public ProfileModel build() {
            return new ProfileModel(userId, companyName,firstName, lastName, truckId, startingBalance);
        }
    }
}


