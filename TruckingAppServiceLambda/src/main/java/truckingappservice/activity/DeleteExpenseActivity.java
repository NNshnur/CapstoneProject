package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.DeleteExpenseRequest;
import truckingappservice.activity.results.DeleteExpenseResult;
import truckingappservice.dynamodb.ExpenseDao;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.exceptions.ExpenseNotFoundException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeleteExpenseActivity {
    private final Logger log = LogManager.getLogger();
    private final ExpenseDao expenseDao;

    @Inject
    public DeleteExpenseActivity(ExpenseDao expenseDao){
        this.expenseDao = expenseDao;
    }

    public DeleteExpenseResult handleRequest(final DeleteExpenseRequest deleteExpenseRequest){
        log.info("Received DeleteExpenseRequest {}", deleteExpenseRequest);
        String expenseId = deleteExpenseRequest.getExpenseId();
        log.error("ExpenseId {}" + expenseId);
        Expense expense = expenseDao.getExpense(expenseId);
        log.error("Expense {}", expense);
        if (expense == null) {
            throw new ExpenseNotFoundException("Expense with ID " + deleteExpenseRequest.getExpenseId() + "not found.");
        }

        List<String> list = expenseDao.deleteExpense(expenseId);
        log.error("list {}", list);
        return DeleteExpenseResult.builder()
                .withExpenseList(list)
                .build();
    }
}