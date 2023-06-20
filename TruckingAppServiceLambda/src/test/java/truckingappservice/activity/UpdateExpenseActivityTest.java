package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.request.UpdateExpenseRequest;
import truckingappservice.activity.results.UpdateExpenseResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.metrics.MetricsPublisher;
import truckingappservice.models.Category;
import truckingappservice.models.ExpenseModel;

public class UpdateExpenseActivityTest {
    @Mock
    private ExpenseDao expenseDao;

    private UpdateExpenseActivity updateExpenseActivity;
    private MetricsPublisher metricsPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateExpenseActivity = new UpdateExpenseActivity(expenseDao, metricsPublisher);
    }

    @Test
    public void handleRequest_updatesExpense() {
        // GIVEN
        String expenseId = "expenseId";
        String truckId = "truckId";
        String vendorName = "vendorName";
        Category category = Category.LODGING;
        String date = "2023-06-20";
        double amount = 100.0;
        String paymentType = "cash";

        UpdateExpenseRequest request = UpdateExpenseRequest.builder()
                .withExpenseId(expenseId)
                .withTruckId(truckId)
                .withVendorName(vendorName)
                .withCategory(category)
                .withDate(date)
                .withAmount(amount)
                .withPaymentType(paymentType)
                .build();

        Expense updatedExpense = new Expense();
        updatedExpense.setExpenseId(expenseId);
        updatedExpense.setTruckId(truckId);
        updatedExpense.setVendorName(vendorName);
        updatedExpense.setCategory(category);
        updatedExpense.setDate(date);
        updatedExpense.setAmount(amount);
        updatedExpense.setPaymentType(paymentType);

        when(expenseDao.saveExpense(false, expenseId, truckId, vendorName, category, date, amount, paymentType))
                .thenReturn(updatedExpense);

        ExpenseModel expectedExpenseModel = new ExpenseModel(expenseId, truckId, vendorName, category, date, amount, paymentType);

        // WHEN
        UpdateExpenseResult result = updateExpenseActivity.handleRequest(request);

        // THEN
        verify(expenseDao).saveExpense(false, expenseId, truckId, vendorName, category, date, amount, paymentType);

        assertNotNull(result.getExpenseModel());
        assertEquals(expectedExpenseModel, result.getExpenseModel());
    }
}
