package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.metrics.MetricsPublisher;

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


        public List<Expense> getAllExpenses() {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            List<Expense> expenseList = dynamoDbMapper.scan(Expense.class, scanExpression);
            return expenseList;
        }
}