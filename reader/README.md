## Reader

Reader is a microservice with which Java-Home-Project-CQRS-Javelin application
interacts. Main purpose of reader module is interacting with MongoDB and
return DTOs which stores in it as a response. 

___

### Required to install (for use)

- [Docker](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe)
- Browser or [Postman](https://dl.pstmn.io/download/latest/win64)
___

### How to run

Open terminal or command prompt and just launch docker-compose file
by using command `docker-compose up`. Release the ports necessary for the
application to work or assign others (in docker-compose.yml). For interaction
with database through UI can create one more container with
[MongoExpress](https://hub.docker.com/_/mongo-express). Use default credentials
defined in `docker-compose.yml`. Use browser interface for GET methods with
http://localhost:8085/api/0/news address or Postman for CREATE, UPDATE, DELETE
methods with the same address.