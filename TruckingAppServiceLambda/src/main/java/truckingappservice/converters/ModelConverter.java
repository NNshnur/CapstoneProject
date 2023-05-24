package truckingappservice.converters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

