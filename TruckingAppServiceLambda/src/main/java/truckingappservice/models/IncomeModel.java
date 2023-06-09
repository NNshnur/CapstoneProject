package truckingappservice.models;

import java.util.Objects;

public class IncomeModel {
    private String incomeId;
    private String truckId;
    private String date;
    private double deadHeadMiles;
    private double loadedMiles;
    private double totalMiles;
    private double grossIncome;
    private double ratePerMile;

    public IncomeModel(String incomeId, String truckId, String date, double deadHeadMiles, double loadedMiles, double totalMiles, double grossIncome, double ratePerMile) {
        this.incomeId = incomeId;
        this.truckId = truckId;
        this.date = date;
        this.deadHeadMiles = deadHeadMiles;
        this.loadedMiles = loadedMiles;
        this.totalMiles = totalMiles;
        this.grossIncome = grossIncome;
        this.ratePerMile = ratePerMile;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomeModel that = (IncomeModel) o;
        return Double.compare(that.deadHeadMiles, deadHeadMiles) == 0 && Double.compare(that.loadedMiles, loadedMiles) == 0 && Double.compare(that.totalMiles, totalMiles) == 0 && Double.compare(that.grossIncome, grossIncome) == 0 && Double.compare(that.ratePerMile, ratePerMile) == 0 && Objects.equals(incomeId, that.incomeId) && Objects.equals(truckId, that.truckId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeId, truckId, date, deadHeadMiles, loadedMiles, totalMiles, grossIncome, ratePerMile);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String incomeId;
        private String truckId;
        private String date;
        private double deadHeadMiles;
        private double loadedMiles;
        private double totalMiles;
        private double grossIncome;
        private double ratePerMile;


        public Builder withIncomeId(String incomeId) {
            this.incomeId = incomeId;
            return this;
        }

        public Builder withTruckId(String truckId) {
            this.truckId = truckId;
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

        public Builder withTotalMiles(double totalMiles) {
            this.totalMiles = deadHeadMiles + loadedMiles;
            return this;
        }

        public Builder withGrossIncome(double grossIncome) {
            this.grossIncome = grossIncome;
            return this;
        }

        public Builder withRatePerMile(double ratePerMile) {
            this.ratePerMile = grossIncome/totalMiles;
            return this;
        }

        public IncomeModel build() {
            return new IncomeModel(incomeId, truckId, date, deadHeadMiles,
                    loadedMiles, totalMiles, grossIncome, ratePerMile);

        }
    }
}

