package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.request.UpdateIncomeRequest;
import truckingappservice.activity.results.UpdateIncomeResult;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.metrics.MetricsPublisher;
import truckingappservice.models.IncomeModel;

public class UpdateIncomeActivityTest {
    @Mock
    private IncomeDao incomeDao;

    private UpdateIncomeActivity updateIncomeActivity;
    private MetricsPublisher metricsPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateIncomeActivity = new UpdateIncomeActivity(incomeDao, metricsPublisher);
    }

    @Test
    public void handleRequest_updatesIncome() {
        // GIVEN
        String incomeId = "incomeId";
        String truckId = "truckId";
        String date = "2023-06-20";
        double deadHeadMiles = 100.0;
        double loadedMiles = 200.0;
        double grossIncome = 500.0;

        UpdateIncomeRequest request = UpdateIncomeRequest.builder()
                .withIncomeId(incomeId)
                .withTruckId(truckId)
                .withDate(date)
                .withDeadHeadMiles(deadHeadMiles)
                .withLoadedMiles(loadedMiles)
                .withGrossIncome(grossIncome)
                .build();

        Income updatedIncome = new Income();
        updatedIncome.setIncomeId(incomeId);
        updatedIncome.setTruckId(truckId);
        updatedIncome.setDate(date);
        updatedIncome.setDeadHeadMiles(deadHeadMiles);
        updatedIncome.setLoadedMiles(loadedMiles);
        updatedIncome.setTotalMiles(loadedMiles + deadHeadMiles);
        updatedIncome.setGrossIncome(grossIncome);
        updatedIncome.setRatePerMile(grossIncome / (loadedMiles + deadHeadMiles));

        when(incomeDao.saveIncome(
                false, incomeId, truckId, date, deadHeadMiles, loadedMiles, loadedMiles + deadHeadMiles, grossIncome, grossIncome / (loadedMiles + deadHeadMiles)))
                .thenReturn(updatedIncome);

        IncomeModel expectedIncomeModel = new IncomeModel(
                incomeId, truckId, date, deadHeadMiles, loadedMiles, loadedMiles + deadHeadMiles, grossIncome, grossIncome / (loadedMiles + deadHeadMiles));

        // WHEN
        UpdateIncomeResult result = updateIncomeActivity.handleRequest(request);

        // THEN
        double expectedRatePerMile = grossIncome / (loadedMiles + deadHeadMiles);
        double delta = 0.01; // Adjust the delta value as needed
        verify(incomeDao).saveIncome(
                false, incomeId, truckId, date, deadHeadMiles, loadedMiles, loadedMiles + deadHeadMiles, grossIncome, grossIncome / (loadedMiles + deadHeadMiles));
        assertEquals(expectedIncomeModel, result.getIncomeModel());
    }
}