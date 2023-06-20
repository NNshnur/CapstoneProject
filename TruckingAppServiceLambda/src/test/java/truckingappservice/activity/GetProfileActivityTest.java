package truckingappservice.activity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import truckingappservice.activity.request.GetProfileRequest;
import truckingappservice.activity.results.GetProfileResult;
import truckingappservice.dynamodb.ProfileDao;
import truckingappservice.dynamodb.models.Profile;
import truckingappservice.models.ProfileModel;

public class GetProfileActivityTest {
    @Mock
    private ProfileDao profileDao;

    private GetProfileActivity getProfileActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getProfileActivity = new GetProfileActivity(profileDao);
    }

    @Test
    public void handleRequest_getsProfile() {
        // GIVEN
        String profileId = "profileId";

        GetProfileRequest request = GetProfileRequest.builder()
                .withId(profileId)
                .build();

        Profile profile = new Profile();
        profile.setId(profileId);

        ProfileModel expectedProfileModel = ProfileModel.builder()
                .withProfileId(profileId)
                .build();

        when(profileDao.getProfile(profileId)).thenReturn(profile);

        // WHEN
        GetProfileResult result = getProfileActivity.handleRequest(request);

        // THEN
        verify(profileDao).getProfile(profileId);

        assertNotNull(result.getProfileModel());
        assertEquals(expectedProfileModel, result.getProfileModel());
    }
}