import truckingClient from '../api/truckingClient';
import BindingClass from "../util/bindingClass";
import Header from '../components/truckingHeader';
import DataStore from "../util/DataStore";

class ViewProfile extends BindingClass {
    constructor() {
        super();
        const methodsToBind = [
        'clientLoaded',
        'mount',
      //  'redirectAllExpenses',
        'redirectProfile',
        'redirectAllIncome',
        'redirectRunningBalance',
        'logout',
        'addCompanyName'
      ]
        this.bindClassMethods(methodsToBind, this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

    async clientLoaded() {
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        if (profile == null) {
          redirectEditProfile();
          document.getElementById("welcome").innerHTML = "<em>Welcome! First of all, let us create your profile!</em>";
         }
        this.dataStore.set("email", identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('companyName', profile.profileModel.companyName);
//        console.log(JSON.stringify(this.dataStore));
        this.addCompanyName();
        console.log("addCompanyName() called")

    }




    /**
     * Add the header to the page and load the dannaClient.
     */
    mount() {
       // document.getElementById('expenses').addEventListener('click', this.redirectAllExpenses);
        document.getElementById('income').addEventListener('click', this.redirectAllIncome);
        document.getElementById('backToProfile').addEventListener('click', this.redirectProfile);
        document.getElementById('running-balance').addEventListener('click', this.redirectRunningBalance);
        document.getElementById('logout').addEventListener('click', this.logout);
        document.getElementById('coName').innerText = "Loading ...";
        this.client = new truckingClient();
        this.clientLoaded();

    }

 async addCompanyName() {

   const companyName = this.dataStore.get("companyName");
   console.log("Company Name is " + companyName);
   if (companyName == null) {
     document.getElementById("coName").innerText = "Something went wrong. Unable to load the company name.";
   } else {
     document.getElementById("coName").innerText = "You logged in as: " + companyName;
   }
 }

    redirectAllExpenses(){
            window.location.href = '/expenses.html';
        }

    redirectProfile() {
         window.location.href = '/profile.html';
        }

    redirectAllIncome(){
        window.location.href = '/income.html';
    }

    redirectRunningBalance(){
        window.location.href = '/runningbalance.html';
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
    const viewProfile = new ViewProfile();
    viewProfile.mount();
};

window.addEventListener('DOMContentLoaded', main)
