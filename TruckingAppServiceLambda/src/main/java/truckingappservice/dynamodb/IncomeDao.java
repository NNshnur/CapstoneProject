package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.exceptions.IncomeNotFoundException;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;
import java.text.DecimalFormat;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        List<Income> filteredIncome = new ArrayList<>();
        for (Income income : incomeList) {
            if (truckIds.contains(income.getTruckId())) {
                filteredIncome.add(income);
            }
        }
        Collections.sort(filteredIncome, new Comparator<Income>() {
            @Override
            public int compare(Income incomeOne, Income incomeTwo) {
                return incomeOne.getDate().compareTo(incomeTwo.getDate());
            }
        });

        return filteredIncome;
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
            income.setRatePerMile(Double.parseDouble(new DecimalFormat("0.00").format(grossIncome / totalMiles)));


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
                income.setRatePerMile(Double.parseDouble(new DecimalFormat("0.00").format(grossIncome / totalMiles)));
            }


        }
        this.dynamoDbMapper.save(income);

        return income;
    }


    public List<String> deleteIncome(String incomeId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Income> incomeList = dynamoDbMapper.scan(Income.class, scanExpression);

        Income incomeToRemove = null;

        for (Income income : incomeList) {
            if (income.getIncomeId().equals(incomeId)) {
                incomeToRemove = income;
                break;
            }
        }

        if (incomeToRemove != null) {
            dynamoDbMapper.delete(incomeToRemove);
        }

        // Remove the deleted expense from the list
        incomeList.remove(incomeToRemove);

        List<String> stringList = new ArrayList<>();
        for (Income income : incomeList) {
            stringList.add(income.toString());
        }
        return stringList;
    }

}













