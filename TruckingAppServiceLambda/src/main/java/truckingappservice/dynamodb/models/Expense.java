package truckingappservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import truckingappservice.models.Category;
import truckingappservice.models.PaymentType;

import java.time.LocalDate;
@DynamoDBTable(tableName = "expenses")
public class Expense {

    public static final String CATEGORY_INDEX = "CategoryIndex";
    private String expenseId;
    private String truckId;
    private String vendorName;
    private Category category;
    private LocalDate date;
    private double amount;
    private PaymentType paymentType;
    @DynamoDBHashKey(attributeName = "expenseId")
    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
    @DynamoDBRangeKey(attributeName = "truckId")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = CATEGORY_INDEX, attributeName = "truckId")
    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }
    @DynamoDBAttribute(attributeName = "vendorName")
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    @DynamoDBAttribute(attributeName = "category")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = CATEGORY_INDEX, attributeName = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @DynamoDBAttribute(attributeName = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    @DynamoDBAttribute(attributeName = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    @DynamoDBAttribute(attributeName = "paymentType")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
