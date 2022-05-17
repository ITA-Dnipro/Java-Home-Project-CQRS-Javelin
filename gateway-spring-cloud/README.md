## Table of Contents

- [About the module](#About-the-module)
- [How to run local](#How-to-run-local)
 
---

## About the module
__Gateway-spring-cloud__ - is a module for routing incoming requests depending on HTTP methods.
It routs GET method to one service and POST, PUT, DELETE methods to another service.
Routs define in application.yaml  

## How to run local
Foremost, you need to have your own services: reader and writer, which are running on separate URIs or ports.
Then you need to define URIs of your services, using environment variables in `Run/Debug Configurations` 
in IntelliJ IDEA or command line:
```properties
READER_URI
WRITER_URI
```
Then run application with your IDEA.
By default, gateway app runs on 8088 port.
If you did everything correctly, you should be able to sent requests to your services on this
URL: http://localhost:8088/your-service-endpoint
