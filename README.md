# forex-transactionDTO
Forex transactionDTO. Simple REST service with transactionDTO validations.

Application for coding task.

For details about the task see CodingTaskDescription.pdf in the same folder as this README.md

## Installation
```bash
# Run from main project directory to build
gradle build
# Run from main project directory to run
gradle bootRun
```

## Usage

```bash
# Example of validation of one valid Spot transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transactionDTO/validate
# Example of validation of one invalid Spot transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transactionDTO/validate
# Example of validation of one valid Forward transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-22\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transactionDTO/validate
# Example of validation of one invalid Forward transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transactionDTO/validate
# Example of validation of one valid VanillaOption transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transactionDTO/validate
# Example of validation of one valid VanillaOption transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN1\",\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-23\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2020-08-24\",\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transactionDTO/validate
                    
```

## Solution discussion
### Because I have not much time for this task I decided to take following assumptions:
#### For such endpoint it would be good to validate much more for example date formats, if fields are present or not etc, but because there is nothing such stated in the task I am skipping that
#### Also because of that I decided to return 200 when no validation errors, 400 when there are validation errors
#### Decided to return number of all transactionDTOS along with list of validation errors
#### Because transactionDTOS do not have unique id (which in my opinion is bad) I decided to put transactionDTO number in every error and it matches number of transactionDTO in original request
#### Based on https://www.investopedia.com/terms/v/vanillaoption.asp - changed excercise to exercise in vanilla option

## TODO
### Refactor code of validator
### Test in junits all kinds of errors for validator
### Implement custom validators for date before another date
### All types of trades:
#### value date cannot be before trade date
#### value date cannot fall on weekend or non-working day for currency
#### if the counterparty is one of the supported ones - DONE
#### validate currencies if they are valid ISO codes (ISO 4217)

### Spot, Forward transactionDTOS:
#### validate the value date against the product type - DONE, checked if it is not null for spot and forward

### Options specific:
#### the style can be either American or European - DONE
#### American option style will have in addition the excerciseStartDate, which has to be after the trade date but before the expiry date
#### expiry date and premium date shall be before delivery date - DONE

### Assumptions:
#### Current date is 09.10.2020 - why do we need this assumption ???
#### Supported counterparties (customers) are : YODA1, YODA2 - DONE
#### Only one legal entity is used: UBS AG - DONE

## Timelog

### Environment preparation and blank project creation - 30m
#### Created new public repository on github.
#### Generation of blank project
#### Added validate REST to project

### Domain objects for transactionDTOS - 2h30m
#### Created basic infrastructure for tests in the code
#### Created object for Spot along with first test
#### Created object for Forward along with first test
#### Created object for VanillaOption along with first tests
#### Changed packages, project name, repository name to pattern forex.transactionDTO
#### Updated README with basic instructions and discussion of the solution

### Basics of validation framework - 2h30m
#### Created validation error, validation rule, validation context, transactionDTO validator
#### Created first general rule for supported customers along with tests
#### Created first spot rule for valueDate along with tests
#### Created spring configuration for spot transactionDTO validator
#### Updated integration tests for case with invalid spot transactionDTO
#### Updated README with case for invalid spot transactionDTO

### Added first rules for forward along with tests - 30m
#### Created spring configuration for forward transactionDTO validator
#### Updated integration tests for case with invalid forward transactionDTO
#### Updated README with case for invalid forward transactionDTO

### Added first rules for vanilla option along with tests - 30m
#### Implemented ExpiryDateBeforeDeliveryDateValidationRule along with tests
#### Created spring configuration for vanilla option transactionDTO validator
#### Updated integration tests for case with invalid vanilla option transactionDTO
#### Updated README with case for invalid vanilla option transactionDTO

### Implementation of more rules and refactorings - 2h
#### Refactored messages of existing validation rules to be better reusable
#### Added PremiumDateBeforeDeliveryDateValidationRule along with tests
#### Added SupportedLegalEntityValidationRule along with tests
#### Added SupportedStyleValidationRule along with tests

### Introduced java-beans validations - 2h
#### Added annotations to Transaction object
#### Removed two general validation rules along with tests
#### Updated integration tests
#### Introduced dto object instead of domain objects
#### Returning 200 or 400 (previously only 200)
#### Added annotation to AbstractSpotForwardTransaction object
#### Removed general rule for valueDate along with tests
#### Added annotations to VanillaOptionTransactionDTO object
#### Removed rule for style along with tests

### Moving to java beans validations - 2h30m
### added basic annotation along with tests for all transactions
### Added date format constraint to all dates field in all transactions
### Fixed typo excercise to exercise