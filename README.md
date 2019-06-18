# Spring Boot + VueJS client for VasPublicPaymentApi

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

<details>
	<summary>Oauth2:</summary>

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

</details>

<details>
	<summary>HMAC:</summary>

The API also requires HMAC authentication to be present in a request.  
In this client the HMAC value is automatically calculated by [HmacSignatureBuilder.java](./backend/src/main/java/com/payex/vas/demo/config/security/HmacSignatureBuilder.java) and added to all outgoing requests in [ExternalRequestInterceptor.java](./backend/src/main/java/com/payex/vas/demo/config/ExternalRequestInterceptor.java)  

HMAC is implemented using SHA-512 secure hash algorithm. 

Expected `Hmac` header format is:
```text
HmacSHA512 <user>:<nonce>:<digest>	
```
where `digest` is a Base64 formatted HMAC SHA512 digest of the following string: 
```text
METHOD\n
RESOURCE\n
USER\
NONCE\n
DATE\n
PAYLOAD\n
```

`METHOD` (mandatory) the requested method (in upper case) 
`RESOURCE` (mandatory) the path to desired resource (without hostname and any query parameters)  
`NONSE` (mandatory) a unique value for each request ([UUID](https://tools.ietf.org/rfc/rfc4122.txt)) 
`DATE`(optional) same as `Transmission-Time` if provided as seperate header. Uses [ISO8601 standard](https://en.wikipedia.org/wiki/ISO_8601)
`PAYLOAD` (optional) body of request 

Example request:

```bash
curl -X POST \
   https://stage-evc.payex.com/payment-api/api/payments/payment-account/balance \
  -d '{
	  	"accountIdentifier": {
			"accountKey": "7013360000000000000",
			"cvc": "123",
			"expiryDate": "2020-12-31",
			"instrument": "CC"
		}
	}'
```

In this example `USER` is user and `SECRET` is secret. 

The plain string to `digest` would then be:
```text
POST
/payment-api/api/payments/payment-account/balance
user
21a0213e-30eb-85ab-b355-a310d31af30e
2019-06-18T09:19:15.208257Z
{
	"accountIdentifier": {
		"accountKey": "7013360000000000000",
		"cvc": "123",
		"expiryDate": "2020-12-31",
		"instrument": "CC"
	}
}
```

The plain `digest` string is then hashed with `HmacSHA512` algorithm and the `SECRET`.
Finally we Base64 encode the hashed value. This is the final `digest` to be provided in the `Hmac` header.


Final `Hmac` header value: 
```text 
HmacSHA512 user:21a0213e-30eb-85ab-b355-a310d31af30e:oY5Q5Rf1anCz7DRm3GyWR0dvJDnhl/psylfnNCn6FA0NOrQS3L0fvyUsQ1IQ9gQPeLUt9J3IM2zwoSfZpDgRJA==
```

</details>

### Security documentation
* [OAuth2](https://oauth.net/2/)
* [Client Credentials](https://www.oauth.com/oauth2-servers/access-tokens/client-credentials/)
* [The RESTful CookBook: HMAC](http://restcookbook.com/Basics/loggingin/)
* [HMAC - Wikipedia](https://en.wikipedia.org/wiki/HMAC)

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
