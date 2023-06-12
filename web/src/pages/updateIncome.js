import truckingClient from '../api/truckingClient.js';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";
import 'bootstrap';

class UpdateIncome extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods([
        'mount',
        'clientLoaded',
        'redirectProfile',
        'confirmRedirect',
        'submit',
        'redirectEditProfile',
        'redirectAllIncomes',
        'redirectUpdateIncome',
        'logout'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.urlParams = new URLSearchParams(window.location.search);
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
  const updateIncomeButton = document.getElementById('updateIncome');
  if (updateIncomeButton) {
    updateIncomeButton.addEventListener('click', this.redirectUpdateIncome);
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

  const urlParams = new URLSearchParams(window.location.search);
  const incomeId = urlParams.get('income');

  console.log(incomeId);
}


async submit(evt) {
  evt.preventDefault();
  const updateButton = document.getElementById('submitted');
  const errorMessageDisplay = document.getElementById('errorMessageDisplay');
  const incomeId = this.urlParams.get('income'); // Get the income ID from urlParams
  const truckId = document.getElementById('t_idUpdate').value;
  const date = document.getElementById('dateUpdate').value;
  const deadHeadMiles = document.getElementById('deadHeadUpdate').value;
  const loadedMiles = document.getElementById('loadedUpdate').value;
  const grossIncome = document.getElementById('grossUpdate').value;

  try {
    // Check if the user is authenticated
    const user = await this.client.getIdentity();
    if (!user) {
      // If not authenticated, show error message
      throw new Error('Only authenticated users can update an expense.');
    }

    // Populate the modal fields with the input values
    document.getElementById('truckUpd').textContent = truckId;
    document.getElementById('dateUpd').textContent = date;
    document.getElementById('deadheadUpd').textContent = deadHeadMiles;
    document.getElementById('loadedUpd').textContent = loadedMiles;
    document.getElementById('grossUpd').textContent = grossIncome;

    // Show the "Please verify" modal
    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    // Handle the confirm button click
    const confirmButton = document.getElementById('confirmButton');
    confirmButton.addEventListener('click', async () => {
      try {
        const updatedIncome = await this.client.updateIncome(
          incomeId,
          truckId,
          date,
          deadHeadMiles,
          loadedMiles,
          grossIncome,
        );

        // Show success message or perform other actions
        updateButton.innerText = 'Income Updated';

        window.location.href = 'income.html';

      } catch (error) {
        updateButton.innerText = 'Submit';
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
      }
    });
    } catch (error) {
        updateButton.innerText = 'Submit';
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
      }
    }

  redirectUpdateIncome() {
  window.location.href = '/income.html';
   }

    redirectToIncomes() {
        console.log("redirectToIncomes");
            window.location.href = `/income.html`;
    }


    confirmRedirect() {
    window.location.href = '/income.html';
    console.log("updateIncome button clicked");
    }

    redirectProfile(){
    window.location.href = '/profile.html';
    }

    redirectEditProfile(){
    window.location.href = '/createProfile.html';
    }

    redirectAllIncomes(){
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
    const updateIncome = new UpdateIncome();
    updateIncome.mount();
};
window.addEventListener('DOMContentLoaded', main);
