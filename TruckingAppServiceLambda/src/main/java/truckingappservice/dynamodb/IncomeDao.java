package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.exceptions.ExpenseNotFoundException;
import truckingappservice.exceptions.IncomeNotFoundException;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;
import truckingappservice.models.Category;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class IncomeDao {
    private final Logger log = LogManager.getLogger();
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates an EventDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public IncomeDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Income getIncome(String incomeId) {

        Income income = this.dynamoDbMapper.load(Income.class, incomeId);


        if (income == null) {
            metricsPublisher.addCount(MetricsConstants.GETINCOME_INCOMENOTFOUND_COUNT, 1);
            throw new IncomeNotFoundException("Could not find event with id " + incomeId);
        }

        metricsPublisher.addCount(MetricsConstants.GETINCOME_INCOMENOTFOUND_COUNT, 0);
        return income;
    }


    public List<Income> getAllIncome(List<String> truckIds) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Income> incomeList = dynamoDbMapper.scan(Income.class, scanExpression);

        // Filter expenses based on the truck IDs and email address
        List<Income> filteredExpenses = new ArrayList<>();
        for (Income income : incomeList) {
            if (truckIds.contains(income.getTruckId())) {
                filteredExpenses.add(income);
            }
        }

        return filteredExpenses;
    }

    public Income saveIncome(boolean isNew, String incomeId, String truckId, String date, double deadHeadMiles,
        double loadedMiles, double totalMiles, double grossIncome, double ratePerMile) {

        Income income = new Income();

        if (isNew) {
            income.setIncomeId(income.generateId());
            income.setTruckId(truckId);
            income.setDate(date);
            income.setDeadHeadMiles(deadHeadMiles);
            income.setLoadedMiles(loadedMiles);
            income.setTotalMiles(loadedMiles + deadHeadMiles);
            income.setGrossIncome(grossIncome);
            income.setRatePerMile(grossIncome/totalMiles);


        } else {

            if (incomeId != null & !incomeId.isEmpty()) {
                income.setIncomeId(incomeId);
            }
            if (truckId != null && !truckId.isEmpty()) {
                income.setTruckId(truckId);
            }

            if (date != null && !date.isEmpty()) {
                income.setDate(date);
            }

            if (deadHeadMiles != 0) {
                income.setDeadHeadMiles(deadHeadMiles);
            }

            if (loadedMiles != 0) {
                income.setLoadedMiles(loadedMiles);
            }
            if (totalMiles != 0) {
                income.setTotalMiles(loadedMiles + deadHeadMiles);
            }

            if (grossIncome != 0) {
                income.setGrossIncome(grossIncome);
            }

            if (ratePerMile != 0) {
                income.setRatePerMile(grossIncome/totalMiles);
            }

        }
        this.dynamoDbMapper.save(income);

        return income;
    }




    public Income deleteIncome(String incomeId) {

        Income incomeToRemove;
        try {
            incomeToRemove = getIncome(incomeId);
        } catch (IncomeNotFoundException e) {
            throw new IncomeNotFoundException("Error:The income ID is not found", e);
        }
        this.dynamoDbMapper.delete(incomeToRemove);
        return incomeToRemove;
    }

}











