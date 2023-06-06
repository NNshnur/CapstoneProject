package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.GetAllExpensesRequest;
import truckingappservice.activity.results.GetAllExpensesResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Profile;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;


public class GetAllExpensesActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;
    private final ProfileDao profileDao;

    @Inject
    public GetAllExpensesActivity(ExpenseDao expenseDao, ProfileDao profileDao) {
        this.expenseDao = expenseDao;
        this.profileDao = profileDao;
    }

    public GetAllExpensesResult handleRequest(GetAllExpensesRequest request){
        Profile profile = profileDao.getProfile(request.getId());
        log.error("profile{}", profile);
        if (profile == null) {
            log.error("Profile not found for id: {}", request.getId());
            return GetAllExpensesResult.builder().withExpenseList(Collections.emptyList()).build();
        }

        List<Expense> listExpenses = expenseDao.getAllExpenses(profile.getTruckId());
        log.error("listExpenses {}", listExpenses);

        return GetAllExpensesResult.builder()
                .withExpenseList(listExpenses)
                .build();
    }
}
