
# Expense Limit API

This is a test task for Solva.kz. The aim is to write an API 
that receives client transactions and gives back an information
about client's exceeded limits and transactions.




## Installation and Deployment

To clone the repository with this project run

```bash
 $ git clone https://github.com/yanalexn/Expense-Limit-API
```

Then, you need to create a database (DB) and a user on 
MySQL server. To do this simply run the following MySQL script

```mysql
CREATE database exp_lim;
CREATE user 'yanalexn'@'%' identified by 'yan';
GRANT ALL ON exp_lim.* TO 'yanalexn'@'%';
```

Finally, you can run the main method in ExpenseLimitApiApplication 
class. Liquibase will automatically create all sufficient tables.


## Usage/Examples

Before the start, let us fill the DB with Account and KztUsd 
values

```mysql
INSERT INTO exp_lim.kzt_usd (exchange_rate, datetime) VALUES (0.0021000000, CURRENT_DATE);
INSERT INTO exp_lim.account (account_number) VALUES (1234567890);
INSERT INTO exp_lim.account (account_number) VALUES (2234567890);
```

The application receives new transactions from the bank via 
GraphQL API. To access this part of the web layer go the link

```
http://localhost:8080/graphql
```
or
```
http://localhost:8080/graphiql
```
with the following request body
```graphql
mutation {
  createTransaction(sum: 10000, currencyShortname: "KZT", datetime: "2022-12-16 12:38:07+06", 
    expenseCategory: "product", accountFromNumber: 1234567890, accountToNumber: 2234567890){
	id
    sum
    currencyShortname
    expenseCategory
    accountFrom {
      accountNumber
    }
    accountTo {
      accountNumber
    }
    limitExceeded
    limitRemaining
  }
}

```
The result should be the following
```json
{
  "data": {
    "createTransaction": {
      "id": "1",
      "sum": 10000,
      "currencyShortname": "KZT",
      "expenseCategory": "product",
      "accountFrom": {
        "accountNumber": 1234567890
      },
      "accountTo": {
        "accountNumber": 2234567890
      },
      "limitExceeded": true,
      "limitRemaining": -21
    }
  }
}
```
The application can interact with the bank client service 
receiving new expense limits and sending back the list of all 
exceeded limits and transactions for a particular client. The 
interaction with a client is orginized through REST API. 

To post a new limit run the following url
```url
http://localhost:8080/client/post
```
with application/json body
```json
{
    "sum": 10,
    "currencyShortname": "USD",
    "expenseCategory": "service",
    "accountNumber": 1234567890
}
```
To get all exceeded limits and transactions go to
```
localhost:8080/client/get/1234567890
```
where the number after /get is the account number of a client. 
The response will be 
```json
{
    "expenseLimitDto": {
        "id": 1,
        "sum": 0.0,
        "currencyShortname": "USD",
        "datetime": "2022-12-01 00:00:00+06",
        "expenseCategory": "product"
    },
    "transactionDto": {
        "id": 1,
        "sum": 10000.0,
        "currencyShortname": "KZT",
        "datetime": "2022-12-01 12:35:07+06",
        "expenseCategory": "product",
        "accountFromNumber": 1234567890,
        "accountToNumber": 2234567890,
        "limitExceeded": true,
        "limitRemaining": -21.0
    }
}
```
Note that this application automatically creates a new 
expense limit whenever it encounters a transaction and there is
no already existing limit for a given type of expense expense category
in this month. The new limit contains the following parameters 
sum = 0, datetime = the beginning of this month, 
expenseCategory = the same as of the transaction and 
currencyShortname = "USD" by default. 

