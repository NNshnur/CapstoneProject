package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.request.DeleteExpenseRequest;
import truckingappservice.activity.results.DeleteExpenseResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.exceptions.ExpenseNotFoundException;

public class DeleteExpenseActivityTest {
    @Mock
    private ExpenseDao expenseDao;

    private DeleteExpenseActivity deleteExpenseActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteExpenseActivity = new DeleteExpenseActivity(expenseDao);
    }

    @Test
    public void handleRequest_deletesExpenseEntry() {
        // GIVEN
        String expectedExpenseId = "expectedExpenseId";
        DeleteExpenseRequest request = new DeleteExpenseRequest(expectedExpenseId);

        Expense existingExpense = new Expense();
        existingExpense.setExpenseId(expectedExpenseId);

        List<String> deletedExpenseList = new ArrayList<>();
        deletedExpenseList.add(expectedExpenseId);

        when(expenseDao.getExpense(expectedExpenseId)).thenReturn(existingExpense);
        when(expenseDao.deleteExpense(expectedExpenseId)).thenReturn(deletedExpenseList);

        // WHEN
        DeleteExpenseResult result = deleteExpenseActivity.handleRequest(request);

        // THEN
        verify(expenseDao).getExpense(expectedExpenseId);
        verify(expenseDao).deleteExpense(expectedExpenseId);

        assertNotNull(result.getExpenseList());
        assertFalse(result.getExpenseList().isEmpty());
        assertEquals(1, result.getExpenseList().size());
        assertEquals(expectedExpenseId, result.getExpenseList().get(0));
    }

    @Test
    public void handleRequest_throwsExpenseNotFoundException() {
        // GIVEN
        String nonExistingExpenseId = "nonExistingExpenseId";
        DeleteExpenseRequest request = new DeleteExpenseRequest(nonExistingExpenseId);

        when(expenseDao.getExpense(nonExistingExpenseId)).thenReturn(null);

        // WHEN & THEN
        assertThrows(ExpenseNotFoundException.class, () -> {
            deleteExpenseActivity.handleRequest(request);
        });

        verify(expenseDao).getExpense(nonExistingExpenseId);
        verifyNoMoreInteractions(expenseDao);
    }
}


