package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public GetAllExpensesResult handleRequest(String id){
        Profile profile = profileDao.getProfile(id);
        if (profile == null) {
            log.error("Profile not found for id: {}", id);
            return GetAllExpensesResult.builder().withExpenseList(Collections.emptyList()).build();
        }

        List<Expense> listExpenses = expenseDao.getAllExpenses(profile.getTruckId());

        return GetAllExpensesResult.builder()
                .withExpenseList(listExpenses)
                .build();
    }
}
