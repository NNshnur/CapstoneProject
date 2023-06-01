package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.CreateExpenseRequest;
import truckingappservice.activity.results.CreateExpenseResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.models.ExpenseModel;

import javax.inject.Inject;

public class CreateExpenseActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;


    @Inject
    public CreateExpenseActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }


    public CreateExpenseResult handleRequest(final CreateExpenseRequest createExpenseRequest) {
        log.info("Received CreateExpenseRequest {}", createExpenseRequest);

        Expense newExpense = expenseDao.saveExpense(true, null, createExpenseRequest.getTruckId(),
                createExpenseRequest.getVendorName(), createExpenseRequest.getCategory(), createExpenseRequest.getDate(),
                createExpenseRequest.getAmount(), createExpenseRequest.getPaymentType());


        ExpenseModel expenseModel = new ModelConverter().toExpenseModel(newExpense);
        return CreateExpenseResult.builder()
                .withExpense(expenseModel)
                .build();
    }
}

