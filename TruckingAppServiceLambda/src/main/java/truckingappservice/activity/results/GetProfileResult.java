package truckingappservice.activity.results;

import truckingappservice.models.ProfileModel;

public class GetProfileResult {
    private final ProfileModel profileModel;
    /**
     * Constructs a new GetProfileResult object with the given profile model.
     *
     * @param profileModel the profile model retrieved from the database
     */
    public GetProfileResult(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }
    /**
     * Returns the profile model retrieved from the database.
     *
     * @return the profile model
     */
    public ProfileModel getProfileModel() {
        return profileModel;
    }
    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "GetProfileResult{" +
                "profileModel=" + profileModel +
                '}';
    }

    public static class Builder{
        private ProfileModel profileModel;

        public Builder withProfileModel(ProfileModel profileModel){
            this.profileModel = profileModel;
            return this;
        }

        public GetProfileResult build(){
            return new GetProfileResult(profileModel);
        }

    }

    public static Builder builder(){
        return new Builder();
    }

}

