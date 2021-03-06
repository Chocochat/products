# Ads Products

## Assumptions

* There are 3 tables designed 
  1. Product - includes the ad tyes like Classic, Standard and Premium
  2. Deal - holds the deal prices like 3 for 2, 5 for 4 etc
  3. Discount - includes the discounted prices 
* company ID(Numeric) would be the preferred way of implementation but for this demo the company name is used as the ID
* Though the customer is a privileged customer but the number of ads posted doesn't meet the deal price then it is defaulted to default product price
* In Deals table 3 for 2 is stored in db as 3:2 and default values are stored in db as 1:1
* In Products table default values are stored as 0.0 overrided prices are stored as double value
* Only the scenarios of Price calculation are unit tested. keeping it to the scope of the coding challenge
* Log4j integration is pending but for this demo to keep it simple ```System.out.println();``` is used

## prerequisite
### Tech Used
* Java >8
* Maven
* Junit
* JaCoCo - Java code coverage tools (TBD)

### IDE 
* IntelliJ or any IDE that supports java development

### To build
* maven - ```mvn clean install```

### To run
* ```java -jar ads-app-1.0-SNAPSHOT.jar```

### CICD
* Jenkings or git (TBD)

### Sample curls
#### Swagger URL
`http://localhost:8080/swagger-ui.html`
#### Health check URL
`http://localhost:8080/actuator/health`

#### Main Price Calculator API
``
curl --location --request GET 'http://localhost:8080/price' \
--header 'Content-Type: application/json' \
--data-raw '{
"customer": "axilCoffeeRoasters123",
"items": [
"std", "std","std", "premium"
]
}'
``

### Data file with SQL commands
* SQL queries file `src/main/resources/data.sql`
* Alternatively API can be used for inserting deleting and updating data
* Swagger file `http://localhost:8080/swagger-ui`

## Modifications via API 
### To _Deals_ Data
`http://localhost:8080/swagger-ui.html#/deals-controller`

![](images/deals.png)

### To _Discounts_ Data
`http://localhost:8080/swagger-ui.html#/discounts-controller`

![](images/discounts.png)

### To view _Products_ data
`http://localhost:8080/swagger-ui.html#/products-controller`

![](images/products.png)

### To get _Price_ and _all_ data
`http://localhost:8080/swagger-ui.html#/generic-controller`

![](images/generic.png)



