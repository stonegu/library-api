# Notes

Include any additional notes below including assumptions made, 
design decisions that you made, additional libraries added or anything that you did that was beyond the asks.

1. pick one service, one repository, one controller to test
2. include postman tests in test/postman folder
3. add interface for service
4. better to use ResponseEntity, which allow customized status code, headers and body (I only use it in RentalController, so we can see the difference)
5. add ResponseEntityExceptionHandler to handle exception

H2:
db url: jdbc:h2:mem:testdb for http://localhost:8000/h2-console

GraphQL: http://localhost:8000/graphiql