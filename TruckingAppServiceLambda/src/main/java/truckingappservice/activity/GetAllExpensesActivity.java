package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.results.GetAllExpensesResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;

import javax.inject.Inject;
import java.util.List;


public class GetAllExpensesActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;

    @Inject
    public GetAllExpensesActivity(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }
    /**
     * Handles a  GetAllExpensesRequest and returns a {@link GetAllExpensesResult} containing a list of all events.
     *
     * @return a {@link GetAllExpensesResult} containing a list of all events
     */
    public GetAllExpensesResult handleRequest(){
        log.info("Receive GetAllExpensesRequest {} ", "called Get All Expenses");

        List<Expense> listExpenses = expenseDao.getAllExpenses();

        return GetAllExpensesResult.builder()
                .withEventList(listExpenses)
                .build();
    }
}
