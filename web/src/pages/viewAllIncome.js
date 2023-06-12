import truckingClient from '../api/truckingClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";

class ViewAllIncome extends BindingClass {
    constructor() {
        super();
         const methodsToBind = [
              'clientLoaded',
              'mount',
              'redirectAllIncomes',
              'logout',
              'displayIncomes',
              'getHTMLForSearchResults',
              'redirectCreateIncome',

            ];
        this.bindClassMethods(methodsToBind, this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayIncomes);
        this.header = new Header(this.dataStore);

    }

    async clientLoaded() {
            const identity = await this.client.getIdentity();
            const profile = await this.client.getProfile(identity.email);
            const income = await this.client.getAllIncome();
            this.dataStore.set("email", identity.email);
            this.dataStore.set('profile', profile);
            this.dataStore.set('incomes', income);

            this.displayIncomes();
            console.log(income);

        }

    mount() {
            document.getElementById('logout').addEventListener('click', this.logout);
            document.getElementById('addInc').addEventListener('click', this.redirectCreateIncome);
            this.client = new truckingClient();
            this.clientLoaded();
        }

    displayIncomes() {
    let incomes = this.dataStore.get("incomes");
    console.log(incomes, "from displayIncome");
    if (incomes == null) {
        document.getElementById("income-list").innerText = "No Income entries found";
    }
    document.getElementById("income-list").innerHTML = this.getHTMLForSearchResults(incomes);
   }

    getHTMLForSearchResults(searchResults) {
     console.log(searchResults , "from getHTMLForSearchResults");
            if (!searchResults || !searchResults.allIncomeList || searchResults.allIncomeList.length === 0) {
                return '<h4>No results found</h4>';
            }
            let html = "";
            for (const res of searchResults.allIncomeList) {

                html += `
                <tr>
                    <td class="text-center">
                        ${res.incomeId}
                    </td>
                    <td>
                         ${res.truckId}
                    </td>
                    <td>
                         ${res.date}
                    </td>
                    <td>
                         ${res.deadHeadMiles}
                    </td>
                    <td>
                          ${res.loadedMiles}
                     </td>
                    <td>
                          ${res.totalMiles}
                    </td>
                    <td>
                          ${res.grossIncome}
                    </td>
                    <td>
                         ${res.ratePerMile}
                    </td>
                    <td>
                    <div class="d-flex justify-content-center">
                    <a class="edit-button btn btn-warning rounded me-4 editExpense" href="updateIncome.html?income=${res.incomeId}">Edit</a>
                    <button class="delete-button btn btn-warning rounded me-4 deleteIncome" data-expense-id="${res.incomeId}" data-id="777">Delete</button>
                    </td>
                    </div>
                </tr>`;
                }
            return html;
        }

    redirectAllIncomes(){
        window.location.href = '/income.html';
    }

    redirectCreateIncome() {
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
    const viewAllIncome = new ViewAllIncome();
    viewAllIncome.mount();
};

window.addEventListener('DOMContentLoaded', main);
