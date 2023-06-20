package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.CreateIncomeRequest;
import truckingappservice.activity.results.CreateIncomeResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.models.IncomeModel;

import javax.inject.Inject;

public class CreateIncomeActivity {
    private final Logger log = LogManager.getLogger();
    private final IncomeDao incomeDao;


    @Inject
    public CreateIncomeActivity(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }


    public CreateIncomeResult handleRequest(final CreateIncomeRequest createIncomeRequest) {

        Income newIncome = incomeDao.saveIncome(true, null, createIncomeRequest.getTruckId(),
                createIncomeRequest.getDate(), createIncomeRequest.getDeadHeadMiles(), createIncomeRequest.getLoadedMiles(),
                createIncomeRequest.getTotalMiles(), createIncomeRequest.getGrossIncome(), createIncomeRequest.getRatePerMile());


        IncomeModel incomeModel = new ModelConverter().toIncomeModel(newIncome);
        return CreateIncomeResult.builder()
                .withIncome(incomeModel)
                .build();
    }
}

