package truckingappservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import truckingappservice.utils.UniqueIdGenerator;

import java.util.Objects;

@DynamoDBTable(tableName = "incomes")
public class Income {
    private String incomeId;
    private String truckId;
    private String date;
    private double deadHeadMiles;
    private double loadedMiles;
    private double totalMiles;
    private double grossIncome;
    private double ratePerMile;
    @DynamoDBHashKey(attributeName = "incomeId")
    public String getIncomeId() {
        return incomeId;
    }
    public String generateId() {
        return UniqueIdGenerator.generateUniqueId();
    }
    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }
    @DynamoDBAttribute(attributeName = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @DynamoDBAttribute(attributeName = "deadHeadMiles")
    public double getDeadHeadMiles() {
        return deadHeadMiles;
    }

    public void setDeadHeadMiles(double deadHeadMiles) {
        this.deadHeadMiles = deadHeadMiles;
    }
    @DynamoDBAttribute(attributeName = "loadedMiles")
    public double getLoadedMiles() {
        return loadedMiles;
    }

    public void setLoadedMiles(double loadedMiles) {
        this.loadedMiles = loadedMiles;
    }
    @DynamoDBAttribute(attributeName = "totalMiles")
    public double getTotalMiles() {
        return getLoadedMiles() + getDeadHeadMiles();
    }

    public void setTotalMiles(double totalMiles) {
        this.totalMiles = totalMiles;
    }
    @DynamoDBAttribute(attributeName = "grossIncome")
    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }
    @DynamoDBAttribute(attributeName = "ratePerMile")
    public double getRatePerMile() {
        return getGrossIncome() / getTotalMiles();
    }

    public void setRatePerMile(double ratePerMile) {
        this.ratePerMile = ratePerMile;
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeId='" + incomeId + '\'' +
                ", truckId='" + truckId + '\'' +
                ", date=" + date +
                ", deadHeadMiles=" + deadHeadMiles +
                ", loadedMiles=" + loadedMiles +
                ", totalMiles=" + totalMiles +
                ", grossIncome=" + grossIncome +
                ", ratePerMile=" + ratePerMile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return Double.compare(income.deadHeadMiles, deadHeadMiles) == 0 && Double.compare(income.loadedMiles, loadedMiles) == 0 && Double.compare(income.totalMiles, totalMiles) == 0 && Double.compare(income.grossIncome, grossIncome) == 0 && Double.compare(income.ratePerMile, ratePerMile) == 0 && Objects.equals(incomeId, income.incomeId) && Objects.equals(truckId, income.truckId) && Objects.equals(date, income.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeId, truckId, date, deadHeadMiles, loadedMiles, totalMiles, grossIncome, ratePerMile);
    }
}
