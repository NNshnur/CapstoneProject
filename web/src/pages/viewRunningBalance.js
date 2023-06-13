import truckingClient from '../api/truckingClient';
import DataStore from '../util/DataStore';

class ViewRunningBalance {
  constructor() {
    this.dataStore = new DataStore();
    this.dataStore.addChangeListener(this.displayRunningBalance.bind(this));
    this.client = new truckingClient();

    this.clientLoaded = this.clientLoaded.bind(this);
    this.logout = this.logout.bind(this);
  }

  async clientLoaded() {
    const identity = await this.client.getIdentity();
    const profile = await this.client.getProfile(identity.email);
    const income = await this.client.getAllIncome();
    const expenses = await this.client.getAllExpenses();
    this.dataStore.set('email', identity.email);
    this.dataStore.set('profile', profile);
    this.dataStore.set('incomes', income);
    this.dataStore.set('expenses', expenses);
    this.displayRunningBalance();
  }

  mount() {
    document.getElementById('logout').addEventListener('click', this.logout);
    this.clientLoaded();
  }

  displayRunningBalance() {
    const expenses = this.dataStore.get('expenses');
    const incomes = this.dataStore.get('incomes');
    const tableBody = document.getElementById('runningbalance-list');

    if ((!incomes || !incomes.allIncomeList || incomes.allIncomeList.length === 0) && (!expenses || !expenses.allExpenseList || expenses.allExpenseList.length === 0)) {
      tableBody.innerHTML = '<tr><td colspan="5"><h4>No results found</h4></td></tr>';
      return;
    }

    const sortedEntries = [];

    // Add expenses to sorted entries
    if (expenses && expenses.allExpenseList && expenses.allExpenseList.length > 0) {
      sortedEntries.push(...expenses.allExpenseList.map(expense => ({ type: 'expense', entry: expense })));
    }

    // Add incomes to sorted entries
    if (incomes && incomes.allIncomeList && incomes.allIncomeList.length > 0) {
      sortedEntries.push(...incomes.allIncomeList.map(income => ({ type: 'income', entry: income })));
    }

    // Sort the entries by date
    sortedEntries.sort((a, b) => new Date(a.entry.date) - new Date(b.entry.date));

    let html = '';
    for (const entry of sortedEntries) {
      if (entry.type === 'expense') {
        html += `
          <tr>
            <td class="text-center">${entry.entry.truckId}</td>
            <td>${entry.entry.date}</td>
            <td></td>
            <td>${entry.entry.amount}</td>
            <td></td>
          </tr>
        `;
      } else if (entry.type === 'income') {
        html += `
          <tr>
            <td class="text-center">${entry.entry.truckId}</td>
            <td>${entry.entry.date}</td>
            <td>${entry.entry.grossIncome}</td>
            <td></td>
            <td></td>
          </tr>
        `;
      }
    }

    tableBody.innerHTML = html;
  }

  async logout() {
    await this.client.logout();
    if (!this.client.isLoggedIn()) {
      window.location.href = '/index.html';
    }
  }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
  const viewRunningBalance = new ViewRunningBalance();
  viewRunningBalance.mount();
};

window.addEventListener('DOMContentLoaded', main);