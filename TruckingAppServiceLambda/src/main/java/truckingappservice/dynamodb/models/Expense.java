package truckingappservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import truckingappservice.models.Category;
import truckingappservice.models.PaymentType;
import truckingappservice.utils.UniqueIdGenerator;

import java.time.LocalDate;
import java.util.Objects;

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
    public String generateId() {
        return UniqueIdGenerator.generateUniqueId();
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
    @DynamoDBTypeConvertedEnum
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
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "paymentType")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "expenseId='" + expenseId + '\'' +
                ", truckId='" + truckId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", category=" + category +
                ", date=" + date +
                ", amount=" + amount +
                ", paymentType=" + paymentType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.amount, amount) == 0 && Objects.equals(expenseId, expense.expenseId) && Objects.equals(truckId, expense.truckId) && Objects.equals(vendorName, expense.vendorName) && category == expense.category && Objects.equals(date, expense.date) && paymentType == expense.paymentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, truckId, vendorName, category, date, amount, paymentType);
    }
}
