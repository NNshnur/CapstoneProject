import truckingClient from '../api/truckingClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";

class ViewAllExpenses extends BindingClass {
    constructor() {
        super();
         const methodsToBind = [
              'clientLoaded',
              'mount',
              'redirectAllExpenses',
              'redirectProfile',
              'redirectCreateExpense',
              'logout',
              'displayExpenses',
              'redirectEditProfile',
              'getHTMLForSearchResults',
              'filterExpensesByCategory',
              'deleteEntryExpense',
            ];
        this.bindClassMethods(methodsToBind, this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayExpenses);
        this.header = new Header(this.dataStore);
        this.urlParams = new URLSearchParams(window.location.search);

    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        if (profile == null) {
            this.redirectEditProfile();
            document.getElementById("welcome").innerHTML = "<em>Welcome! First of all, let us create your profile!</em>";
         }
        const expenses = await this.client.getAllExpenses();
        this.dataStore.set("email", identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('expenses', expenses);
        this.displayExpenses();


    }

    /**
     * Add the header to the page and load the dannaClient.
     */
    mount() {
        document.getElementById('expenses-link').addEventListener('click', this.redirectAllExpenses);
        const categoryDropdown = document.getElementById('categoryDropdown');
        const categoryItems = document.getElementsByClassName('dropdown-item');
         for (let i = 0; i < categoryItems.length; i++) {
            categoryItems[i].addEventListener('click', () => {
              const selectedCategory = categoryItems[i].innerText;
              this.filterExpensesByCategory(selectedCategory);
            });
          }
        document.getElementById('addEx').addEventListener('click', this.redirectCreateExpense);
        const deleteButtons = document.getElementsByClassName('delete-button');
                for (let i = 0; i < deleteButtons.length; i++) {
                    deleteButtons[i].addEventListener('click', this.deleteEntryExpense.bind(this));
                }

        const urlParams = new URLSearchParams(window.location.search);
        const expenseId = urlParams.get('expense');


        document.getElementById('logout').addEventListener('click', this.logout);
        this.client = new truckingClient();
        this.clientLoaded();

    }

    displayExpenses() {
    let expenses = this.dataStore.get("expenses");
    console.log(expenses, "from displayExpenses");
    if (expenses == null) {
        document.getElementById("expense-list").innerText = "No Expenses found";
    } else {
        document.getElementById("expense-list").innerHTML = this.getHTMLForSearchResults(expenses);
        const deleteButtons = document.getElementsByClassName('delete-button');
        for (let i = 0; i < deleteButtons.length; i++) {
           // deleteButtons[i].addEventListener('click', this.deleteEntryExpense.bind(this));
            deleteButtons[i].addEventListener('click', this.deleteEntryExpense);
        }
    }
}


    getHTMLForSearchResults(searchResults) {
     console.log(searchResults , "from getHTMLForSearchResults");
            if (!searchResults || !searchResults.allExpenseList || searchResults.allExpenseList.length === 0) {
                return '<h4>No results found</h4>';
            }
            let html = "<div id = 'expenseEditList'>";
            for (const res of searchResults.allExpenseList) {
                html += `
                <tr>
                    <td class="text-center">
                        ${res.expenseId}
                    </td>
                    <td>
                         ${res.truckId}
                    </td>
                    <td>
                         ${res.vendorName}
                    </td>
                    <td>
                         ${res.category}
                    </td>
                    <td>
                          ${res.date}
                     </td>
                    <td>
                          ${res.amount}
                    </td>
                    <td>
                          ${res.paymentType}
                    </td>
                    <td>
                    <div class="d-flex justify-content-center">
                    <a class="edit-button btn btn-warning rounded me-4 editExpense" href="updateExpense.html?expense=${res.expenseId}">Edit</a>
                      <button class="delete-button btn btn-warning rounded me-4 deleteExpense" data-expense-id="${res.expenseId}" data-id="123">Delete</button>
                    </td>
                    </div>
                </tr>`;
                }
               html+= "</div>";
            return html;
        }

async deleteEntryExpense(event) {
  try {
    const expenseId = event.target.dataset.expenseId;
    console.log("ExpenseId", expenseId);

    if (!expenseId) {
      console.error('Expense ID not found in URL parameters.');
      return;
    }

    // Check if the user is authenticated
    const user = await this.client.getIdentity();
    if (!user) {
      throw new Error('Only authenticated users can delete an expense.');
    }

    // Delete the expense entry
    await this.client.deleteExpense(expenseId);

    // Remove the expense entry from the data store
    let expenses = this.dataStore.get('expenses');
    if (expenses) {
      let updatedExpenses = expenses.allExpenseList.filter(
        (expense) => expense.expenseId !== expenseId
      );
      this.dataStore.set('expenses', { allExpenseList: updatedExpenses });
    }

    // Re-render the expenses list
    this.displayExpenses();
  } catch (error) {
    console.error('Error deleting expense:', error);
  }
}

filterExpensesByCategory(selectedCategory) {
  console.log("Filtering expenses by category:", selectedCategory);
  const expenses = this.dataStore.get('expenses');
  if (!expenses || !expenses.allExpenseList) {
    return;
  }

  const filteredExpenses = expenses.allExpenseList.filter(
    (expense) => expense.category.toLowerCase() === selectedCategory.toLowerCase()
  );

  const expenseListElement = document.getElementById('expense-list');
  if (!expenseListElement) {
    return;
  }

  if (filteredExpenses.length === 0) {
    expenseListElement.innerHTML = '<tr><td colspan="8">No expenses found for the selected category: ' + selectedCategory + '</td></tr>';
  } else {
    expenseListElement.innerHTML = this.getHTMLForSearchResults({ allExpenseList: filteredExpenses });
  }
}


    redirectEditUpdate() {
    window.location.href = '/updateExpense.html';
    }

     redirectEditProfile() {
        window.location.href = '/createProfile.html';
    }

    redirectAllExpenses(){
        window.location.href = '/expenses.html';
    }

     redirectProfile() {
         window.location.href = '/profile.html';
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
    const viewAllExpenses = new ViewAllExpenses();
    viewAllExpenses.mount();
};

window.addEventListener('DOMContentLoaded', main);
