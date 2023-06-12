import truckingClient from '../api/truckingClient.js';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";
import 'bootstrap';

class CreateIncome extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods([
        'mount',
        'clientLoaded',
        'redirectProfile',
        'confirmRedirect',
        'submit',
        'redirectEditProfile',
        'redirectAllIncome',
        'redirectCreateIncome',
        'logout'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

  /**
      * Once the client is loaded, get the profile metadata.
      */
     async clientLoaded() {
         const identity = await this.client.getIdentity();
         const profile = await this.client.getProfile(identity.email);
         this.dataStore.set("email", identity.email);
         this.dataStore.set('profile', profile);

     }

mount() {
  const createIncomeButton = document.getElementById('createIncome');
  if (createIncomeButton) {
    createIncomeButton.addEventListener('click', this.redirectCreateIncome);
  }

  const logoutButton = document.getElementById('logout');
  if (logoutButton) {
    logoutButton.addEventListener('click', this.logout);
  }

  const submitButton = document.getElementById('submitted');
  if (submitButton) {
    submitButton.addEventListener('click', this.submit);
  }

  const closeButton = document.querySelector('[data-bs-dismiss="modal"]');
  if (closeButton) {
    closeButton.addEventListener('click', this.confirmRedirect.bind(this));
  }

  this.header.addHeaderToPage();
  this.client = new truckingClient();
  this.clientLoaded();

}

async submit(evt) {
  evt.preventDefault();
  const createButton = document.getElementById('submitted');
  const errorMessageDisplay = document.getElementById('errorMessageDisplay');

    const truckId = document.getElementById('t_idzInc').value;
    const date = document.getElementById('incDate').value;
    const deadHeadMiles = document.getElementById('deadHeadMilesC').value;
    const loadedMiles = document.getElementById('loadedC').value;
    const grossIncome = document.getElementById('grossC').value;
  try {
    // Check if the user is authenticated
    const user = await this.client.getIdentity();
    if (!user) {
      // If not authenticated, show error message
      throw new Error('Only authenticated users can create an income.');
    }
    const incomeCreator = user.email;
    const income = await this.client.createIncome(
      truckId,
      date,
      deadHeadMiles,
      loadedMiles,
      grossIncome,
      (error) => {
        createButton.innerText = 'Submit';
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
      }
    );

    // Show the verification modal
    const truckInc = document.getElementById('truckInc');
        truckInc.innerText = truckId;
        const dateInc = document.getElementById('dateInc');
        dateInc.innerText = date;
        const deadheadInc = document.getElementById('deadheadInc');
        deadheadInc.innerText = deadHeadMiles;
        const loadedInc = document.getElementById('loadedInc');
        loadedInc.innerText = loadedMiles;
        const grossInc = document.getElementById('grossInc');
        grossInc.innerText = grossIncome;

    const confirmButton = document.getElementById('confirmInc');
    confirmButton.addEventListener('click', this.confirmRedirect.bind(this));

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();
  } catch (error) {
    createButton.innerText = 'Submit';
    errorMessageDisplay.innerText = `Error: ${error.message}`;
    errorMessageDisplay.classList.remove('hidden');
  }
}

    redirectToIncome() {
        console.log("redirectToIncomes");
            window.location.href = `/income.html`;
    }

    confirmRedirect() {
    window.location.href = '/income.html';
    console.log("createIncome button clicked");
    }

    redirectProfile(){
    window.location.href = '/profile.html';
    }

    redirectEditProfile(){
    window.location.href = '/createProfile.html';
    }

    redirectAllIncome(){
    window.location.href = '/income.html';
    }

    redirectCreateIncome(){
    window.location.href = '/createIncome.html';
    }

    async logout(){
    await this.client.logout();
    if(!this.client.isLoggedIn()){
        window.location.href ='/index.html';
    }
}
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createIncome = new CreateIncome();
    createIncome.mount();
};
window.addEventListener('DOMContentLoaded', main);
