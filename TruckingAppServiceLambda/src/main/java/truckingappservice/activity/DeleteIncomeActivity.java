package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.DeleteIncomeRequest;
import truckingappservice.activity.results.DeleteIncomeResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.exceptions.ExpenseNotFoundException;
import truckingappservice.exceptions.IncomeNotFoundException;
import truckingappservice.models.IncomeModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
        Income income = incomeDao.getIncome(incomeId);
        if (income == null) {
            throw new IncomeNotFoundException("Income with ID " + deleteIncomeRequest.getIncomeId() + "not found.");
        }
        List<String> list = incomeDao.deleteIncome(incomeId);
        List<String> updatedList = new ArrayList<>(list);

        log.error("list {}", list);
        return DeleteIncomeResult.builder()
                .withIncomeList(updatedList)
                .build();
    }
}

