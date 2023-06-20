package truckingappservice.activity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.request.DeleteIncomeRequest;
import truckingappservice.activity.results.DeleteIncomeResult;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.exceptions.IncomeNotFoundException;

public class DeleteIncomeActivityTest {
    @Mock
    private IncomeDao incomeDao;

    private DeleteIncomeActivity deleteIncomeActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteIncomeActivity = new DeleteIncomeActivity(incomeDao);
    }

    @Test
    public void handleRequest_deletesIncomeEntry() {
        // GIVEN
        String expectedIncomeId = "expectedIncomeId";
        DeleteIncomeRequest request = new DeleteIncomeRequest(expectedIncomeId);

        Income existingIncome = new Income();
        existingIncome.setIncomeId(expectedIncomeId);

        List<String> deletedIncomeList = new ArrayList<>();
        deletedIncomeList.add(expectedIncomeId);

        when(incomeDao.getIncome(expectedIncomeId)).thenReturn(existingIncome);
        when(incomeDao.deleteIncome(expectedIncomeId)).thenReturn(deletedIncomeList);

        // WHEN
        DeleteIncomeResult result = deleteIncomeActivity.handleRequest(request);

        // THEN
        verify(incomeDao).getIncome(expectedIncomeId);
        verify(incomeDao).deleteIncome(expectedIncomeId);

        assertNotNull(result.getIncomeList());
        assertFalse(result.getIncomeList().isEmpty());
        assertEquals(1, result.getIncomeList().size());
        assertEquals(expectedIncomeId, result.getIncomeList().get(0));
    }

    @Test
    public void handleRequest_throwsIncomeNotFoundException() {
        // GIVEN
        String nonExistingIncomeId = "nonExistingIncomeId";
        DeleteIncomeRequest request = new DeleteIncomeRequest(nonExistingIncomeId);

        when(incomeDao.getIncome(nonExistingIncomeId)).thenReturn(null);

        // WHEN & THEN
        assertThrows(IncomeNotFoundException.class, () -> {
            deleteIncomeActivity.handleRequest(request);
        });

        verify(incomeDao).getIncome(nonExistingIncomeId);
        verifyNoMoreInteractions(incomeDao);
    }
}
