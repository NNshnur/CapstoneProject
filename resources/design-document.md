# Design Document

## Instructions

## TruckBizBook Design

## 1. Problem Statement

Truck owner operators face challenges in effectively tracking their expenses and income, leading to difficulties 
in managing their financial operations. I would like to develop an application that helps truck owner operators track 
their expenses and income.  Users will be able to create new expense entries – like fuel, tolls, maintenance, permits, 
etc… They can also log their income from completed jobs. The application will provide reports to help monitor 
profitability and manage finances effectively.

## 2. Top Questions to Resolve in Review

1. Shall I pre-define the categories for the variable expenses or not?
2. What data type to use for the date, shall I use a date picker (using a calendar)?
3. Shall I put the data for fixed and variable expenses together and call it something like - Operating expenses;
4. What if the owner operator has more than 1 truck;

## 3. Use Cases

U1. As a user I want to create operating expense entry;
U2. As a user I want to update operating expense entry;
U3. As a user I want to view all operating expense;
U4. As a user I want to 'soft delete' expense entry.
U5. As a user I want to create an entry of income;
U6. As a user I want to delete an entry of income;
U7. As a user I want to filter by category;
U8. As a user I want to filter by truck;
U9. As a user I want to filter by date;
U10. As a user I want to see a graph representation of my expenses and income by month (dashboard);
U11. As I user I want to generate report and export it in PDF format.

## 4. Project Scope

_Clarify which parts of the problem you intend to solve. It helps reviewers know what questions to ask to make sure you are solving for what you say and stops discussions from getting sidetracked by aspects you do not intend to handle in your design._

### 4.1. In Scope

_Which parts of the problem defined in Sections 1 and 2 will you solve with this design? This should include the base functionality of your product. What pieces are required for your product to work?_

_The functionality described above should be what your design is focused on. You do not need to include the design for any out of scope features or expansions._

### 4.2. Out of Scope

_Based on your problem description in Sections 1 and 2, are there any aspects you are not planning to solve? Do potential expansions or related problems occur to you that you want to explicitly say you are not worrying about now? Feel free to put anything here that you think your team can't accomplish in the unit, but would love to do with more time._

_The functionality here does not need to be accounted for in your design._

# 5. Proposed Architecture Overview

_Describe broadly how you are proposing to solve for the requirements you described in Section 2. This may include class diagram(s) showing what components you are planning to build. You should argue why this architecture (organization of components) is reasonable. That is, why it represents a good data flow and a good separation of concerns. Where applicable, argue why this architecture satisfies the stated requirements._

# 6. API

## 6.1. Public Models

_Define the data models your service will expose in its responses via your *`-Model`* package. These will be equivalent to the *`PlaylistModel`* and *`SongModel`* from the Unit 3 project._

## 6.2. _First Endpoint_

_Describe the behavior of the first endpoint you will build into your service API. This should include what data it requires, what data it returns, and how it will handle any known failure cases. You should also include a sequence diagram showing how a user interaction goes from user to website to service to database, and back. This first endpoint can serve as a template for subsequent endpoints. (If there is a significant difference on a subsequent endpoint, review that with your team before building it!)_

_(You should have a separate section for each of the endpoints you are expecting to build...)_

## 6.3 _Second Endpoint_

_(repeat, but you can use shorthand here, indicating what is different, likely primarily the data in/out and error conditions. If the sequence diagram is nearly identical, you can say in a few words how it is the same/different from the first endpoint)_

# 7. Tables

_Define the DynamoDB tables you will need for the data your service will use. It may be helpful to first think of what objects your service will need, then translate that to a table structure, like with the *`Playlist` POJO* versus the `playlists` table in the Unit 3 project._

# 8. Pages

_Include mock-ups of the web pages you expect to build. These can be as sophisticated as mockups/wireframes using drawing software, or as simple as hand-drawn pictures that represent the key customer-facing components of the pages. It should be clear what the interactions will be on the page, especially where customers enter and submit data. You may want to accompany the mockups with some description of behaviors of the page (e.g. “When customer submits the submit-dog-photo button, the customer is sent to the doggie detail page”)_