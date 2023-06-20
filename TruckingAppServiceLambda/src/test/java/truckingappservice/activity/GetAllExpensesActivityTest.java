package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.request.GetAllExpensesRequest;
import truckingappservice.activity.results.GetAllExpensesResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Profile;

public class GetAllExpensesActivityTest {
    @Mock
    private ProfileDao profileDao;

    @Mock
    private ExpenseDao expenseDao;

    private GetAllExpensesActivity getAllExpensesActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getAllExpensesActivity = new GetAllExpensesActivity(expenseDao, profileDao);
    }

    @Test
    public void handleRequest_getsAllExpenses() {
        // GIVEN
        String profileId = "profileId";
        String truckId = "truckId";

        GetAllExpensesRequest request = GetAllExpensesRequest.builder()
                .withId(profileId)
                .build();

        Profile profile = new Profile();
        profile.setId(profileId);
        profile.setTruckId(Collections.singletonList(truckId));

        List<Expense> expectedExpenseList = new ArrayList<>();
        expectedExpenseList.add(new Expense());
        expectedExpenseList.add(new Expense());

        when(profileDao.getProfile(profileId)).thenReturn(profile);
        when(expenseDao.getAllExpenses(Collections.singletonList(truckId))).thenReturn(expectedExpenseList);

        // WHEN
        GetAllExpensesResult result = getAllExpensesActivity.handleRequest(request);

        // THEN
        verify(profileDao).getProfile(profileId);
        verify(expenseDao).getAllExpenses(Collections.singletonList(truckId));

        assertNotNull(result.getAllExpenseList());
        assertEquals(expectedExpenseList, result.getAllExpenseList());
    }
}
