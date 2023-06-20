package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.CreateProfileActivity;
import truckingappservice.activity.request.CreateProfileRequest;
import truckingappservice.activity.results.CreateProfileResult;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.models.ProfileModel;

import java.util.Collections;

public class CreateProfileActivityTest {
    @Mock
    private ProfileDao profileDao;

    private CreateProfileActivity createProfileActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createProfileActivity = new CreateProfileActivity(profileDao);
    }

    @Test
    public void handleRequest_createsAndSavesProfileEntry() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedFirstName = "expectedFirstName";
        String expectedLastName = "expectedLastName";
        String expectedCompanyName = "expectedCompanyName";
        String expectedTruckId = "expectedTruckId";
        double expectedStartingBalance = 5000;

        CreateProfileRequest request = CreateProfileRequest.builder()
                .withId(expectedId)
                .withFirstName(expectedFirstName)
                .withLastName(expectedLastName)
                .withCompanyName(expectedCompanyName)
                .withTruckId(Collections.singletonList(expectedTruckId))
                .withStartingBalance(expectedStartingBalance)
                .build();

        Profile savedProfile = new Profile();
        savedProfile.setId(expectedId);
        savedProfile.setFirstName(expectedFirstName);
        savedProfile.setLastName(expectedLastName);
        savedProfile.setCompanyName(expectedCompanyName);
        savedProfile.setTruckId(Collections.singletonList(expectedTruckId));
        savedProfile.setStartingBalance(expectedStartingBalance);

        when(profileDao.saveProfile(true, request.getId(), request.getFirstName(),
                request.getLastName(), request.getCompanyName(), request.getTruckId(),
                request.getStartingBalance())).thenReturn(savedProfile);

        // WHEN
        CreateProfileResult result = createProfileActivity.handleRequest(request);

        // THEN
        verify(profileDao).saveProfile(true, request.getId(), request.getFirstName(),
                request.getLastName(), request.getCompanyName(), request.getTruckId(),
                request.getStartingBalance());

        assertNotNull(result.getProfile().getProfileId());
        assertEquals(savedProfile.getFirstName(), result.getProfile().getFirstName());
        assertEquals(savedProfile.getLastName(), result.getProfile().getLastName());
        assertEquals(savedProfile.getCompanyName(), result.getProfile().getCompanyName());
        assertEquals(savedProfile.getTruckId(), result.getProfile().getTruckId());
        assertEquals(savedProfile.getStartingBalance(), result.getProfile().getStartingBalance());
    }
}
