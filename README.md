# LoanBroaker

[![Stories in Ready](https://badge.waffle.io/hilleer/LoanBroaker.png?label=ready&title=Ready)](http://waffle.io/hilleer/LoanBroaker)

Repository for System integration Loan broker project on Cphbusiness, PBA in software development.

**Table of contents.**

- [Important links](#important-links)
- [Authors](#authors)
- [References](#references)
- [Banks](#banks)
- [Translators](#translators)
- [Notes](#notes)

## Important links

## Authors

[Frederik Larsen](https://github.com/lalelarsen)

[Anders Bjergfelt](https://github.com/andersbjergfelt)

[Emil Gräs](https://github.com/emilgras)

[Daniel Hillmann](https://github.com/hilleer)

## References

Original project [description](https://github.com/datsoftlyngby/soft2017fall-system-integration-teaching-material/blob/master/assignments/LoanBrokerProject.pdf).

## Run the project

1. Run GetCreditScore (Main)
2. Run GetBanks (Main)
3. Run RecipientList (Main)
4. Run Translator (Main)
5. Run Nomalizer (Main)
6. Run TingGodRabbitMQBank (Main)
7. Run GetCreditScore (Dummy)

## Banks

List of bank id's:

- `bank-lån-and-spar`
- `bank-jyske-bank`
- `bank-nordea`
- `bank-danske-bank`

## Translators.

**Node translators:**

- TranslatorJson
- TranslatorJsonGodeTing

In this dir run `npm install`.

**Production:** in this dir, run `npm run start`. Will start two processes that each start their respective translator in production.

**Development:** In this dir, run `npm run dev`. Will start two proccesses that each start teir respective translator in development mode.

## Notes

### HashMap vs. JSON

Why did we choose to pass HashMap from process to process, and why did we regret it? 

### Better namings

Could we have named our proccesses better, and how would this possibly have helped us? 

- Structure.
- Which services need what?

### Bottle necks

- If you're born later in the year/month so that your ssn becomes too large for a integer to be (bigger than 32-bit).

- If one service fails / throws an error, the other procceses/service will not know (mention how this could be handled).