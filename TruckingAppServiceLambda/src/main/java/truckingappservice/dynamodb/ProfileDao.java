package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import truckingappservice.dynamodb.models.Expense;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.exceptions.InvalidAttributeException;
import truckingappservice.exceptions.ProfileNotFoundException;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class ProfileDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public ProfileDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Profile getProfile(String id) {

        Profile profile = this.dynamoDbMapper.load(Profile.class, id);

        if (profile == null) {
            metricsPublisher.addCount(MetricsConstants.GETPROFILE_PROFILENOTFOUND_COUNT, 1);
            throw new ProfileNotFoundException("Could not find profile with profileId " + id);
        }
        metricsPublisher.addCount(MetricsConstants.GETPROFILE_PROFILENOTFOUND_COUNT, 0);

        return profile;
    }

    public Profile saveProfile(boolean isNew, String id, String firstName, String lastName, String companyName, List<String> truckId) {
        Profile saveProfile = new Profile();
        saveProfile.setId(id);
        if (isNew) {
            saveProfile.setFirstName(firstName);
            saveProfile.setLastName(lastName);
            saveProfile.setCompanyName(companyName);
            saveProfile.setTruckId(truckId);
            this.dynamoDbMapper.save(saveProfile);
        } else {
            if (firstName != null || !firstName.isEmpty()) {
                saveProfile.setFirstName(firstName);
            }
            if (lastName != null || !lastName.isEmpty()) {
                saveProfile.setLastName(lastName);
            }
            if (companyName != null || !companyName.isEmpty()) {
                saveProfile.setCompanyName(companyName);
            }
            if (truckId != null || !truckId.isEmpty()) {
                saveProfile.setTruckId(truckId);
            }
            this.dynamoDbMapper.save(saveProfile);
        }
        return saveProfile;
    }

}






