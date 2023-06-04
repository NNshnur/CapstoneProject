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
              'getHTMLForSearchResults'
            ];
this.bindClassMethods(methodsToBind, this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayExpenses);
        this.header = new Header(this.dataStore);

    }

    /**
     * Once the client is loaded, get the profile metadata.
     */
    async clientLoaded() {
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
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
//        document.getElementById('createEvents').addEventListener('click', this.redirectCreateEvents);
        document.getElementById('logout').addEventListener('click', this.logout);
        this.client = new truckingClient();
        this.clientLoaded();
    }


    displayExpenses(){
            let expenses = this.dataStore.get("expenses");
            console.log(expenses , "from displayExpenses");
            if (expenses == null) {
                document.getElementById("expense-list").innerText = "No Expenses found";
            }
            document.getElementById("expense-list").innerHTML = this.getHTMLForSearchResults(expenses);
    }

    getHTMLForSearchResults(searchResults) {
     console.log(searchResults , "from getHTMLForSearchResults");
            if (!searchResults || !searchResults.allExpenseList || searchResults.allExpenseList.length === 0) {
                return '<h4>No results found</h4>';
            }
            let html = "";
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
                    <button class="edit-button btn btn-success rounded me-4" onclick="editExpense(${res.expenseId})">Edit</button>
                    <button class="delete-button btn btn-danger rounded me-4" onclick="deleteExpense(${res.expenseId})">Delete</button>
                    </td>
                    </div>
                </tr>`;
            }
            return html;
        }


//    redirectEditProfile(){
//        window.location.href = '/createProfile.html';
//    }
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
            window.location.href ='/landingPage.html';
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
