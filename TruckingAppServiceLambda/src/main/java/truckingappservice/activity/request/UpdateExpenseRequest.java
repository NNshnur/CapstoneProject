package truckingappservice.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import truckingappservice.models.Category;

import java.util.Set;

@JsonDeserialize(builder = UpdateExpenseRequest.Builder.class)
public class UpdateExpenseRequest {
    private final String profileId;
    private final String expenseId;
    private final String truckId;
    private final String vendorName;
    private final Category category;
    private final String date;
    private final double amount;
    private final String paymentType;

    private UpdateExpenseRequest(String expenseId, String profileId,String truckId, String vendorName, Category category, String date, double amount, String paymentType) {
    this.profileId = profileId;
    this.expenseId = expenseId;
    this.truckId = truckId;
    this.vendorName = vendorName;
    this.category = category;
    this.date = date;
    this.amount = amount;
    this.paymentType = paymentType;

    }

    public String getProfileId() {
        return profileId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getTruckId() {
        return truckId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public Category getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    @Override
    public String toString() {
        return "UpdateExpenseRequest{" +
                "profileId='" + profileId + '\'' +
                ", expenseId='" + expenseId + '\'' +
                ", truckId='" + truckId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", category=" + category +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String profileId;
        private String expenseId;
        private String truckId;
        private String vendorName;
        private Category category;
        private String date;
        private double amount;
        private String paymentType;

        public Builder withProfileId(String profileId) {
            this.profileId = profileId;
            return this;
        }
        public Builder withExpenseId(String expenseId) {
            this.expenseId = expenseId;
            return this;
        }

        public Builder withTruckId(String truckId) {
            this.truckId = truckId;
            return this;
        }

        public Builder withVendorName(String vendorName) {
            this.vendorName = vendorName;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder withPaymentType(String paymentType) {
            this.paymentType = paymentType;
            return this;
        }


        public UpdateExpenseRequest build() {
            return new UpdateExpenseRequest( expenseId, profileId, truckId, vendorName, category, date, amount, paymentType);

        }
    }
}
