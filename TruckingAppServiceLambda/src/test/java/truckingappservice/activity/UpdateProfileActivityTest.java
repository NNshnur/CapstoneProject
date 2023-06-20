package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.UpdateProfileActivity;
import truckingappservice.activity.request.UpdateProfileRequest;
import truckingappservice.activity.results.UpdateProfileResult;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.metrics.MetricsPublisher;
import truckingappservice.models.ProfileModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpdateProfileActivityTest {
    @Mock
    private ProfileDao profileDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private UpdateProfileActivity updateProfileActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateProfileActivity = new UpdateProfileActivity(profileDao, metricsPublisher);
    }

    @Test
    public void handleRequest_updatesProfile() {
        // GIVEN
        String profileId = "profileId";
        String firstName = "Naty";
        String lastName = "Shnur";
        String companyName = "Naty's Trucking";
        List<String> truckId = new ArrayList<>();
        truckId.add("31-03-89");
        truckId.add("77-55-33");
        truckId.add("22-11-00");
        double startingBalance = 1000.0;

        UpdateProfileRequest request = UpdateProfileRequest.builder()
                .withId(profileId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withCompanyName(companyName)
                .withTruckId(truckId)
                .withStartingBalance(startingBalance)
                .build();

        Profile existingProfile = new Profile();
        existingProfile.setId(profileId);

        Profile updatedProfile = new Profile();
        updatedProfile.setId(profileId);
        updatedProfile.setFirstName(firstName);
        updatedProfile.setLastName(lastName);
        updatedProfile.setCompanyName(companyName);
        updatedProfile.setTruckId(truckId);
        updatedProfile.setStartingBalance(startingBalance);

        when(profileDao.getProfile(profileId)).thenReturn(existingProfile);
        when(profileDao.saveProfile(
                false, profileId, firstName, lastName, companyName, truckId, startingBalance))
                .thenReturn(updatedProfile);

        ProfileModel expectedProfileModel = ProfileModel.builder()
                .withProfileId(profileId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withCompanyName(companyName)
                .withTruckId(truckId)
                .withStartingBalance(startingBalance)
                .build();

        // WHEN
        UpdateProfileResult result = updateProfileActivity.handleRequest(request);

        // THEN
        verify(profileDao).getProfile(profileId);
        verify(profileDao).saveProfile(
                false, profileId, firstName, lastName, companyName, truckId, startingBalance);
        assertEquals(expectedProfileModel, result.getProfile());
    }
}