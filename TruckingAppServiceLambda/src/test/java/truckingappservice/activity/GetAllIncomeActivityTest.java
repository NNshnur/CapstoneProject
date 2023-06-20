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

import truckingappservice.activity.request.GetAllIncomeRequest;
import truckingappservice.activity.results.GetAllIncomeResult;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.dynamodb.models.Profile;
public class GetAllIncomeActivityTest {
    @Mock
    private ProfileDao profileDao;

    @Mock
    private IncomeDao incomeDao;

    private GetAllIncomeActivity getAllIncomeActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getAllIncomeActivity = new GetAllIncomeActivity(incomeDao, profileDao);
    }

    @Test
    public void handleRequest_getsAllIncome() {
        // GIVEN
        String profileId = "profileId";
        String truckId = "truckId";

        GetAllIncomeRequest request = GetAllIncomeRequest.builder()
                .withId(profileId)
                .build();

        Profile profile = new Profile();
        profile.setId(profileId);
        profile.setTruckId(Collections.singletonList(truckId));

        List<Income> expectedIncomeList = new ArrayList<>();
        expectedIncomeList.add(new Income());
        expectedIncomeList.add(new Income());

        when(profileDao.getProfile(profileId)).thenReturn(profile);
        when(incomeDao.getAllIncome(Collections.singletonList(truckId))).thenReturn(expectedIncomeList);

        // WHEN
        GetAllIncomeResult result = getAllIncomeActivity.handleRequest(request);

        // THEN
        verify(profileDao).getProfile(profileId);
        verify(incomeDao).getAllIncome(Collections.singletonList(truckId));

        assertNotNull(result.getAllIncomeList());
        assertEquals(expectedIncomeList, result.getAllIncomeList());
    }
}

