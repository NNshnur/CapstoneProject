package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.UpdateExpenseRequest;
import truckingappservice.activity.results.UpdateExpenseResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;

import javax.inject.Inject;

public class UpdateExpenseActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public UpdateExpenseActivity(ExpenseDao expenseDao, MetricsPublisher metricsPublisher) {
        this.expenseDao = expenseDao;
        this.metricsPublisher = metricsPublisher;
    }


    public UpdateExpenseResult handleRequest(final UpdateExpenseRequest updateExpenseRequest) {

        Expense updateExpense = expenseDao.saveExpense(false, updateExpenseRequest.getExpenseId(), updateExpenseRequest.getTruckId(),
                updateExpenseRequest.getVendorName(), updateExpenseRequest.getCategory(), updateExpenseRequest.getDate(),
        updateExpenseRequest.getAmount(), updateExpenseRequest.getPaymentType());

        return UpdateExpenseResult.builder()
                .withExpense(new ModelConverter().toExpenseModel(updateExpense))
                .build();
    }


    private void publishExceptionMetrics(final boolean isInvalidAttributeValue,
                                         final boolean isInvalidAttributeChange) {

        metricsPublisher.addCount(MetricsConstants.UPDATEEXPENSE_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
        metricsPublisher.addCount(MetricsConstants.UPDATEEXPENSE_INVALIDATTRIBUTECHANGE_COUNT,
                isInvalidAttributeChange ? 1 : 0);
    }
}



