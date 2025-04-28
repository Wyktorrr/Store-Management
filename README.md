# Store-Management

My application is developed using Java 21 and Spring Boot 3.4.5. I made use of Postgres as data base.
The Postgres database is furthermore Dockerized.

The main focus has been on the authentification and authorization mechanisms. The authorization is based on JWT.
There are several CRUD operations for the Product and User domain objects as a proof of concept.

Once a user is registered and successfully logged in, a JWT token is generated and returned to the client. This token is then used for subsequent requests to access protected resources.

Enjoy!