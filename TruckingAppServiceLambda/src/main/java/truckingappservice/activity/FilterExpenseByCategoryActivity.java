package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.FilterExpenseByCategoryRequest;
import truckingappservice.activity.results.FilterExpenseByCategoryResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Profile;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class FilterExpenseByCategoryActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;
    private final ProfileDao profileDao;

    @Inject
    public FilterExpenseByCategoryActivity(ExpenseDao expenseDao, ProfileDao profileDao) {
        this.expenseDao = expenseDao;
        this.profileDao = profileDao;
    }

    public FilterExpenseByCategoryResult handleRequest(FilterExpenseByCategoryRequest request){
        Profile profile = profileDao.getProfile(request.getId());
        if (profile == null) {
            log.error("Profile not found for id: {}", request.getId());
            return FilterExpenseByCategoryResult.builder().withExpenseListByCategory(Collections.emptyList()).build();
        }

        List<Expense> listExpenses = expenseDao.getExpensesByCategory(profile.getTruckId(), request.getCategory());
        return FilterExpenseByCategoryResult.builder()
                .withExpenseListByCategory(listExpenses)
                .build();
    }
}








