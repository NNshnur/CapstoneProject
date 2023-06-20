package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.GetAllIncomeRequest;
import truckingappservice.activity.results.GetAllIncomeResult;
import truckingappservice.dynamodb.IncomeDao;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Income;
import truckingappservice.dynamodb.models.Profile;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class GetAllIncomeActivity {
    private final Logger log = LogManager.getLogger();
    private final IncomeDao incomeDao;
    private final ProfileDao profileDao;

    @Inject
    public GetAllIncomeActivity(IncomeDao incomeDao, ProfileDao profileDao) {
        this.incomeDao = incomeDao;
        this.profileDao = profileDao;
    }

    public GetAllIncomeResult handleRequest(GetAllIncomeRequest request){
        Profile profile = profileDao.getProfile(request.getId());
        if (profile == null) {
            log.error("Profile not found for id: {}", request.getId());
            return GetAllIncomeResult.builder().withIncomeList(Collections.emptyList()).build();
        }

        List<Income> listIncomes = incomeDao.getAllIncome(profile.getTruckId());

        return GetAllIncomeResult.builder()
                .withIncomeList(listIncomes)
                .build();
    }
}
