package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateIncomeRequest.Builder.class)
public class CreateIncomeRequest {

private final String truckId;
private final String date;
private final double deadHeadMiles;
private final double loadedMiles;
private final double totalMiles;
private final double grossIncome;
private final double ratePerMile;

private CreateIncomeRequest(String truckId, String date, double deadHeadMiles, double loadedMiles, double totalMiles, double grossIncome, double ratePerMile) {
this.truckId = truckId;
this.date = date;
this.deadHeadMiles = deadHeadMiles;
this.loadedMiles = loadedMiles;
this.totalMiles = totalMiles;
this.grossIncome = grossIncome;
this.ratePerMile = ratePerMile;
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
  return "CreateIncomeRequest{" +
         "truckId='" + truckId + '\'' +
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
            private String truckId;
            private String date;
            private double deadHeadMiles;
            private double loadedMiles;
            private double grossIncome;

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

            public CreateIncomeRequest build() {
                double totalMiles = deadHeadMiles + loadedMiles;
                double ratePerMile = grossIncome / totalMiles;
                return new CreateIncomeRequest(truckId, date, deadHeadMiles, loadedMiles, totalMiles, grossIncome, ratePerMile);

            }
        }
    }
