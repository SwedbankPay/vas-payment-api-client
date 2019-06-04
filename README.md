# Spring boot + VueJS client for VasPublicPaymentApi

* Java 11
* VueJS
* Maven
* Postgres

## Swagger documentation
* [VasPublicPaymentApi](https://stage-evc.payex.com/payment-api/swagger-ui.html)


## First App run

Inside the root directory, do a: 

```
mvn clean install
```

Run our complete Spring Boot App:

```
mvn --projects backend spring-boot:run
```

Now go to http://localhost:8080/ and have a look at your new client.


# Project setup

```
vas-payment-api-client
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```


## Build docker image:
    
    mvn --projects backend clean compile jib:dockerBuild
    
## Deploy docker image locally:
    
    docker-compose up -d    
