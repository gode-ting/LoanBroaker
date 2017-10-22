# Client

**Table of contents:**

- [Endpoints](#endpoints)
- [How to](#how-to)

## Endpoints

### `/ - GET`

returns all available endpoints as JSON.

### `/loanRequest - POST`

Should be sent as a body in the format:

```json
{"ssn": "280492-1111", "loanAmount": 1000, "loanDuration": 365}
```

### `/loanRequest/ssn - GET`

Will return loans registered for that ssn in JSON.

## How to

To run the client you need to have node installed.

When node is installed, you should install all dependencies - this is done with the command `npm install`.

The client is a REST service where you can post request for loan providers, and get a response to this. See [endpoints](#endpoints).

1. Post a request to the service. This is done by sending a POST to `/loanRequest` with a json body in the format `{"ssn": "180492-1111", "loanAmount": 1000.1, "loanDuration": 300}`.
2. Get a response for the request you made before. This is done by sending a GET request to `/loanRequest/:ssn` where ssn is a parameter, and should be the ssn you used in the post.

It's not possible to use the POST endpoint if you've already made one that you haven't yet reviewed by the GET endpoint.
Also, it's not possible to use the GET endpoint unless you've already made a request by the POST endpoint.