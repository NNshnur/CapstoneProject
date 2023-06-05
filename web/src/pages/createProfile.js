import truckingClient from '../api/truckingClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";
import 'bootstrap';


class CreateProfile extends BindingClass {
  constructor() {
    super();
    this.bindClassMethods(['clientLoaded', 'mount','confirmRedirect', 'submitFormData', 'redirectEditProfile', 'logout', 'setPlaceholders'], this);
    this.dataStore = new DataStore();
    this.header = new Header(this.dataStore);
  }

  async clientLoaded() {
    const identity = await this.client.getIdentity();
    this.dataStore.set('id', identity.email);
    const profile = await this.client.getProfile(identity.email);
    this.dataStore.set('profile', profile);
    if (profile == null) {
      document.getElementById("welcome").innerHTML = "<em>Welcome! First of all, let us create your profile!</em>";
    }
    this.setPlaceholders();
  }

  mount() {
//    document.getElementById('profilePic').addEventListener('click', this.redirectEditProfile);
    document.getElementById('logout').addEventListener('click', this.logout);
    document.getElementById('confirm').addEventListener('click', this.confirmRedirect);
    document.getElementById('submitted').addEventListener('click', this.submitFormData);

    this.client = new truckingClient();
    this.clientLoaded();
  }

  async setPlaceholders() {
    const profile = this.dataStore.get("profile");

    if (profile == null) {
      return;
    }
    const loadingElement = document.getElementById("loading");
    if (loadingElement) {
      loadingElement.remove();
    }
  }

async submitFormData(evt) {
  evt.preventDefault();

  const firstName = document.getElementById('fname').value;
  const lastName = document.getElementById('lname').value;
  const companyName = document.getElementById('coname').value;
  const truckIds = document.getElementById('tid').value.split(",").map(id => id.trim());

  try {
    let profile;

    if (document.getElementById('welcome').innerText == "Welcome! First of all, let us create your profile!") {
      profile = await this.client.createProfile(firstName, lastName, companyName, truckIds);

    } else {
      const id = this.dataStore.get('id');
      profile = await this.client.updateProfile(id, firstName, lastName, companyName, truckIds);

    }

    this.dataStore.set('profile', profile);
    document.getElementById('fnameC').innerText = firstName || profile.profileModel.firstName;
    document.getElementById('lnameC').innerText = lastName || profile.profileModel.lastName;
    document.getElementById('companyNameC').innerText = companyName || profile.profileModel.companyName;
    document.getElementById('truckIdsC').innerText = truckIds.join(",") || profile.profileModel.truckIds;
    document.getElementById('loading-modal').remove();

  } catch (error) {
    const errorMessageDisplay = document.getElementById('errorMessage');
    if (errorMessageDisplay) {
      errorMessageDisplay.innerText = `Error: ${error.message}`;
    }
  }
}

  confirmRedirect() {
    window.location.href = '/profile.html';
  }

  redirectEditProfile() {
    window.location.href = '/createProfile.html';
  }

  logout() {
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