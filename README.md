# Weather Sensor REST API

This API is designed to handle data from a weather sensor that measures the temperature and rain status of the surrounding air. The sensor, in this case, is simulated on our own computer, and it will send HTTP requests in JSON format to our server, allowing us to receive and store the data in a database for analysis.

## Project Structure

The project is organized as follows:

```
/weather-sensor-api
   ├── src/
   │   ├── main/
   │   │    ├── java/
   │   │    │    ├── kz.tilegenov.javaspring.FirstRestApp/
   │   │    │    │    ├── controller/        # Contains the API endpoints' implementations
   │   │    │    │    ├── model/             # Contains the data models used in the application
   │   │    │    │    ├── repository/        # Contains the database repository interfaces
   │   │    │    │    ├── service/           # Contains the business logic and service implementations
   │   │    │    │    ├── dto/               # Contains the Data Transfer Object classes
   │   │    │    │    ├── util/               # Contains utility classes  
   │   │    │    │    └──FirstRestAppApplication.java.java   # Application entry point
   │   │    └── resources/
   │   │         └── application.properties   # Configuration properties for the application
   │   └── test/                               # Contains unit and integration tests
   └── pom.xml                                 # Project's Maven configuration file
```

## Endpoints

The project includes the following REST API endpoints that can be accessed for various operations:

### 1. POST /sensors/registration

This endpoint allows the weather sensor to register with our server. The sensor needs to provide a unique identifier, such as a serial number, during the registration process.

### 2. POST /measurements/add

This endpoint allows the weather sensor to send a new measurement to the server. The data should be sent in JSON format, including the timestamp, temperature, and rain status.

### 3. GET /measurements/rainyDaysCount

This endpoint retrieves the total count of rainy days recorded in the database. A "rainy day" is a day when the rain status was true in at least one measurement.

### 4. GET /measurements

This endpoint retrieves all the measurements stored in the database. It will return a JSON array containing the historical data collected from the weather sensor.

## Technologies Used

- Java
- Spring Framework
- RestTemplate
- PostgreSQL
