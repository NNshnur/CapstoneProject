package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.metrics.MetricsPublisher;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class IncomeDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private MetricsPublisher metricsPublisher;

    private IncomeDao incomeDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        incomeDao = new IncomeDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    void getIncome_withExistingIncomeId_returnsIncome() {
        // GIVEN
        String incomeId = "incomeId";
        Income expectedIncome = new Income();
        expectedIncome.setIncomeId(incomeId);
        when(dynamoDBMapper.load(Income.class, incomeId)).thenReturn(expectedIncome);

        // WHEN
        Income result = incomeDao.getIncome(incomeId);

        // THEN
        verify(dynamoDBMapper).load(Income.class, incomeId);
        assertEquals(expectedIncome, result, "Expected to receive the Income object");
    }

    @Test
    void getAllIncome_returnsFilteredAndSortedIncomeList() {
        // GIVEN
        List<Income> incomeList = new ArrayList<>();
        Income incomeOne = new Income();
        incomeOne.setIncomeId("incomeId1");
        incomeOne.setTruckId("45678");
        incomeOne.setDate("06-06-2023");
        incomeList.add(incomeOne);

        Income incomeTwo = new Income();
        incomeTwo.setIncomeId("incomeId2");
        incomeTwo.setTruckId("45678");
        incomeTwo.setDate("06-07-2023");
        incomeList.add(incomeTwo);

        List<String> truckIds = Arrays.asList("45678");

        PaginatedScanList<Income> mockScanList = mock(PaginatedScanList.class);
        when(mockScanList.iterator()).thenReturn(incomeList.iterator());

        when(dynamoDBMapper.scan(eq(Income.class), any(DynamoDBScanExpression.class)))
                .thenReturn(mockScanList);

        // WHEN
        List<Income> result = incomeDao.getAllIncome(truckIds);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("incomeId1", result.get(0).getIncomeId());
        assertEquals("incomeId2", result.get(1).getIncomeId());
        verify(dynamoDBMapper).scan(eq(Income.class), any(DynamoDBScanExpression.class));
    }
    @Test
    void saveIncome_withNewIncome_returnsSavedIncome() {
        // GIVEN
        boolean isNew = true;
        String incomeId = null;
        String truckId = "12345";
        String date = "2023-06-01";
        double deadHeadMiles = 50.0;
        double loadedMiles = 250.0;
        double totalMiles = deadHeadMiles + loadedMiles;
        double grossIncome = 1500.0;
        double ratePerMile = Double.parseDouble(new DecimalFormat("0.00").format(grossIncome / totalMiles));

        // Create an instance of your IncomeDao class
        IncomeDao incomeDao = new IncomeDao(dynamoDBMapper, null);

        // WHEN
        Income result = incomeDao.saveIncome(isNew, incomeId, truckId, date, deadHeadMiles, loadedMiles, totalMiles, grossIncome, ratePerMile);

        // THEN
        assertNotNull(result);
        assertNotNull(result.getIncomeId());
        assertEquals(truckId, result.getTruckId());
        assertEquals(date, result.getDate());
        assertEquals(deadHeadMiles, result.getDeadHeadMiles());
        assertEquals(loadedMiles, result.getLoadedMiles());
        assertEquals(totalMiles, result.getTotalMiles());
        assertEquals(grossIncome, result.getGrossIncome());
        assertEquals(ratePerMile, result.getRatePerMile());
        verify(dynamoDBMapper).save(eq(result));
    }

}

