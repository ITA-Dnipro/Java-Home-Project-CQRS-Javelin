## Table of Contents

- [About the module](#About-the-module)
- [How to run local](#How-to-run-local)
 
---

## About the module
__Gateway-spring-cloud__ - is a module for routing incoming requests depending on HTTP methods.
It routs GET method to one service and POST, PUT, DELETE methods to another service.
Routs define in application.yaml  

## How to run local

__Run with Intellij IDEA__

Foremost, you need to have your own services: reader and writer, which are running on separate ports.
Then you need to define URIs of your services, using environment variables in `Run/Debug Configurations` 
in IntelliJ IDEA or command line:
```properties
READER_URI=http://localhost:8085/
WRITER_URI=http://localhost:8086/
```
Note, that you should specify the same ports where your reader and writer services run.
Then run application with your IDEA.

__Run with Docker__

Before running gateway you should build images of reader and writer services.
Then you should specify properties of services in `services:` section of 
`docker-compose.yml` file which is located in `gateway-spring-cloud` package. 
For example:
```properties
  test-reader-microservice:
    image: homeacademy/test-reader-microservice
    ports:
      - 8085:8085
    networks:
      - javelin
```
After that use command `docker-compose up` in package `gateway-spring-cloud` package
to run application.

By default, gateway app runs on 8088 port.
If you did everything correctly, you should be able to sent requests to your services on this
URL: http://localhost:8088/your-service-endpoint
