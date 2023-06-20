package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.CreateIncomeActivity;
import truckingappservice.activity.request.CreateIncomeRequest;
import truckingappservice.activity.results.CreateIncomeResult;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.models.IncomeModel;
public class CreateIncomeActivityTest {

    @Mock
    private IncomeDao incomeDao;
    private CreateIncomeActivity createIncomeActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createIncomeActivity = new CreateIncomeActivity(incomeDao);

    }

    @Test
    public void handleRequest_createsAndSavesIncomeEntry() {
    //GIVEN
        String expectedTruckId = "expectedTruckId";
        String expectedDate = "expectedDate";
        double expectedDeadHeadMiles = 100;
        double expectedLoadedMiles = 500;
        double expectedGrossIncome = 3000;

        CreateIncomeRequest request = CreateIncomeRequest.builder()
                .withTruckId(expectedTruckId)
                .withDate(expectedDate)
                .withDeadHeadMiles(expectedDeadHeadMiles)
                .withLoadedMiles(expectedLoadedMiles)
                .withGrossIncome(expectedGrossIncome)
                .build();

        Income savedIncome = new Income();
        savedIncome.setIncomeId("expectedIncomeId");
        savedIncome.setTruckId(expectedTruckId);
        savedIncome.setDate(expectedDate);
        savedIncome.setDeadHeadMiles(expectedDeadHeadMiles);
        savedIncome.setLoadedMiles(expectedLoadedMiles);
        savedIncome.setGrossIncome(expectedGrossIncome);

        when(incomeDao.saveIncome(true, null, request.getTruckId(), request.getDate(),
                request.getDeadHeadMiles(), request.getLoadedMiles(), request.getTotalMiles(),
                request.getGrossIncome(), request.getRatePerMile())).thenReturn(savedIncome);

        // WHEN
        CreateIncomeResult result = createIncomeActivity.handleRequest(request);

        // THEN
        verify(incomeDao).saveIncome(true, null, request.getTruckId(), request.getDate(),
                request.getDeadHeadMiles(), request.getLoadedMiles(), request.getTotalMiles(),
                request.getGrossIncome(), request.getRatePerMile());

        assertNotNull(result.getIncome().getIncomeId());
        assertEquals(savedIncome.getTruckId(), result.getIncome().getTruckId());
        assertEquals(savedIncome.getDate(), result.getIncome().getDate());
        assertEquals(savedIncome.getDeadHeadMiles(), result.getIncome().getDeadHeadMiles());
        assertEquals(savedIncome.getLoadedMiles(), result.getIncome().getLoadedMiles());
        assertEquals(savedIncome.getTotalMiles(), result.getIncome().getTotalMiles());
        assertEquals(savedIncome.getGrossIncome(), result.getIncome().getGrossIncome());
        assertEquals(savedIncome.getRatePerMile(), result.getIncome().getRatePerMile());
    }

}

