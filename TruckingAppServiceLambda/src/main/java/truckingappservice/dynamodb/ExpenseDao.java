package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.exceptions.ExpenseNotFoundException;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;
import truckingappservice.models.Category;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
@Singleton
public class ExpenseDao {

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
        public ExpenseDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
            this.dynamoDbMapper = dynamoDbMapper;
            this.metricsPublisher = metricsPublisher;
        }

        /**
         * Returns the Expenses list
         * @return the stored Event, or null if none was found.
         */
        public Expense getExpense(String expenseId) {

            Expense expense = this.dynamoDbMapper.load(Expense.class, expenseId);


            if (expense == null) {
                metricsPublisher.addCount(MetricsConstants.GETEXPENSE_EXPENSENOTFOUND_COUNT, 1);
                throw new ExpenseNotFoundException("Could not find event with id " + expenseId);
            }

            metricsPublisher.addCount(MetricsConstants.GETEXPENSE_EXPENSENOTFOUND_COUNT, 0);
            return expense;
        }

        public List<Expense> getAllExpenses() {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Expense> expenseList = dynamoDbMapper.scan(Expense.class, scanExpression);
            return expenseList;
        }

    public Expense saveExpense(boolean isNew, String expenseId, String truckId, String vendorName, Category category,
                               String date, double amount, String paymentType) {
        Expense expense = new Expense();

        if(isNew){
            expense.setExpenseId(expense.generateId());
            expense.setTruckId(truckId);
            expense.setVendorName(vendorName);
            expense.setCategory(category);
            expense.setDate(date);
            expense.setAmount(amount);
            expense.setPaymentType(paymentType);

        } else {

            if (expenseId!=null & !expenseId.isEmpty()) {
                expense.setExpenseId(expenseId);
            }
            if(truckId != null && !truckId.isEmpty()){
                expense.setTruckId(truckId);
            }
            if(vendorName != null && !vendorName.isEmpty()){
                expense.setVendorName(vendorName);
            }
            if(category != null){
                expense.setCategory(category);
            }
            if(date != null && !date.isEmpty()){
                expense.setDate(date);
            }

            if(amount != 0){
                expense.setAmount(amount);
            }

            if(paymentType != null && !paymentType.isEmpty()){
                expense.setPaymentType(paymentType);
            }

        }
        this.dynamoDbMapper.save(expense);

        return expense;
    }
}