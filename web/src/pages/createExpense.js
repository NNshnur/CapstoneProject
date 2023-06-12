import truckingClient from '../api/truckingClient.js';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";
import 'bootstrap';

class CreateExpense extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods([
        'mount',
        'clientLoaded',
        'redirectProfile',
        'confirmRedirect',
        'submit',
        'redirectEditProfile',
        'redirectAllExpenses',
        'redirectCreateExpense',
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
  const createExpenseButton = document.getElementById('createExpense');
  if (createExpenseButton) {
    createExpenseButton.addEventListener('click', this.redirectCreateExpense);
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

  const categoryOptions = document.querySelectorAll('#categoryOptionss .dropdown-item');
    categoryOptions.forEach(option => {
      option.addEventListener('click', this.showCategoryOptions.bind(this));
    });

}

async submit(evt) {
  evt.preventDefault();
  const createButton = document.getElementById('submitted');
  const errorMessageDisplay = document.getElementById('errorMessageDisplay');
  const truckId = document.getElementById('t_idz').value;
  const vendorName = document.getElementById('vName').value;
  const category = document.getElementById('categoryCc').value; // Get the selected category value
  const date = document.getElementById('dDate').value;
  const amount = document.getElementById('amo').value;
  const paymentType = document.getElementById('ptypeC').value;
  try {
    // Check if the user is authenticated
    const user = await this.client.getIdentity();
    if (!user) {
      // If not authenticated, show error message
      throw new Error('Only authenticated users can create an expense.');
    }
    const expenseCreator = user.email;
    const expense = await this.client.createExpense(
      truckId,
      vendorName,
      category,
      date,
      amount,
      paymentType,
      (error) => {
        createButton.innerText = 'Submit';
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
      }
    );

    // Show the verification modal
    const truckC = document.getElementById('truckC');
    truckC.innerText = truckId;
    const VendorNameC = document.getElementById('VendorNameC');
    VendorNameC.innerText = vendorName;
    const categoryC = document.getElementById('categoryC');
    categoryC.innerText = category;
    const dateC = document.getElementById('dateC');
    dateC.innerText = date;
    const amountC = document.getElementById('amountC');
    amountC.innerText = amount;
    const paymentTypeC = document.getElementById('paymentTypeC');
    paymentTypeC.innerText = paymentType;

    const confirmButton = document.getElementById('confirm');
    confirmButton.addEventListener('click', this.confirmRedirect.bind(this));

    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();
  } catch (error) {
    createButton.innerText = 'Submit';
    errorMessageDisplay.innerText = `Error: ${error.message}`;
    errorMessageDisplay.classList.remove('hidden');
  }
}

    redirectToExpenses() {
        console.log("redirectToExpenses");
            window.location.href = `/expenses.html`;
    }
    confirmRedirect() {
    window.location.href = '/expenses.html';
    console.log("createExpense button clicked");
    }

    redirectProfile(){
    window.location.href = '/profile.html';
    }

    redirectEditProfile(){
    window.location.href = '/createProfile.html';
    }

    redirectAllExpenses(){
    window.location.href = '/expenses.html';
    }

    redirectCreateExpense(){
    window.location.href = '/createExpense.html';
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
    const createExpense = new CreateExpense();
    createExpense.mount();
};
window.addEventListener('DOMContentLoaded', main);
