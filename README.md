# forex-transaction
Forex transaction. Simple REST service with transaction validations.

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
# Example of validation of one valid Spot transaction
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"Spot\",\"direction\":\"BUY\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-15\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one valid Forward transaction
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA2\",\"ccyPair\":\"EURUSD\",\"type\":\"Forward\",\"direction\":\"SELL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"valueDate\":\"2020-08-22\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
# Example of validation of one valid VanillaOption transaction
curl --header "Content-Type: application/json" --request POST --data "[{\"customer\":\"YODA1\",\"ccyPair\":\"EURUSD\",\"type\":\"VanillaOption\",\"style\":\"EUROPEAN\",\"direction\":\"BUY\",\"strategy\":\"CALL\",\"tradeDate\":\"2020-08-11\",\"amount1\":1000000.00,\"amount2\":1120000.00,\"rate\":1.12,\"deliveryDate\":\"2020-08-22\",\"expiryDate\":\"2020-08-19\",\"payCcy\":\"USD\",\"premium\":0.20,\"premiumCcy\":\"USD\",\"premiumType\":\"%USD\",\"premiumDate\":\"2020-08-12\",\"legalEntity\":\"UBS AG\",\"trader\":\"Josef Schoenberger\"}]" http://localhost:8080/forex-transaction/validate
```

## Solution discussion
### Because I have not much time for this task I decided to take following assumptions:
#### For such endpoint it would be good to validate much more for example date formats, if fields are present or not etc, but because there is nothing such stated in the task I am skipping that
#### Because full validation would be on the objects DTO, I am not doing that that's why I changed to use domain objects in the request and do not use mapping between DTO and domain objects - in normal application with more time I would go with broader validation of the request like stated in point before
#### That's why my domain objects contain domain types like LocalDate, BigDecimal etc - DTO would contain raw form of data
#### Also because of that I decided to return 200 for both cases - means validation passed correct or not and 400 is reserved in my solution only if you pass data which can not be mapped to LocalDate or enum for example
#### If have more time we can image validate returning 200 in case validation did not return any errors and 400 with complex validation result objects when validation failed - not applying now that solution as described before

## Timelog

### Environment preparation and blank project creation - 30m
#### Created new public repository on github.
#### Generation of blank project
#### Added validate REST to project

### Domain objects for transactions - 2h30m
#### Created basic infrastructure for tests in the code
#### Created object for Spot (along with first test)
#### Created object for Forward (along with first test)
#### Created object for VanillaOption (along with first test)
#### Changed packages, project name, repository name to pattern forex.transaction
#### Updated README with basic instructions and discussion of the solution