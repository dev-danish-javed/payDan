# PayDan - A GraphQL project

I have used this project to learn about GraphQL.

---

## GrpahQL Main Features
- Helps avoid overfetching and under fetching
- Compatiable with API and webscokets

---

## How ?
- It let's client structure the response
- Same class with multiple schema and accept and recieve different requests/responses

---

## Where to use ?
- Handling multiple clients
- Very helpful with SPA client
- Mobile Client ... basically any dynamic UI
- Microservices : Different services need different fields

---

### Understand better with an example
Imagine a service that shows wallet transaction history. We have two web app and a mobile app utlizing this service.

#### Mobile App
Mobile screen is small and hence less data can be displyed in a row. Imagine, it only displays 3 fields:
- Transaction Date
- Transaction Amount
- Transaction Status

#### Web App
Usually a laptop screen will have more space to display more data. Imagine its decided to display the 5 fields below:
- Transaction Date
- Trnsaction Amount
- Recieved from/Sent to
- Effective balance
- Transaction Status

#### The Problem
To serve these cient, our ms will return the transaction list with object of Transaction class. The means we are returning all the 5 fields to both. While web will utilize 
these fields mobile app with ignore the two fields. That means mobile is recieves useless data that consumes network bandwith.
Consider the wallet belongs to a shopkeeper who takes UPI. In real world the class can have _n_ number of other fields and that to with _n_ number of nesting.
Like payment method details, account number, notes, transaction id, platform fee etc.  
Now image how much data the mobile is fetching unnecessarily.

##### How would we have fixed it, the traditional way ?
Create different endpoints with different response classes while keeping the bussiness layer same. Sounds tidious and not so cool, right ?  

Here is what's cool

#### How would we fix it, the GraphQL way ?
We create just one endpoint with the same class. We create a schema out of it with only the fields we want to allow the fields.
Then while making request the clients can specify the fields they want to response to have and the serve will return exactly that.
So mobile can ask for only the three fields it want and web can ask for the 5 fields it wants from the same endpoint.

#### Another example
Imagine you are rendering the profile page for the wallet. On the top we want user's profile pic then email id and then below that we need wallet balance.  
In traditional setup, we'll have to make 2 seperate API calls
- one to get profile details
- other to get wallet balance

In the GrpahQL way, we'll make just one call
- We'll prepare payload with the two queries for profile and balance
- In one API call we'll get both details

In a page there can be mutiple UI elements that need different data from multiple different APIs. With GraphQL we can couple all the queries in a single payload and get 
data for the entire page in one go.
Imagine the amount of server hits and thus cloud bill we can save.

# Elements
- Schema
- input
- enum
- Query
- Mutation
- Subscription

> The app is hosted on [paydan.onrender.com](https://paydan.onrender.com/) Check that out. DO mind the initail load is slow for the free tier I'm using.
