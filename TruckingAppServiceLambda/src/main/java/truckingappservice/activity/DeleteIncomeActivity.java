package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.DeleteIncomeRequest;
import truckingappservice.activity.results.DeleteIncomeResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.models.IncomeModel;

import javax.inject.Inject;

public class DeleteIncomeActivity {
    private final Logger log = LogManager.getLogger();
    private final IncomeDao incomeDao;

    @Inject
    public DeleteIncomeActivity(IncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public DeleteIncomeResult handleRequest(final DeleteIncomeRequest deleteIncomeRequest) {
        log.info("Received deleteIncomeRequest {}", deleteIncomeRequest);
        String incomeId = deleteIncomeRequest.getIncomeId();
        Income income = incomeDao.deleteIncome(incomeId);
        IncomeModel incomeModel = new ModelConverter().toIncomeModel(income);
        return DeleteIncomeResult.builder()
                .withIncome(incomeModel)
                .build();
    }
}

