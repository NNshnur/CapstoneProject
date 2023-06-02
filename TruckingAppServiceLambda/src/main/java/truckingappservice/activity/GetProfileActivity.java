package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.GetProfileRequest;
import truckingappservice.activity.results.GetProfileResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.models.ProfileModel;

import javax.inject.Inject;

public class GetProfileActivity {

    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;

    @Inject
    public GetProfileActivity(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }
    /**
     * Handles a request for getting a user profile by its ID.
     *
     * @param getProfileRequest the request object containing the profile ID.
     * @return a {@code GetProfileResult} object containing the retrieved user profile.
     */
    public GetProfileResult handleRequest(final GetProfileRequest getProfileRequest){
        log.info("Receive GetProfileResult {} ", getProfileRequest);

        Profile profile = profileDao.getProfile(getProfileRequest.getProfileId());

        ProfileModel profileModel = new ModelConverter().toProfileModel(profile);

        return GetProfileResult.builder()
                .withProfileModel(profileModel)
                .build();
    }

}
