# Design Document

# TruckBizBook Design

## 1. Problem Statement

*Truck owner operators face challenges in effectively tracking their expenses and income, leading to difficulties 
in managing their financial operations. I would like to develop an application that helps truck owner operators track 
their expenses and income.  Users will be able to create new expense entries – like fuel, tolls, maintenance, permits, 
etc… They can also log their income from completed jobs. The application will provide reports to help monitor 
profitability and manage finances effectively.*

## 2. Top Questions to Resolve in Review

1. Shall I pre-define the categories for the variable expenses or not?
2. What data type to use for the date, shall I use a date picker (using a calendar)?
3. Shall I put the data for fixed and variable expenses together and call it something like - Operating expenses;
4. What if the owner operator has more than 1 truck;

## 3. Use Cases

* U1. As a user I want to create operating expense entry;
* U2. As a user I want to update operating expense entry;
* U3. As a user I want to view all operating expense;
* U4. As a user I want to 'soft delete' expense entry.
* U5. As a user I want to create an entry of income;
* U6. As a user I want to delete an entry of income;
* U7. As a user I want to update an entry of income;
* U8. As a user I want to view all income entries;
* U9. As a user I want to filter by category;
* U10. As a user I want to filter by truck;
* U11. As a user I want to filter by date;
* U12. As a user I want to see a graph representation of my expenses and income by month (dashboard);
* U13. As I user I want to generate report and export it in PDF format.

## 4. Project Scope

### 4.1. In Scope

1. As a user I want to create operating expense entry; 
2. As a user I want to update operating expense entry; 
3. As a user I want to view all operating expense; 
4. As a user I want to 'soft delete' expense entry. 
5. As a user I want to create an entry of income; 
6. As a user I want to delete an entry of income; 
7. As a user I want to update an entry of income; 
8. As a user I want to view all income entries; 
9. As a user I want to filter by category;
10. As a user I want to filter by date;
11. As a user I want to filter by truck;


### 4.2. Out of Scope

* Stretch Goal 1 - As a user I want to see a graph representation of my expenses and income by month (dashboard);
* Stretch Goal 2 - As I user I want to generate report and export it in PDF format.

# 5. Proposed Architecture Overview

_Describe broadly how you are proposing to solve for the requirements you described in Section 2. This may include class diagram(s) showing what components you are planning to build. You should argue why this architecture (organization of components) is reasonable. That is, why it represents a good data flow and a good separation of concerns. Where applicable, argue why this architecture satisfies the stated requirements._

# 6. API

## 6.1. Public Models

**Profile Model**
// To do 

**Expense Model**
// To do 

**Income Model**
// To do


## 6.2. Create an operating expense-entry endpoint
POST/expenses/create

## 6.3. Update an operating expense-entry endpoint
PUT/expenses/{id}

## 6.4. View all operating expenses endpoint
GET/expenses/all

## 6.5. Delete an operating expense-entry endpoint
DELETE/expenses/{id}

## 6.6. Create an income-entry endpoint
POST/incomes/create

## 6.7. Update an income-entry endpoint
PUT/incomes/{id}

## 6.8. View all income entries endpoint
GET/incomes/all

## 6.9. Delete an income-entry endpoint
DELETE/incomes/{id}

## 6.10. Filter expense by category endpoint
GET/expenses?category=nameOfCategoryNeeded

## 6.11. Filter expenses by date endpoint
GET/expenses?date=dateNeeded


# 7. Tables
`OperatingExpenseTable`
``` 
expenseId // partition key, string
truckId // string (sort key)
date // LocalDate
vendorName // string
category // enum
amount // double 
payment type // enum (or string)
```

`IncomeTable`
``` 
incomeId // partition key, string
truckId // string (sort key)
date // LocalDate
bedHeadMiles // double
loadedMiles // double
totalMmiles // double
grossIncome // double
costPerMile // double
profit // double
personalIncome // double
```

`CategoryIndex`
``` 
category // partition key, enum
truckId // string
expenseId // string
date //LocalDate
vendorName //string
amount //double
payment type/enum
```

`DateIndex`
``` 
date //partition key, LocalDate
truckId // string
expenseId // string
category // enum
vendorName //string
amount //double
payment type/enum
```

`TruckIndex`
``` 
truckId // partition key, string
expenseId // string
date // LocalDate
vendorName // string
category // enum
amount // double 
payment type // enum (or string)
```

# 8. Pages
 