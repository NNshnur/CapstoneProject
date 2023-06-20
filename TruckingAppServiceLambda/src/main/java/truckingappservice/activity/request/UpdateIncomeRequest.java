package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@JsonDeserialize(builder = UpdateIncomeRequest.Builder.class)
public class UpdateIncomeRequest {
    private final String profileId;
    private final String incomeId;
    private final String truckId;
    private final String date;
    private final double deadHeadMiles;
    private final double loadedMiles;
    private final double totalMiles;
    private final double grossIncome;
    private final double ratePerMile;

    private UpdateIncomeRequest(String profileId, String incomeId, String truckId, String date, double deadHeadMiles, double loadedMiles,
                                double totalMiles, double grossIncome, double ratePerMile) {
        this.profileId = profileId;
        this.incomeId = incomeId;
        this.truckId = truckId;
        this.date = date;
        this.deadHeadMiles = deadHeadMiles;
        this.loadedMiles = loadedMiles;
        this.totalMiles = totalMiles;
        this.grossIncome = grossIncome;
        this.ratePerMile = ratePerMile;

    }

    public String getProfileId() {
        return profileId;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public String getTruckId() {
        return truckId;
    }

    public String getDate() {
        return date;
    }

    public double getDeadHeadMiles() {
        return deadHeadMiles;
    }

    public double getLoadedMiles() {
        return loadedMiles;
    }

    public double getTotalMiles() {
        return totalMiles;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public double getRatePerMile() {
        return ratePerMile;
    }

    @Override
    public String toString() {
        return "UpdateIncomeRequest{" +
                "profileId='" + profileId + '\'' +
                ", incomeId='" + incomeId + '\'' +
                ", truckId='" + truckId + '\'' +
                ", date='" + date + '\'' +
                ", deadHeadMiles=" + deadHeadMiles +
                ", loadedMiles=" + loadedMiles +
                ", totalMiles=" + totalMiles +
                ", grossIncome=" + grossIncome +
                ", ratePerMile=" + ratePerMile +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String profileId;
        private String incomeId;
        private String truckId;
        private String date;
        private double deadHeadMiles;
        private double loadedMiles;
        private double grossIncome;

        public Builder withProfileId(String profileId) {
            this.profileId = profileId;
            return this;
        }

        public Builder withIncomeId(String incomeId) {
            this.incomeId = incomeId;
            return this;
        }

        public Builder withTruckId(String truckId) {
            this.truckId = truckId;
            return this;
        }

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public Builder withDeadHeadMiles(double deadHeadMiles) {
            this.deadHeadMiles = deadHeadMiles;
            return this;
        }

        public Builder withLoadedMiles(double loadedMiles) {
            this.loadedMiles = loadedMiles;
            return this;
        }

        public Builder withGrossIncome(double grossIncome) {
            this.grossIncome = grossIncome;
            return this;
        }

        public UpdateIncomeRequest build() {
            double totalMiles = deadHeadMiles + loadedMiles;
            double ratePerMile = grossIncome/totalMiles;
            return new UpdateIncomeRequest( profileId, incomeId, truckId, date, deadHeadMiles, loadedMiles, totalMiles, grossIncome, ratePerMile);

        }
    }
}

