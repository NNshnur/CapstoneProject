package truckingappservice.activity.results;

import truckingappservice.models.ProfileModel;

public class UpdateProfileResult {
    private final ProfileModel profile;

    private UpdateProfileResult(ProfileModel profile){
        this.profile = profile;
    }

    public ProfileModel getProfile(){
        return profile;
    }

    @Override
    public String toString() {
        return "UpdateProfileResult{" +
                "profile=" + profile +
                '}';
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{

        private ProfileModel profile;

        public Builder withProfile(ProfileModel profile){
            this.profile = profile;
            return this;
        }

        public UpdateProfileResult build(){
            return new UpdateProfileResult(profile);
        }
    }
}

