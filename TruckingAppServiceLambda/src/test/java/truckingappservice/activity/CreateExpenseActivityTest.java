package truckingappservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import truckingappservice.activity.request.CreateExpenseRequest;
import truckingappservice.activity.results.CreateExpenseResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.models.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateExpenseActivityTest {
    @Mock
    private ExpenseDao expenseDao;

    private CreateExpenseActivity createExpenseActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createExpenseActivity = new CreateExpenseActivity(expenseDao);
    }

    @Test
    public void handleRequest_createsAndSavesExpenseEntry() {
        // GIVEN
        String expectedTruckId = "expectedTruckId";
        String expectedVendorName = "expectedVendorName";
        Category expectedCategory = Category.FUEL;
        String expectedDate = "expectedDate";
        double expectedAmount = 600;
        String expectedPaymentType = "expectedPaymentType";

        CreateExpenseRequest request = CreateExpenseRequest.builder()
                .withTruckId(expectedTruckId)
                .withVendorName(expectedVendorName)
                .withCategory(expectedCategory)
                .withDate(expectedDate)
                .withAmount(expectedAmount)
                .withPaymentType(expectedPaymentType)
                .build();

        Expense savedExpense = new Expense();
        savedExpense.setExpenseId("expectedExpenseId");
        savedExpense.setTruckId(expectedTruckId);
        savedExpense.setVendorName(expectedVendorName);
        savedExpense.setCategory(expectedCategory);
        savedExpense.setDate(expectedDate);
        savedExpense.setAmount(expectedAmount);
        savedExpense.setPaymentType(expectedPaymentType);

        when(expenseDao.saveExpense(true, null, request.getTruckId(), request.getVendorName(),
                request.getCategory(), request.getDate(), request.getAmount(), request.getPaymentType()))
                .thenReturn(savedExpense);

        // WHEN
        CreateExpenseResult result = createExpenseActivity.handleRequest(request);

        // THEN
        verify(expenseDao).saveExpense(true, null, request.getTruckId(), request.getVendorName(),
                request.getCategory(), request.getDate(), request.getAmount(), request.getPaymentType());

        assertNotNull(result.getExpense().getExpenseId());
        assertEquals(savedExpense.getTruckId(), result.getExpense().getTruckId());
        assertEquals(savedExpense.getVendorName(), result.getExpense().getVendorName());
        assertEquals(savedExpense.getCategory(), result.getExpense().getCategory());
        assertEquals(savedExpense.getDate(), result.getExpense().getDate());
        assertEquals(savedExpense.getAmount(), result.getExpense().getAmount());
        assertEquals(savedExpense.getPaymentType(), result.getExpense().getPaymentType());
    }
}
