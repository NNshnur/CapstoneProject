package truckingappservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.CreateProfileRequest;
import truckingappservice.activity.results.CreateProfileResult;
import truckingappservice.converters.ModelConverter;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.models.ProfileModel;

import javax.inject.Inject;

public class CreateProfileActivity {

    private final Logger log = LogManager.getLogger();
    private final ProfileDao profileDao;

    @Inject
    public CreateProfileActivity(ProfileDao profileDao){
        this.profileDao = profileDao;
    }
    public CreateProfileResult handleRequest(final CreateProfileRequest createProfileRequest){

        Profile newProfile = profileDao.saveProfile(true,
                createProfileRequest.getId(), createProfileRequest.getFirstName(),
                createProfileRequest.getLastName(), createProfileRequest.getCompanyName(),
                createProfileRequest.getTruckId(), createProfileRequest.getStartingBalance());

        ProfileModel profileModel = new ModelConverter().toProfileModel(newProfile);
        return CreateProfileResult.builder()
                .withProfile(profileModel)
                .build();
    }

}
