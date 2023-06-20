package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.exceptions.ExpenseNotFoundException;
import truckingappservice.metrics.MetricsPublisher;
import truckingappservice.models.Category;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ExpenseDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private MetricsPublisher metricsPublisher;

    private ExpenseDao expenseDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        expenseDao = new ExpenseDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    void getExpense_withExistingExpenseId_returnsExpense() {
        // GIVEN
        String expenseId = "expenseId";
        Expense expectedExpense = new Expense();
        expectedExpense.setExpenseId(expenseId);
        when(dynamoDBMapper.load(Expense.class, expenseId)).thenReturn(expectedExpense);

        // WHEN
        Expense result = expenseDao.getExpense(expenseId);

        // THEN
        verify(dynamoDBMapper).load(Expense.class, expenseId);
        assertEquals(expectedExpense, result, "Expected to receive the Expense object");
    }

    @Test
    void getExpense_withNonexistentExpenseId_throwsException() {
        // GIVEN
        String expenseId = "nonexistentExpenseId";
        when(dynamoDBMapper.load(Expense.class, expenseId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ExpenseNotFoundException.class, () -> expenseDao.getExpense(expenseId));

    }


    @Test
    public void getAllExpenses_returnsFilteredAndSortedExpenseList() {
        // GIVEN
        List<Expense> expenseList = new ArrayList<>();
        Expense expenseOne = new Expense();
        expenseOne.setExpenseId("expenseId1");
        expenseOne.setAmount(100.0);
        expenseOne.setCategory(Category.FUEL);
        expenseOne.setDate("06-06-2023");
        expenseOne.setTruckId("45678");
        expenseOne.setPaymentType("Credit-Card");
        expenseOne.setVendorName("Some Cool Co");
        expenseList.add(expenseOne);

        Expense expenseTwo = new Expense();
        expenseTwo.setExpenseId("expenseId2");
        expenseTwo.setAmount(200.0);
        expenseTwo.setCategory(Category.MAINTENANCE);
        expenseTwo.setDate("06-07-2023");
        expenseTwo.setTruckId("45678");
        expenseTwo.setPaymentType("Credit Card");
        expenseTwo.setVendorName("Some Another Co");
        expenseList.add(expenseTwo);

        List<String> truckIds = Arrays.asList("45678");

        PaginatedScanList<Expense> mockScanList = mock(PaginatedScanList.class);
        when(mockScanList.iterator()).thenReturn(expenseList.iterator());

        // Mock the behavior of the scan method to return the mockScanList
        when(dynamoDBMapper.scan(eq(Expense.class), any(DynamoDBScanExpression.class)))
                .thenReturn(mockScanList);

        // Create an instance of your ExpenseDao class
        ExpenseDao expenseDao = new ExpenseDao(dynamoDBMapper, null);

        // WHEN
        List<Expense> result = expenseDao.getAllExpenses(truckIds);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("expenseId1", result.get(0).getExpenseId());
        assertEquals("expenseId2", result.get(1).getExpenseId());
        verify(dynamoDBMapper).scan(eq(Expense.class), any(DynamoDBScanExpression.class));
    }

    @Test
    void saveExpense_withNewExpense_returnsSavedExpense() {
        // GIVEN
        boolean isNew = true;
        String expenseId = null;
        String truckId = "45678";
        String vendorName = "Some Vendor";
        Category category = Category.FUEL;
        String date = "06-06-2023";
        double amount = 100.0;
        String paymentType = "Credit Card";

        ExpenseDao expenseDao = new ExpenseDao(dynamoDBMapper, null);

        // WHEN
        Expense result = expenseDao.saveExpense(isNew, expenseId, truckId, vendorName, category, date, amount, paymentType);

        // THEN
        assertNotNull(result);
        assertEquals(truckId, result.getTruckId());
        assertEquals(vendorName, result.getVendorName());
        assertEquals(category, result.getCategory());
        assertEquals(date, result.getDate());
        assertEquals(amount, result.getAmount(), 0.01);
        assertEquals(paymentType, result.getPaymentType());
        assertNotNull(result.getExpenseId());
        verify(dynamoDBMapper).save(eq(result));
    }

    @Test
    void getExpensesByCategory_ShouldReturnFilteredExpenses() {
        List<Expense> expenseList = new ArrayList<>();
        Expense expenseOne = new Expense();
        expenseOne.setExpenseId("expenseId1");
        expenseOne.setAmount(100.0);
        expenseOne.setCategory(Category.FUEL);
        expenseOne.setDate("06-06-2023");
        expenseOne.setTruckId("45678");
        expenseOne.setPaymentType("Credit-Card");
        expenseOne.setVendorName("Some Cool Co");
        expenseList.add(expenseOne);

        Expense expenseTwo = new Expense();
        expenseTwo.setExpenseId("expenseId2");
        expenseTwo.setAmount(200.0);
        expenseTwo.setCategory(Category.MAINTENANCE);
        expenseTwo.setDate("06-07-2023");
        expenseTwo.setTruckId("45678");
        expenseTwo.setPaymentType("Debit Card");
        expenseTwo.setVendorName("Another Co");
        expenseList.add(expenseTwo);

        List<String> truckIds = Arrays.asList("45678");

        // Mock the behavior of expenseDao.scan() method
        PaginatedScanList<Expense> mockScanList = mock(PaginatedScanList.class);
        when(mockScanList.iterator()).thenReturn(expenseList.iterator());

        // Mock the behavior of the scan method to return the mockScanList
        when(dynamoDBMapper.scan(eq(Expense.class), any(DynamoDBScanExpression.class)))
                .thenReturn(mockScanList);

        // Define the truck IDs and category for filtering
        Category category = Category.FUEL;

        // Call the getExpensesByCategory method
        List<Expense> filteredExpenses = expenseDao.getExpensesByCategory(truckIds, category);

        // Assert that the returned list contains the expected expenses
        assertEquals(1, filteredExpenses.size(), "Incorrect number of filtered expenses");
        assertTrue(filteredExpenses.contains(expenseOne), "Expense 1 not found in filtered list");
    }
}



