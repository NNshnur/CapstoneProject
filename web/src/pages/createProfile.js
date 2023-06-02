import truckingClient from '../api/truckingClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";

class CreateProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount','confirmRedirect','submitFormData', 'redirectEditProfile','logout','setPlaceholders'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

    async clientLoaded() {
        const identity = await this.client.getIdentity();
        this.dataStore.set('id', identity.email);
        const profile = await this.client.getProfile(identity.email);
        this.dataStore.set('profile', profile);
        if(profile == null) {
            document.getElementById("welcome").innerHTML = "<em>Welcome! First of all, let us create your profile!</em>"
        }
        document.getElementById("fname").setAttribute('placeholder', 'First Name');
        document.getElementById("lname").setAttribute('placeholder', 'Last Name');
        document.getElementById("companyName").setAttribute('placeholder', 'Company Name');
        document.getElementById("truckIds").setAttribute('placeholder', 'Truck ID(s)');
        this.setPlaceholders();

    }

    mount() {

        document.getElementById('profilePic').addEventListener('click', this.redirectEditProfile);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('confirm').addEventListener('click', this.confirmRedirect);
        document.getElementById('submitted').addEventListener('click', this.submitFormData);


        this.client = new truckingClient();
        this.clientLoaded();
    }

    async setPlaceholders(){
        const profile = this.dataStore.get("profile");

        if (profile == null) {
            return;
        }
        if (profile.profileModel.firstName && profile.profileModel.lastName) {
            document.getElementById('fname').setAttribute('placeholder', profile.profileModel.firstName);
            document.getElementById('lname').setAttribute('placeholder', profile.profileModel.lastName);
        }
        if (profile.profileModel.companyName) {
            document.getElementById('companyName').setAttribute('placeholder',profile.profileModel.companyName);
        }
        if (profile.profileModel.truckIds) {
            document.getElementById('truckIds').setAttribute('placeholder',profile.profileModel.truckIds);
        }

         const loadingElement = document.getElementById("loading");
          if (loadingElement) {
            loadingElement.remove();
          }
    }


    async submitFormData(evt){
        evt.preventDefault();
        const firstName = document.getElementById('fname').value || document.getElementById('fname').getAttribute('placeholder');
        const lastName = document.getElementById('lname').value ||  document.getElementById('lname').getAttribute('placeholder');
        const companyName = document.getElementById('companyName').value ||  document.getElementById('companyName').getAttribute('placeholder');
        const truckIds = document.getElementById('truckIds').value ||  document.getElementById('truckIds').getAttribute('placeholder');
        console.log(firstName, lastName, companyName, truckIds);
        let profile;
        if(document.getElementById('welcome').innerText == "Welcome! First of all, let us create your profile!"){
            profile = await this.client.createProfile(companyName, firstName, lastName, truckIds, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
        } else {
            profile = await this.client.updateProfile(this.dataStore.get('id'),companyName, firstName, lastName, truckIds, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
        }


        this.dataStore.set('profile', profile);
        document.getElementById('fnameC').innerText = firstName || profile.profileModel.firstName;
        document.getElementById('lnameC').innerText = lastName || profile.profileModel.lastName;
        document.getElementById('companyName').innerText = companyName || profile.profileModel.companyName;
        document.getElementById('truckIds').innerText = truckIds || profile.profileModel.truckIds;
        document.getElementById('loading-modal').remove();

    }
    confirmRedirect() {
        window.location.href = '/profile.html';
    }
    redirectEditProfile(){
        window.location.href = '/createProfile.html';
    }

    logout(){
        this.client.logout();
    }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createProfile = new CreateProfile();
    createProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);