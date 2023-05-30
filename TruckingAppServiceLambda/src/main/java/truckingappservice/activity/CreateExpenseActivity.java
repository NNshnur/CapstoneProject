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

    /**
     * Instantiates a new CreateEventActivity object.
     *
     * @param expenseDao EventDao to access the events table.
     */
    @Inject
    public CreateExpenseActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    /**
     * This method handles the incoming request by persisting a new event
     * with the provided event name and user from the request.
     * <p>
     * It then returns the newly created event.
     * <p>
     * If the provided event name or user has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createExpenseRequest request object containing the event name and customer ID
     *                              associated with it
     * @return createEventResult result object containing the API defined {@link ExpenseModel}
     */
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

