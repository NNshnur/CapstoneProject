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
          'redirectEditProfile',
          'redirectProfile',
          'redirectAllIncome',
          'redirectRunningBalance',
          'populateExpensesChart',
          'populateIncomeChart',
          'logout',
          'addCompanyName'
        ];
        this.bindClassMethods(methodsToBind, this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);

    }

    async clientLoaded() {
        const identity = await this.client.getIdentity();
        const profile = await this.client.getProfile(identity.email);
        if (profile == null) {
          this.redirectEditProfile();
          document.getElementById("welcome").innerHTML = "<em>Welcome! First of all, let us create your profile!</em>";
         }
        this.dataStore.set("email", identity.email);
        this.dataStore.set('profile', profile);
        this.dataStore.set('companyName', profile.profileModel.lastName);
        const expenses = await this.client.getAllExpenses();
        this.dataStore.set('expenses', expenses);
        const income = await this.client.getAllIncome();
        this.dataStore.set('incomes', income);

//        console.log(JSON.stringify(this.dataStore));
        this.addCompanyName();
        console.log("addCompanyName() called")

        this.populateExpensesChart();
        this.populateIncomeChart();
        console.log("method populateExpensesChart is being called");
        console.log("method populateIncomeChart is being called");

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
        document.getElementById('companyNameC').innerText = "Loading ...";
        this.client = new truckingClient();
        this.clientLoaded();

    }

async addCompanyName() {
  const profile = this.dataStore.get('profile');
  const lastName = profile && profile.profileModel && profile.profileModel.lastName;

  console.log("Profile: ", profile);
  console.log("Profile structure: ", JSON.stringify(profile));
  console.log("Company Name: ", lastName);

  if (lastName == null) {
    document.getElementById("companyNameC").innerText = "Something went wrong. Unable to load the company name.";
  } else {
    document.getElementById("companyNameC").innerText = "You logged in as: " + " " + lastName;
  }
}

async populateExpensesChart() {
  const expenses = this.dataStore.get("expenses");

  if (!expenses || !expenses.allExpenseList || expenses.allExpenseList.length === 0) {
    return;
  }

  const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

  const currentDate = new Date();
  const currentMonth = currentDate.getMonth();
  const currentYear = currentDate.getFullYear();

  const monthlyExpenses = Array(12).fill(0);

  for (const expense of expenses.allExpenseList) {

    const date = new Date(expense.date);
    const month = date.getMonth();
    const year = date.getFullYear();



    if (year === currentYear && month <= currentMonth) {
      monthlyExpenses[month] += expense.amount;
    }
  }

  const barColors = Array(12).fill("#203965");

  new Chart("myChart-expenses", {
    type: "bar",
    data: {
      labels: months,
      datasets: [{
        backgroundColor: barColors,
        data: monthlyExpenses

      }]
    },
     options: {
          scales: {
            yAxes: [{
              ticks: {
                min: 0,
                max: 5000,
                stepSize: 500, // Set the desired step size
              }
            }]
          },
          legend: { display: false },
          title: {
            display: true,
            text: "Expense YTD"
          }
        }
      });
    }

 async populateIncomeChart() {

   const incomes = this.dataStore.get("incomes");

   if (!incomes || !incomes.allIncomeList || incomes.allIncomeList.length === 0) {
     return;
   }

   const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

   const currentDate = new Date();
   const currentMonth = currentDate.getMonth();
   const currentYear = currentDate.getFullYear();

   const monthlyIncomes = Array(12).fill(0);

   for (const income of incomes.allIncomeList) {
     const date = new Date(income.date);
     const month = date.getMonth();
     const year = date.getFullYear();

     if (year === currentYear && month <= currentMonth) {
       monthlyIncomes[month] += income.grossIncome;
     }
   }

   const barColors = Array(12).fill("#FEBF0E");

   new Chart("myChart-income", {
     type: "bar",
     data: {
       labels: months,
       datasets: [{
         backgroundColor: barColors,
         data: monthlyIncomes
       }]
     },
     options: {
               scales: {
                 yAxes: [{
                   ticks: {
                     min: 0,
                     max: 15000,
                     stepSize: 500, // Set the desired step size
                   }
                 }]
               },
       legend: { display: false },
       title: {
         display: true,
         text: "Income YTD"
       }
     }
   });
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

    redirectEditProfile() {
        window.location.href = '/createProfile.html';
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
