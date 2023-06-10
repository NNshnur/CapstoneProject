package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.UpdateExpenseRequest;
import truckingappservice.activity.request.UpdateIncomeRequest;
import truckingappservice.activity.results.UpdateExpenseResult;
import truckingappservice.activity.results.UpdateIncomeResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;

import javax.inject.Inject;

public class UpdateIncomeActivity {
    private final Logger log = LogManager.getLogger();
    private final IncomeDao incomeDao;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public UpdateIncomeActivity(IncomeDao incomeDao, MetricsPublisher metricsPublisher) {
        this.incomeDao = incomeDao;
        this.metricsPublisher = metricsPublisher;
    }


    public UpdateIncomeResult handleRequest(final UpdateIncomeRequest updateIncomeRequest) {
        log.info("Received updateIncomeRequest {}", updateIncomeRequest);

        Income updateIncome = incomeDao.saveIncome(false, updateIncomeRequest.getIncomeId(), updateIncomeRequest.getTruckId(),
                updateIncomeRequest.getDate(), updateIncomeRequest.getDeadHeadMiles(), updateIncomeRequest.getLoadedMiles(),
                updateIncomeRequest.getTotalMiles(), updateIncomeRequest.getGrossIncome(), updateIncomeRequest.getRatePerMile());

        return UpdateIncomeResult.builder()
                .withIncome(new ModelConverter().toIncomeModel(updateIncome))
                .build();
    }


    private void publishExceptionMetrics(final boolean isInvalidAttributeValue,
                                         final boolean isInvalidAttributeChange) {

        metricsPublisher.addCount(MetricsConstants.UPDATEINCOME_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
        metricsPublisher.addCount(MetricsConstants.UPDATEINCOME_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeChange ? 1 : 0);
    }
}




