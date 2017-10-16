# Client

## Endpoints

### `/ - GET`

returns all available endpoints as JSON.

### `/loanRequest - POST`

Should be sent as a body in the format:

```json
{"ssn": "280492-1111", "loanAmount": 1000, "loanDuration": 365}
```