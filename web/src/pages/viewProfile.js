import truckingClient from '../api/truckingClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount','redirectEditProfile','redirectAllExpenses','delay',
                                       'redirectCreateExpense','redirectAllIncome', 'redirectRunningBalance','logout','addName'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

    async clientLoaded() {
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        this.dataStore.set("email", identity.email);
       // this.dataStore.set("myPersonalEventIds", profile.profileModel.events);
        this.dataStore.set('profile', profile);
       // const expenses = await this.client.getAllExpenses(); -> expenses page
        // this.dataStore.set('expenses', expenses.allExpensesList); -> expenses page
        this.dataStore.set('firstName', profile.profileModel.firstName);
        this.dataStore.set('lastName', profile.profileModel.lastName);
        console.log(JSON.stringify(this.dataStore));
//        this.addEvents(); -> to Expenses page + method itself
        this.addName();

    }
    /**
     * Add the header to the page and load the dannaClient.
     */
    mount() {
        document.getElementById('profilePic').addEventListener('click', this.redirectEditProfile);
        document.getElementById('expenses').addEventListener('click', this.redirectAllExpenses);
        document.getElementById('income').addEventListener('click', this.redirectAllIncome);
        document.getElementById('running-balance').addEventListener('click', this.redirectRunningBalance);
//        document.getElementById('allExpenses').addEventListener('click', this.redirectAllExpenses); -> move to Expense page
//        document.getElementById('createExpense').addEventListener('click', this.redirectCreateExpense); -> move to Expense Page
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('names').innerText = "Loading ...";

        this.client = new truckingClient();
        this.clientLoaded();
    }
    async delay(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }


    async addName(){
        const fname = this.dataStore.get("firstName");
        const lname = this.dataStore.get("lastName");
        if (fname == null) {
            document.getElementById("names").innerText = "Something went wrong. Unable to load the name.";
        }
        document.getElementById("names").innerText = fname + " " + lname;
    }

    redirectEditProfile(){
        window.location.href = '/createProfile.html';
    }

    redirectProfile() {
         window.location.href = '/profile.html';
        }

//    redirectAllExpenses(){     -> move to Expense page
//        window.location.href = '/expenses.html';
//    }

//    redirectCreateExpense(){    -> move to Expense page + create html
//        window.location.href = '/createExpense.html';
//    }
    redirectAllIncome(){
        window.location.href = '/income.html';
    }

    redirectRunningBalance(){
        window.location.href = '/runningbalance.html';
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
    const viewProfile = new ViewProfile();
    viewProfile.mount();
};

window.addEventListener('DOMContentLoaded', main)
