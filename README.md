# Spring boot + VueJS client for VasPublicPaymentApi

* Java 11
* VueJS
* Maven
* Postgres

## Project setup

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


## Swagger documentation

* [VasPublicPaymentApi](https://stage-evc.payex.com/payment-api/swagger-ui.html)


## Security

VasPublicPaymentApi requires an OAuth2 access token for interaction.  
This application automatically handles token fetching and refreshing by using [Spring Security](https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/htmlsingle/#boot-features-security-custom-user-info-client).   
Configuration values are set in [application.yml](./backend/src/main/resources/application.yml): 
```yaml
# "XXX" Should be replaced by value provided by PayEx
# CLIENT_ID/CLIENT_SECRET/VAS_AUTH_SERVER_URL can also be set in docker-compose.yml as environment variables if running with docker
# The application will see if environment variables are present, if not fall back to "XXX" values.
vas-payment-api:
    oauth2:
        client:
            grantType: client_credentials
            clientId: "${CLIENT_ID}:XXX"
            clientSecret: "${CLIENT_SECRET}:XXX"
            accessTokenUri: "${VAS_AUTH_SERVER_URL}:XXX"
            scope: publicapi
```
And the implementation of these are located in [Oauth2RestTemplateConfiguration.java](./backend/src/main/java/com/payex/vas/demo/config/security/Oauth2RestTemplateConfiguration.java):
```java
public class Oauth2RestTemplateConfiguration {
    //...
    @Bean
    @ConfigurationProperties("vas-payment-api.oauth2.client")
    protected ClientCredentialsResourceDetails oAuthDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    protected RestTemplate restTemplate() {
        var restTemplate = new OAuth2RestTemplate(oAuthDetails());
        restTemplate.setInterceptors(ImmutableList.of(externalRequestInterceptor()));
        restTemplate.setRequestFactory(httpRequestFactory());
        return restTemplate;
    }
    //...
}
```
The API also requires HMAC authentication to be present in a request.  
In this client the HMAC value is automatically calculated by [HmacSignatureBuilder.java](./backend/src/main/java/com/payex/vas/demo/config/security/HmacSignatureBuilder.java) and added to all outgoing requests in [ExternalRequestInterceptor.java](./backend/src/main/java/com/payex/vas/demo/config/ExternalRequestInterceptor.java)  
Example HMAC header value: 

```text
HmacSHA512 Systemtest:e45807b3-3cd2-4330-923c-51bea2c1a4a0:8M16n2xbqFl2rHvPRRrpIXwIHA+V+PM7vAPzYBqGgXwNLvOeZmnA5pBpFQaIBofoBMhvyI3sN2jjsog52+4+9w==
``` 

### Security documentation
* [OAuth2](https://oauth.net/2/)
* [Client Credentials](https://www.oauth.com/oauth2-servers/access-tokens/client-credentials/)


## First App run

Inside the root directory, do a: 

```bash
mvn clean install
```

Run our complete Spring Boot App (needs Postgres server running):

```bash
mvn --projects backend spring-boot:run
```

Now go to http://localhost:8080/ and have a look at your new client.

## Testing application

1. Add a new card with provided details from PayEx.
2. Click on newly added Card
3. Click on "initiate payment" to create a new transaction 
 

## Build docker image:
```bash
mvn --projects backend clean compile jib:dockerBuild
```
    
## Deploy to local docker:
```bash
docker-compose up -d    
```
