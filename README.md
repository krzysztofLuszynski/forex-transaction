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
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-07\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one invalid Spot transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one valid Forward transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-06\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one invalid Forward transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one valid VanillaOption transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one valid VanillaOption transactionDTO
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA4\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN1\",\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-23\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2020-08-24\",\"legalEntity\":\"UBS AG1\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate

# All actuator endpoints                   
curl --request GET http://localhost:8080/forex-transaction/actuator
# Actuator health page
curl --request GET http://localhost:8080/forex-transaction/actuator/health
# Actuator request metrics
curl --request GET http://localhost:8080/forex-transaction/actuator/metrics/http.server.requests                             
```

## Solution discussion
#### Decided to return 200 when no validation errors, 400 when there are validation errors
#### Decided to return number of all transactionDTOS along with list of validation errors
#### Because transactionDTOS do not have unique id (which in my opinion is bad and should be corrected after discussion with product owner) I decided to put transactionDTO number in every error and it matches number of transactionDTO in original request
#### Errors are returned in sequence one transaction per another, however inside of one transaction if multiple errors they are sorted based on message by case insensitive order
#### Based on https://www.investopedia.com/terms/v/vanillaoption.asp - changed excercise to exercise in vanilla option - should be corrected after discussion with product owner
#### No value date for vanilla options in attached demo data - applying this field only to spots and forwards, along with validations
#### I do not like CcyPair - I would introduce currency1 and currency2 - the same like for amount1 and amount2 - I had to introduce separate constraint because of that ! - should be corrected after discussion with product owner
#### Also naming like PayCcy, PremiumCcy is poor - I would use full names PaymentCurrency and PremiumCurrency - should be corrected after discussion with product owner
#### IT in purpose are having so many cases (some of them are tested in junits for validators), because these tests are still fast and they are the contract of validation
#### Test coverage is 96% lines, 92% of methods - not covered some lombok annotations
#### I think this task is far too much for simple job interview - I spent more then 17 hours on this task and did not finished with dates for currencies and extra activities
#### In my opinion there should be much less validations, then I can focus also on extra activities
#### There is statement about current date - I think it would be nice to introduce validation that date can not be past/future from current date, but current date could be parametrized - no such task here...
#### Such task should focus on different aspects, not just crazy implementing many rules with tests !

## TODO
### All types of trades:
#### value date cannot be before trade date - DONE for spots and forwards 
#### value date cannot fall on weekend or non-working day for currency - DONE only for weekends
#### if the counterparty is one of the supported ones - DONE
#### validate currencies if they are valid ISO codes (ISO 4217) - DONE

### Spot, Forward transactionDTOS:
#### validate the value date against the product type - DONE, checked if it is not null for spot and forward

### Options specific:
#### the style can be either American or European - DONE
#### American option style will have in addition the exerciseStartDate, which has to be after the trade date but before the expiry date - DONE
#### expiry date and premium date shall be before delivery date - DONE

### Extra activity
#### Expose performance metrics of the application including: number of requests processed, processing time (min, max, average quantile95) - enabled only actuator, should implement https://docs.spring.io/spring-metrics/docs/current/public/prometheus but do not have more time for that
#### Describe and present the approach for providing high availability of the service and its scalability - The simplest change would be to try to use parallel streams for large requests, so we can use many threads, the only point is that first we need to assign each transaction number, so we will have them in the output just like with current solution. Also all logs with errors and transactions should be analysed and probably removed in case of many entries.
#### Create online documentation of the REST API exposed by the service - I would use openAPI, something like that: https://swagger.io/specification/ we were using that in former company for contact first approach

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

### Moving to java beans validations - 4h
#### added basic annotation along with tests for all transactions
#### Added date format constraint to all dates field in all transactions
#### Fixed typo excercise to exercise
#### Implemented firstDateBeforeSecondDate constraint and removed old rules
#### Used it for value date and trade date validation

### Other validatations - 3h30m
#### Added validations for dates for AMERICAN vanilla options along with tests
#### Added validations for currency for single value along with tests
#### Added validations for CcyPair along with tests
#### Added validation for non working days along with tests

### Final cleanup - 1h
#### Enabling actuator
#### Cleaning README
#### Added sorting messages for one transaction based on message


