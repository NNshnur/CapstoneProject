import truckingClient from '../api/truckingClient.js';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";
import 'bootstrap';

class UpdateExpense extends BindingClass {
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
        'redirectUpdateExpense',
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
  const updateExpenseButton = document.getElementById('updateExpense');
  if (updateExpenseButton) {
    updateExpenseButton.addEventListener('click', this.redirectUpdateExpense);
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

  // Extract the expenseId from the URL parameter
  const urlParams = new URLSearchParams(window.location.search);
  const expenseId = urlParams.get('expense');

  const categoryOptions = document.querySelectorAll('#categoryOptionss .dropdown-item');
  categoryOptions.forEach(option => {
    option.addEventListener('click', this.showCategoryOptions.bind(this));
  });

  // Use the expenseId as needed
  console.log(expenseId);
}


async submit(evt) {
  evt.preventDefault();
  const updateButton = document.getElementById('submitted');
  const errorMessageDisplay = document.getElementById('errorMessageDisplay');
  const expenseId = this.urlParams.get('expense'); // Get the expense ID from urlParams
  const truckId = document.getElementById('t_idz').value;
  const vendorName = document.getElementById('vName').value;
  const category = document.getElementById('categoryCc').value;
  const date = document.getElementById('dDate').value;
  const amount = document.getElementById('amo').value;
  const paymentType = document.getElementById('ptypeC').value;

  try {
    // Check if the user is authenticated
    const user = await this.client.getIdentity();
    if (!user) {
      // If not authenticated, show error message
      throw new Error('Only authenticated users can update an expense.');
    }

    // Populate the modal fields with the input values
    document.getElementById('truckC').textContent = truckId;
    document.getElementById('VendorNameC').textContent = vendorName;
    document.getElementById('dateC').textContent = date;
    document.getElementById('categoryC').textContent = category;
    document.getElementById('amountC').textContent = amount;
    document.getElementById('paymentTypeC').textContent = paymentType;

    // Show the "Please verify" modal
    const confirmModal = new bootstrap.Modal(document.getElementById('confirmModal'));
    confirmModal.show();

    // Handle the confirm button click
    const confirmButton = document.getElementById('confirmButton');
    confirmButton.addEventListener('click', async () => {
      // Perform the update operation after confirmation
      try {
        // Update the expense entry
        const updatedExpense = await this.client.updateExpense(
          expenseId,
          truckId,
          vendorName,
          category,
          date,
          amount,
          paymentType
        );

        // Show success message or perform other actions
        updateButton.innerText = 'Expense Updated';

        window.location.href = 'expenses.html';

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

  redirectUpdateExpense() {
  window.location.href = '/expenses.html';
   }

    redirectToExpenses() {
        console.log("redirectToExpenses");
            window.location.href = `/expenses.html`;
    }


    confirmRedirect() {
    window.location.href = '/expenses.html';
    console.log("updateExpense button clicked");
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
    const updateExpense = new UpdateExpense();
    updateExpense.mount();
};
window.addEventListener('DOMContentLoaded', main);
