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
//              'redirectEditProfile',
              'getHTMLForSearchResults',
              'redirectCreateIncome',

            ];
        this.bindClassMethods(methodsToBind, this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displayIncomes);
        this.header = new Header(this.dataStore);

        this.urlParams = new URLSearchParams(window.location.search);

    }

    async clientLoaded() {
            const identity = await this.client.getIdentity();
            const profile = await this.client.getProfile(identity.email);
//              if (profile == null) {
//                  redirectEditProfile();
//                  document.getElementById("welcome").innerHTML = "<em>Welcome! First of all, let us create your profile!</em>";
//               }
            const income = await this.client.getAllIncome();
            this.dataStore.set("email", identity.email);
            this.dataStore.set('profile', profile);
            this.dataStore.set('incomes', income);

            this.displayIncomes();
            console.log(income);

        }



    mount() {

            document.getElementById('income-link').addEventListener('click', this.redirectAllExpenses);
            document.getElementById('logout').addEventListener('click', this.logout);
            document.getElementById('addInc').addEventListener('click', this.redirectCreateIncome);
            const deleteButtons = document.getElementsByClassName('delete-button-income');
                            for (let i = 0; i < deleteButtons.length; i++) {
                                deleteButtons[i].addEventListener('click', this.deleteEntryIncome.bind(this));
            }

            const urlParams = new URLSearchParams(window.location.search);
            const incomeId = urlParams.get('income');

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

            const deleteButtons = document.getElementsByClassName('delete-button-income');
            for (let i = 0; i < deleteButtons.length; i++) {
                const deleteButton = deleteButtons[i];
                deleteButton.removeEventListener('click', this.deleteEntryIncome);
                deleteButton.addEventListener('click', this.deleteEntryIncome.bind(this));
            }
        }

    getHTMLForSearchResults(searchResults) {
     console.log(searchResults , "from getHTMLForSearchResults");
            if (!searchResults || !searchResults.allIncomeList || searchResults.allIncomeList.length === 0) {
                return '<h4>No results found</h4>';
            }
            let html = "<div id = 'incomeEditList'>";
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
                    <a class="edit-button btn btn-warning rounded me-4 editIncome" href="updateIncome.html?income=${res.incomeId}">Edit</a>
                    <button class="delete-button-income btn btn-warning rounded me-4 deleteIncome" data-income-id="${res.incomeId}" data-id="777">Delete</button>
                    </td>
                    </div>
                </tr>`;
                }
                html+= "</div>";
                return html;
        }

 async deleteEntryIncome(event) {
   try {
     const incomeId = event.target.dataset.incomeId;
     console.log("IncomeId", incomeId);

     if (!incomeId) {
       console.error('Income ID not found in URL parameters.');
       return;
     }

     // Check if the user is authenticated
    const user = await this.client.getIdentity();
        if (!user) {
          throw new Error('Only authenticated users can delete an expense.');
        }

     // Delete the income entry
     await this.client.deleteIncome(incomeId);

     // Remove the income entry from the data store
     let income = this.dataStore.get('incomes');
     if (income) {
       let updatedIncome = income.allIncomeList.filter(
         (income) => income.incomeId !== incomeId
       );
       this.dataStore.set('incomes', { allIncomeList: updatedIncome });
     }

     // Re-render the income list
     this.displayIncomes();
   } catch (error) {
     console.error('Error deleting income:', error);
   }
 }

    redirectAllIncomes(){
        window.location.href = '/income.html';
    }

//    redirectEditProfile() {
//        window.location.href = '/createProfile.html';
//    }

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
