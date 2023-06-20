package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.DeleteExpenseRequest;
import truckingappservice.activity.results.DeleteExpenseResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.exceptions.ExpenseNotFoundException;

import javax.inject.Inject;
import java.util.List;

public class DeleteExpenseActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;

    @Inject
    public DeleteExpenseActivity(ExpenseDao expenseDao){
        this.expenseDao = expenseDao;
    }

    public DeleteExpenseResult handleRequest(final DeleteExpenseRequest deleteExpenseRequest){
        String expenseId = deleteExpenseRequest.getExpenseId();
        Expense expense = expenseDao.getExpense(expenseId);
        if (expense == null) {
            throw new ExpenseNotFoundException("Expense with ID " + deleteExpenseRequest.getExpenseId() + "not found.");
        }

        List<String> list = expenseDao.deleteExpense(expenseId);
        return DeleteExpenseResult.builder()
                .withExpenseList(list)
                .build();
    }
}