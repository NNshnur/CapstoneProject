package truckingappservice.converters;

import truckingappservice.dynamodb.models.Expense;
import truckingappservice.models.ExpenseModel;

public class ModelConverter {


    /**
     * Converts a provided Expenses into a ExpenseModel representation.

     * @return the converted EventModel
     */

    public ExpenseModel toExpenseModel(Expense expense){
        return ExpenseModel.builder()
                .withCategory(expense.getCategory())
                .withAmount(expense.getAmount())
                .withExpenseId(expense.getExpenseId())
                .withDate(expense.getDate())
                .withPaymentType(expense.getPaymentType())
                .withTruckId(expense.getTruckId())
                .withVendorName(expense.getVendorName())
                .build();
    }

}

