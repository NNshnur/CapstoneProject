package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.UpdateProfileRequest;
import truckingappservice.activity.results.UpdateProfileResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.metrics.MetricsConstants;
import truckingappservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import java.time.ZonedDateTime;

public class UpdateProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;
    private final MetricsPublisher metricsPublisher;

    @Inject
    public UpdateProfileActivity(ProfileDao profileDao, MetricsPublisher metricsPublisher){
        this.profileDao = profileDao;
        this.metricsPublisher = metricsPublisher;
    }

    public UpdateProfileResult handleRequest(final UpdateProfileRequest updateProfileRequest)  {
        log.info("Received UpdateProfileRequest{}", updateProfileRequest);

        Profile profile = profileDao.getProfile(updateProfileRequest.getProfileId());

        Profile newProfile = profileDao.saveProfile(false, profile.getId(),
                updateProfileRequest.getFirstName(), updateProfileRequest.getLastName(), updateProfileRequest.getCompanyName(),
                updateProfileRequest.getTruckId(), updateProfileRequest.getStartingBalance());

        publishExceptionMetrics(false,false);
        return UpdateProfileResult.builder()
                .withProfile(new ModelConverter().toProfileModel(newProfile))
                .build();
    }

    //see above - this needs work to be a valid method to track any useful metric
    private void publishExceptionMetrics(final boolean isInvalidAttributeValue,
                                         final boolean isInvalidAttributeChange) {
        metricsPublisher.addCount(MetricsConstants.UPDATEPROFILE_INVALIDATTRIBUTEVALUE_COUNT,
                isInvalidAttributeValue ? 1 : 0);
        metricsPublisher.addCount(MetricsConstants.UPDATEPROFILE_INVALIDATTRIBUTECHANGE_COUNT,
                isInvalidAttributeChange ? 1 : 0);
    }
}

