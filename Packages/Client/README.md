# Client

- [Endpoints](#endpoints)
- [How to](#how-to)

## Endpoints

### `/ - GET`

returns all available endpoints as JSON.

### `/loanRequest - POST`

Should be sent as a body in the format:

```json
{"ssn": "280492-1111", "loanAmount": 1000, "loanDuration": 3}
```

`ssn`: the ssn of the person making the request. Should be a string.
`loanAmount`: the amount to loan. Should be a number.
`loanDuration`: the duration for the loan in years. Should be a number. 

### `/loanRequest/ssn - GET`

Will return loans registered for that ssn in JSON.

## How to

To run the client you need to have node installed.

When node is installed, you should install all dependencies - this is done with the command `npm install`.

The client is a REST service where you can post request for loan providers, and get a response to this. See [endpoints](#endpoints).

1. Post a request to the service. This is done by sending a POST to `/loanRequest` with a json body in the format `{"ssn": "180492-1111", "loanAmount": 1000.1, "loanDuration": 3}`.
2. Get a response for the request you made before. This is done by sending a GET request to `/loanRequest/:ssn` where ssn is a parameter, and should be the ssn you used in the post.

It's not possible to use the POST endpoint if you've already made one that you haven't yet reviewed by the GET endpoint.
Also, it's not possible to use the GET endpoint unless you've already made a request by the POST endpoint.

| Endpoint           | Method | Description                                                          | Paramters | Result               |
|--------------------|--------|----------------------------------------------------------------------|-----------|----------------------|
| `/loanRequest`     | POST   | Posts a request to get the best loan of available providers          |           | *Success* or *error* |
| `/loanRequst/:ssn` | GET    | Request result of the POST endpoint - that is the best loan provider | `ssn`     | Json of the result   |
| `/`                | GET    | Returns an object/list of all available endpoints with descriptions  |           | json                 |

### What could have been done different on the client

We have implemented an offline map of ssn's that be requested loans for, to allow users only to request loans once before they can do it again. In a real-world project this should have been done differently, as the list will reset every time the servers restarts.

Also, the response to the user should have probably have been delivered differently. It's a bit hard to wait for an answer in the browser, as it has to keep loading untill it receives any messages. Our solution was to make a get-request to an endpoint with the ssn, to retrieve the best provider. A proper solution could have been to send an e-mail to the user with the best loan provider(s).

also, obviously there should have been an actual user interface, and not just some rest endpints - those would be a bit hard to non-programmer users.