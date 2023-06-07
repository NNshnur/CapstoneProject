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

  const categoryOptions = document.querySelectorAll('#categoryOptionss .dropdown-item');
    categoryOptions.forEach(option => {
      option.addEventListener('click', this.showCategoryOptions.bind(this));
    });

}

async submit(evt) {
  evt.preventDefault();
  const updateButton = document.getElementById('submitted');
  const errorMessageDisplay = document.getElementById('errorMessageDisplay');
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
    updateButton.innerText = 'Submit';
    errorMessageDisplay.innerText = `Error: ${error.message}`;
    errorMessageDisplay.classList.remove('hidden');
  }
}

async function editExpense(expenseId) {
  try {
    const expense = await this.client.getExpense(expenseId);
    if (!expense) {
      throw new Error('Expense not found.');
    }
    // Populate the form fields with the expense data
    document.getElementById('t_idz').value = expense.truckId;
    document.getElementById('vName').value = expense.vendorName;
    document.getElementById('categoryCc').value = expense.category;
    document.getElementById('dDate').value = expense.date;
    document.getElementById('amo').value = expense.amount;
    document.getElementById('ptypeC').value = expense.paymentType;

    const editButton = document.getElementById('editConfirm');
    editButton.addEventListener('click', () => saveChanges(expenseId));

    // Redirect to updateExpense.html
    const updateButton = document.getElementById('updateExpense');
    updateButton.addEventListener('click', this.redirectUpdateExpense);

    const editModal = new bootstrap.Modal(document.getElementById('editModal'));
    editModal.show();
  } catch (error) {
    console.error('Error:', error.message);
  }
}



        async function saveChanges(expenseId) {
            const truckId = document.getElementById('t_idz').value;
            const vendorName = document.getElementById('vName').value;
            const category = document.getElementById('categoryCc').value;
            const date = document.getElementById('dDate').value;
            const amount = document.getElementById('amo').value;
            const paymentType = document.getElementById('ptypeC').value;

            try {
                // Update the expense with the new data
                const updatedExpense = await this.client.updateExpense(
                    expenseId,
                    truckId,
                    vendorName,
                    category,
                    date,
                    amount,
                    paymentType
                );

                console.log('Expense updated:', updatedExpense);

                // Close the edit modal
                const editModal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
                editModal.hide();

                // Refresh the expenses list or perform any other necessary actions
                // ...

            } catch (error) {
                console.error('Error:', error.message);
            }
        }

  redirectUpdateExpense() {
  window.location.href = '/updateExpense.html';
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
    const createExpense = new CreateExpense();
    createExpense.mount();
};
window.addEventListener('DOMContentLoaded', main);
