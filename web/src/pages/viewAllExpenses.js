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
              'deleteEntryExpense',
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
        if (profile == null) {
              redirectEditProfile();
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
        document.getElementById('addEx').addEventListener('click', this.redirectCreateExpense);

//        var elements = document.getElementsByClassName('delete-button')
//            for (var i = 0; i < elements.length; i++) {
//            console.log(elements.length + " elements length " + elements);
//            elements[i].addEventListener('click', this.deleteEntryExpense, false);
//            }
            const deleteButtons = document.getElementsByClassName('delete-button');
                for (let i = 0; i < deleteButtons.length; i++) {
                    deleteButtons[i].addEventListener('click', this.deleteEntryExpense.bind(this));
                }


        document.getElementById('logout').addEventListener('click', this.logout);
        this.client = new truckingClient();
        this.clientLoaded();
    }


//    displayExpenses(){
//            let expenses = this.dataStore.get("expenses");
//            console.log(expenses , "from displayExpenses");
//            if (expenses == null) {
//                document.getElementById("expense-list").innerText = "No Expenses found";
//            }
//            document.getElementById("expense-list").innerHTML = this.getHTMLForSearchResults(expenses);
//    }

    displayExpenses() {
    let expenses = this.dataStore.get("expenses");
    console.log(expenses, "from displayExpenses");
    if (expenses == null) {
        document.getElementById("expense-list").innerText = "No Expenses found";
    } else {
        document.getElementById("expense-list").innerHTML = this.getHTMLForSearchResults(expenses);
        const deleteButtons = document.getElementsByClassName('delete-button');
        for (let i = 0; i < deleteButtons.length; i++) {
            deleteButtons[i].addEventListener('click', this.deleteEntryExpense.bind(this));
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
                    <a class="edit-button btn btn-success rounded me-4 editExpense" href="updateExpense.html?expense=${res.expenseId}">Edit</a>
                    <button class="delete-button btn btn-danger rounded me-4 deleteExpense" data-expenseId="${res.expenseId}">Delete</button>

                    </td>
                    </div>
                </tr>`;
                }
               html+= "</div>";
            return html;
        }
    deleteEntryExpense() {
       console.log("we are deleting - just a test message")

    }



    redirectEditUpdate() {
    window.location.href = '/updateExpense.html';
    }

    redirectEditProfile(){
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
