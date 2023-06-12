import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the DannaAPIService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class TruckingClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity','login', 'logout', 'getProfile', 'getAllExpenses', 'createExpense', 'createProfile','updateProfile',
        'updateExpense', 'deleteExpense', 'getAllIncome', 'createIncome', 'updateExpense', 'isLoggedIn'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    async isLoggedIn(){
        return this.authenticator.isUserLoggedIn();
    }
    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {

        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }
            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the profile for the given ID.
     * @param id Unique identifier for a profile
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The profile's metadata.
     */
    async getProfile(id, errorCallback) {
        try {
        console.log(id + " id");
            const token = await this.getTokenOrThrow("Only authenticated users can view a profile.");
            const response = await this.axiosClient.get(`profiles/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

   async getAllExpenses(errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can get all expenses.");
                const response = await this.axiosClient.get(`expenses/all`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                return response.data;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
        }

    async createProfile(companyName, firstName, lastName, truckId, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can create a profile.");
                const response = await this.axiosClient.post(`profiles/create`, {
                    companyName: companyName,
                    firstName: firstName,
                    lastName: lastName,
                    truckId: truckId,

                }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });
                console.log(response.data);
                return response.data;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
        }

    async updateProfile(id, companyName, firstName, lastName, truckId, errorCallback) {
                try {
                    const token = await this.getTokenOrThrow("Only authenticated users can update a profile.");
                    const response = await this.axiosClient.put(`profiles/${id}`, {
                        companyName: companyName,
                        firstName: firstName,
                        lastName: lastName,
                        truckId: truckId,
                    }, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'application/json'
                        }
                    });
                    return response.data;
                } catch (error) {
                    this.handleError(error, errorCallback)
                }
            }
    async createExpense(truckId, vendorName, category, date, amount, paymentType, errorCallback) {
                    try {
                        const token = await this.getTokenOrThrow("Only authenticated users can create an expense.");
                        const response = await this.axiosClient.post(`expenses/create`, {
                            truckId: truckId,
                            vendorName: vendorName,
                            category: category,
                            date: date,
                            amount: amount,
                            paymentType: paymentType
                        }, {
                            headers: {
                                Authorization: `Bearer ${token}`,
                                'Content-Type': 'application/json'
                            }
                        });
                        return response.data;
                    } catch (error) {
                        this.handleError(error, errorCallback);
                    }
                }
    async updateExpense(id, truckId, vendorName, category, date, amount, paymentType, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update expenses.");
            const response = await this.axiosClient.put(`expenses/${id}`, {
                truckId: truckId,
                vendorName: vendorName,
                category: category,
                date: date,
                amount: amount,
                paymentType: paymentType,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


   async deleteExpense(expenseId, errorCallback) {
       try {
           const token = await this.getTokenOrThrow("Only authenticated users can remove an expense.");
           console.log('Token:', token); // Log the value of the token

           const response = await this.axiosClient.delete(`expenses/${expenseId}`, {
               headers: {
                   Authorization: `Bearer ${token}`,
                   'Content-Type': 'application/json'
               }
           });


           console.log("response is " + response);

           return response.data;
       } catch (error) {
           this.handleError(error, errorCallback);
       }
   }

   async getAllIncome(errorCallback) {
               try {
                   const token = await this.getTokenOrThrow("Only authenticated users can get all incomes.");
                   const response = await this.axiosClient.get(`incomes/all`, {
                       headers: {
                           Authorization: `Bearer ${token}`,
                           'Content-Type': 'application/json'
                       }
                   });
                   return response.data;
               } catch (error) {
                   this.handleError(error, errorCallback)
               }
           }

   async createIncome(truckId, date, deadHeadMiles, loadedMiles, grossIncome, errorCallback) {
                       try {
                           const token = await this.getTokenOrThrow("Only authenticated users can create an expense.");
                           const response = await this.axiosClient.post(`incomes/create`, {
                               truckId: truckId,
                               date: date,
                               deadHeadMiles: deadHeadMiles,
                               loadedMiles: loadedMiles,
                               grossIncome: grossIncome,
                           }, {
                               headers: {
                                   Authorization: `Bearer ${token}`,
                                   'Content-Type': 'application/json'
                               }
                           });
                           return response.data;
                       } catch (error) {
                           this.handleError(error, errorCallback);
                       }
           }

   async updateIncome(id, truckId, date, deadHeadMiles, loadedMiles, grossIncome, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update income.");
            const response = await this.axiosClient.put(`incomes/${id}`, {
                truckId: truckId,
                date: date,
                deadHeadMiles: deadHeadMiles,
                loadedMiles: loadedMiles,
                grossIncome: grossIncome,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

//   async deleteIncome(incomeId, errorCallback) {
//       try {
//           const token = await this.getTokenOrThrow("Only authenticated users can remove an expense.");
//           console.log('Token:', token); // Log the value of the token
//
//           const response = await this.axiosClient.delete(`incomes/${incomeId}`, {
//               headers: {
//                   Authorization: `Bearer ${token}`,
//                   'Content-Type': 'application/json'
//               }
//           });


//           console.log("response is " + response);
//
//           return response.data;
//       } catch (error) {
//           this.handleError(error, errorCallback);
//       }
//   }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error("errorFromApi " + errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
        console.error("errorCallback " + errorCallback)
            errorCallback(error);
        }
    }
}
