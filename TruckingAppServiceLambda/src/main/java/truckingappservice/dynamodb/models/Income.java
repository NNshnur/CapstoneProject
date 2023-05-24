package truckingappservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.LocalDate;
@DynamoDBTable(tableName = "incomes")
public class Income {
    private String incomeId;
    private String truckId;
    private LocalDate date;
    private double deadHeadMiles;
    private double loadedMiles;
    private double totalMiles;
    private double grossIncome;
    private double ratePerMile;
    @DynamoDBHashKey(attributeName = "incomeId")
    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }
    @DynamoDBRangeKey(attributeName = "truckId")
    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }
    @DynamoDBAttribute(attributeName = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
        return totalMiles;
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
        return ratePerMile;
    }

    public void setRatePerMile(double ratePerMile) {
        this.ratePerMile = ratePerMile;
    }
}
