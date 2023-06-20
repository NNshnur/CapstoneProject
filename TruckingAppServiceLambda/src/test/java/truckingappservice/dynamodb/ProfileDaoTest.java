package truckingappservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.exceptions.ProfileNotFoundException;
import truckingappservice.metrics.MetricsPublisher;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProfileDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;

    private ProfileDao profileDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        profileDao = new ProfileDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    void getProfile_withExistingProfileId_returnsProfile() {
        // GIVEN
        String profileId = "profileId";
        Profile expectedProfile = new Profile();
        expectedProfile.setId(profileId);
        when(dynamoDBMapper.load(Profile.class, profileId)).thenReturn(expectedProfile);

        // WHEN
        Profile result = profileDao.getProfile(profileId);

        // THEN
        verify(dynamoDBMapper).load(Profile.class, profileId);
        assertEquals(expectedProfile, result, "Expected to receive the Profile object");
    }

    @Test
    void getProfile_withNonExistingProfileId_throwsProfileNotFoundException() {
        // GIVEN
        String profileId = "nonExistingProfileId";
        when(dynamoDBMapper.load(Profile.class, profileId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(ProfileNotFoundException.class, () -> {
            profileDao.getProfile(profileId);
        });

        verify(dynamoDBMapper).load(Profile.class, profileId);
    }

    @Test
    void saveProfile_withNewProfile_returnsSavedProfile() {
        // GIVEN
        boolean isNew = true;
        String profileId = null;
        String firstName = "Natalie";
        String lastName = "Shnur";
        String companyName = "Naty's Trucking Company";
        List<String> truckIds = new ArrayList<>();
        truckIds.add("112233");
        truckIds.add("556677");
        double startingBalance = 1000.0;

        ProfileDao profileDao = new ProfileDao(dynamoDBMapper, metricsPublisher);

        // WHEN
        Profile result = profileDao.saveProfile(isNew, profileId, firstName, lastName, companyName, truckIds, startingBalance);

        // THEN

        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(companyName, result.getCompanyName());
        assertEquals(truckIds, result.getTruckId());
        assertEquals(startingBalance, result.getStartingBalance(), 0.01);
        verify(dynamoDBMapper).save(eq(result));
    }

    @Test
    void saveProfile_withExistingProfile_updatesAndReturnsSavedProfile() {
        // GIVEN
        boolean isNew = false;
        String profileId = "existingProfileId";
        String firstName = "Natalia";
        String lastName = "Shnur";
        String companyName = "Naty's Company";
        List<String> truckIds = new ArrayList<>();
        double startingBalance = 1000.0;

        Profile existingProfile = new Profile();
        existingProfile.setId(profileId);
        existingProfile.setFirstName("Old First Name");
        existingProfile.setLastName("Old Last Name");
        existingProfile.setCompanyName("Old Company Name");
        existingProfile.setTruckId(new ArrayList<>());
        existingProfile.setStartingBalance(2000.0);

        when(dynamoDBMapper.load(Profile.class, profileId)).thenReturn(existingProfile);

        // Create an instance of your ProfileDao class
        ProfileDao profileDao = new ProfileDao(dynamoDBMapper, null);

        // WHEN
        Profile result = profileDao.saveProfile(isNew, profileId, firstName, lastName, companyName, truckIds, startingBalance);

        // THEN

        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(companyName, result.getCompanyName());
        assertEquals(truckIds, result.getTruckId());
        assertEquals(startingBalance, result.getStartingBalance(), 0.01);
        verify(dynamoDBMapper).save(eq(result));
    }
}