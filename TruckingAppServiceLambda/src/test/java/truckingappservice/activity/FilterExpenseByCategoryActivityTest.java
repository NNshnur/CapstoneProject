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

import truckingappservice.activity.request.FilterExpenseByCategoryRequest;
import truckingappservice.activity.results.FilterExpenseByCategoryResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.models.Category;

public class FilterExpenseByCategoryActivityTest {

    @Mock
    private ProfileDao profileDao;

    @Mock
    private ExpenseDao expenseDao;

    private FilterExpenseByCategoryActivity filterExpenseByCategoryActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filterExpenseByCategoryActivity = new FilterExpenseByCategoryActivity(expenseDao, profileDao);
    }

    @Test
    public void handleRequest_filtersExpensesByCategory() {
        // GIVEN
        String profileId = "profileId";
        String truckId = "truckId";
        Category category = Category.FUEL;

        FilterExpenseByCategoryRequest request = new FilterExpenseByCategoryRequest(category, profileId);

        Profile profile = new Profile();
        profile.setId(profileId);
        profile.setTruckId(Collections.singletonList(truckId));

        List<Expense> expectedExpenseList = new ArrayList<>();
        expectedExpenseList.add(new Expense());
        expectedExpenseList.add(new Expense());

        when(profileDao.getProfile(profileId)).thenReturn(profile);
        when(expenseDao.getExpensesByCategory(Collections.singletonList(truckId), category)).thenReturn(expectedExpenseList);

        // WHEN
        FilterExpenseByCategoryResult result = filterExpenseByCategoryActivity.handleRequest(request);

        // THEN
        verify(profileDao).getProfile(profileId);
        verify(expenseDao).getExpensesByCategory(Collections.singletonList(truckId), category);

        assertNotNull(result.getExpenseListByCategory());
        assertEquals(expectedExpenseList, result.getExpenseListByCategory());
    }

    @Test
    public void handleRequest_returnsEmptyListWhenProfileNotFound() {
        // GIVEN
        String nonExistingProfileId = "nonExistingProfileId";
        Category category = Category.FUEL;

        FilterExpenseByCategoryRequest request = new FilterExpenseByCategoryRequest(category, nonExistingProfileId);

        when(profileDao.getProfile(nonExistingProfileId)).thenReturn(null);

        // WHEN
        FilterExpenseByCategoryResult result = filterExpenseByCategoryActivity.handleRequest(request);

        // THEN
        verify(profileDao).getProfile(nonExistingProfileId);
        verifyNoMoreInteractions(expenseDao);

        assertNotNull(result.getExpenseListByCategory());
        assertTrue(result.getExpenseListByCategory().isEmpty());
    }
}

