package truckingappservice.models;

import java.util.Objects;

public class ExpenseModel {
    private String expenseId;
    private String truckId;
    private String vendorName;
    private Category category;
    private String date;
    private double amount;
    private String paymentType;

    public ExpenseModel(String expenseId, String truckId, String vendorName, Category category, String date, double amount, String paymentType) {
        this.expenseId = expenseId;
        this.truckId = truckId;
        this.vendorName = vendorName;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.paymentType = paymentType;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseModel that = (ExpenseModel) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(expenseId, that.expenseId) && Objects.equals(truckId, that.truckId) && Objects.equals(vendorName, that.vendorName) && category == that.category && Objects.equals(date, that.date) && paymentType == that.paymentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, truckId, vendorName, category, date, amount, paymentType);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String expenseId;
        private String truckId;
        private String vendorName;
        private Category category;
        private String date;
        private double amount;
        private String paymentType;


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


        public ExpenseModel build() {
            return new ExpenseModel(expenseId, truckId, vendorName, category, date, amount, paymentType);

        }
    }
}

