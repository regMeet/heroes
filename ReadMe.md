# SuperHeroes API

## Maven Commands
Compile and tests  
mvn clean install

To skip the tests  
mvn clean install -DskipTests

To just run the tests  
mvn test

To run the application  
mvn spring-boot:run

## Endpoints
There is a postman collection on the root of the project that you can import into postman, it contains some scripts to login with different role users and saves automatically the access token for the endpoints that need it.

##### Ping
http method: GET
URI: http://localhost:8080/heroes/ping/{message}

##### login user / login admin
They just differ on the username (user / admin)  

http method: POST
URI: http://localhost:8080/auth/login  
body:  
&nbsp; {  
&nbsp;&nbsp;&nbsp;&nbsp;   "username": "user",  
&nbsp;&nbsp;&nbsp;&nbsp;   "password": "password"  
&nbsp; }

##### signup new user
You can create new users for using the secure endpoints, but they will just have the USER role. If you want them to change existent data, you will need to user an ADMIN role.  

http method: POST
URI: http://localhost:8080/auth/signup  
body:  
&nbsp; {  
&nbsp;&nbsp;&nbsp;&nbsp;   "username": "newUser",  
&nbsp;&nbsp;&nbsp;&nbsp;   "password": "password"  
&nbsp; }


### Secure Endpoints (admin / user)
You will be able to use any of the two roles with the Authorization Bearer header on the following endpoints.

##### Get all SuperHeroes
http method: GET
URI: http://localhost:8080/heroes/  

##### Get SuperHero by Id
This endpoints has enabled an EhCache, meaning that every request made on this endpoint will cache the next responsed for JUST 20 seconds (testing purpose).  
So if you get some particular id, then update it and you try to get it again right away you will get the cache response until 20 sec has passed.
http method: GET
URI: http://localhost:8080/heroes/id/{id}  

##### Get SuperHeros by name (array)
http method: GET
URI: http://localhost:8080/heroes/name/{name}  

##### Get SuperHeros by name (array)
http method: GET
URI: http://localhost:8080/heroes/name/{name}  

### Secure Endpoints (only admin)
The following endpoints will only be enabled for the ADMIN ROLE, since we are modifying existent data and we don't want any new user be able to do that.

##### Save a new superhero
http method: POST
URI: http://localhost:8080/heroes/  
body:
&nbsp;  {  
&nbsp; &nbsp; &nbsp; &nbsp;  "name": "Alan"  
&nbsp;  }  

##### Update a superhero
http method: PUT
URI: http://localhost:8080/heroes/{id}  
body:
&nbsp;  {  
&nbsp; &nbsp; &nbsp; &nbsp;  "name": "anyname"  
&nbsp;  }  

##### Delete a superhero
http method: DELETE
URI: http://localhost:8080/heroes/{id}  

## Exception Handler
There is an exception handler that will capture every possible exception and will return them in a cleaner way.  
All the secure endpoints can throw an Unauthorize error (401 or 403 if the role is not the one needed).  
When the endpoint is looking for a particular ID, it can throw a Not Found error (404).  
If the endpoint if modifying data, it will look if the data already exist which might cause a Conflict Error (409).  

