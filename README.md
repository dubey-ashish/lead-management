
# Project Overview

The **Lead Management System** is designed to help Key Account Managers (KAMs) manage and track restaurant leads, interactions, and account performance in a seamless and efficient manner. This system allows users to create, update, and track restaurant leads, plan calls, and analyze the performance of restaurant accounts. With role-based access and security features, it ensures that only authorized users can perform sensitive actions.




## Features
- **Lead Management**: Create and manage restaurant leads and their associated details.
- **Interaction Tracking**: Track all interactions with restaurant leads to ensure timely follow-ups.
- **Call Planning**: Automatically schedule and manage calls with restaurant leads based on a specified frequency.
- **Account Performance Tracking**: Track the performance of restaurant accounts collectively and individually over time.
- **Role-based Security**: Authentication and authorization features ensuring access control for different roles.


## Technologies Used
- **Spring Boot** for backend development.
- **MongoDB** for data storage.
- **Swagger** for API documentation.
## System Requirements
- **JDK 21**: This project is developed using Java 21. Ensure that JDK 21 is installed on your machine.
- **Maven 3.8+**: Apache Maven is used for project dependency management and building.
- **MongoDB**: MongoDB is used as the database. Ensure MongoDB is installed and running.
## Installation Instructions
To set up this project locally, follow these steps:

 **Extract the ZIP file**: 
   - Download and extract the ZIP file containing the project code to your local machine.

 **Install Java 21**: 
   - Ensure that Java 21 is installed on your system. You can verify this by running the command `java -version` in your terminal. If Java is not installed, follow the installation instructions for Java 21 from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html) or use a package manager like `apt` (Linux) or `brew` (macOS).

 **Install Maven**: 
   - This project uses Maven for dependency management. Ensure that Maven is installed on your system. You can verify this by running `mvn -version`. If Maven is not installed, follow the installation instructions from [Maven's official website](https://maven.apache.org/install.html).

 **Navigate to the project folder**: 
   - Open a terminal window and navigate to the root directory of the extracted project folder.

 **Build the project**: 
   - In the terminal, run the following command to compile the project and download the necessary dependencies:
     ```bash
     mvn clean install
     ```

Once these steps are completed, the project is ready for running.

## Running Instructions

 **Local Data Population**

A folder containing BSON files for the `udaan` database is included. To import the database into MongoDB, use the `mongorestore` command:
   ```bash
   mongorestore --db udaan /path/to/udaan
   ````

 **MongoDB Configuration**

Before running the application, ensure the following MongoDB setup:

 Database `udaan`.

 Collections :
   - `restaurants`
   - `accounts`
   - `orders`
   - `interaction_history`
   - `interaction_plan`


 **Run the application**:
   - Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
   - Right-click on the `LeadManagementApplication` class (the class with `@SpringBootApplication`) and select **Run**.

 **Verify the application**:
   - The application will start on `http://localhost:8080`.


## API Documentation
The API documentation is provided through Swagger UI. To view the available endpoints and interact with the API:
- Run the application.
- Open your browser and go to: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Swagger will list all the available APIs with descriptions, request parameters, and response formats.

## Sample Usage Examples

#### 1. Get Lead by ID

**Endpoint**: `GET`  `/v1/lead/get-lead/{leadId}`

**Description**: Fetches details of a specific lead by `leadId`.

**Request**:
- **Method**: `GET`
- **URL**: `http://localhost:8080/v1/lead/get-lead/{leadId}`
- **Example URL**: `http://localhost:8080/v1/lead/get-lead/rest123`

**Response**:
- **Status**: `200` `OK`
- **Body**:
```json
{
    "leadId": "676fac1df82a10e4e654dc46",
    "restaurantId": "rest123",
    "name": "The Gourmet Haven",
    "cuisine": "American",
    "address": {
        "street": "5th Avenue",
        "city": "New York",
        "state": "New York",
        "country": "USA",
        "zipcode": "10001",
        "coordinates": {
            "latitude": 40.712776,
            "longitude": -74.005974
        },
        "timeZone": "America/New_York"
    },
    "contacts": [
        {
            "name": "John Doe",
            "employeeId": "EMP001",
            "role": "MANAGER",
            "contacts": [
                {
                    "type": "MOBILE",
                    "value": "+1-555-1234"
                },
                {
                    "type": "EMAIL",
                    "value": "john.doe@gourmethaven.com"
                }
            ]
        }
    ],
    "leadStatus": "MEMBER",
    "currentManagerId": "MGR001",
    "managerSince": "2024-12-20T09:00:00Z",
    "previousManagerId": null,
    "callEveryNDay": 7
}
```

#### 2. Create Interaction History

**Endpoint**:  `POST` `/v1/interactions/create`

**Description**: Creates a new interaction history entry.

**Request**:
- **Method**: `POST`
- **URL**: `http://localhost:8080/v1/interactions/create`
- **Body**:
```json
{
  "restaurantId": "rest456",
  "thirdPartyService": "V7WUFXY59D9O9",
  "keyAccountManagerId": "MGR001",
  "employeeId": "EMP001",
  "contactInfo": {
    "type": "MOBILE",
    "value": "+1-555-1234"
  },
  "interactionType": "CALL",
  "timeOfInteraction":"2024-12-26T10:00:00.000Z",
  "details": "Follow-up call regarding yearly performance review."
}
```
**Response**:
- **Status**: `201` `CREATED`
- **Body**:
```json
{
  "restaurantId": "rest456",
  "thirdPartyService": "V7WUFXY59D9O9",
  "keyAccountManagerId": "MGR001",
  "employeeId": "EMP001",
  "contactInfo": {
    "type": "MOBILE",
    "value": "+1-555-1234"
  },
  "interactionType": "CALL",
  "timeOfInteraction":"2024-12-26T10:00:00.000Z",
  "details": "Follow-up call regarding yearly performance review."
}
```

#### 3. Get All Restaurant Performances

**Endpoint**:  `GET` `/v1/performance/restaurants`

**Description**: Fetches the performance data of all restaurants within the specified date range, in decreasing order of successful numbers.

**Request**:
- **Method**: `GET`
- **URL**: `http://localhost:8080/v1/performance/restaurants`
- **Parameters**:
  - `start`: `ISO 8601 format` (e.g., `2023-10-30T10:00:00Z`)
  - `end`: `ISO 8601 format` (e.g., `2025-10-30T10:00:00Z`)

**Example URL**:
`http://localhost:8080/v1/performance/restaurants?start=2023-10-30T10:00:00Z&end=2025-10-30T10:00:00Z`

**Response**:
- **Status**: `200` `OK`
- **Body**:
```json
{
    "start": "2023-10-30T10:00:00Z",
    "end": "2025-10-30T10:00:00Z",
    "totalOrders": 8,
    "successfulOrders": 6,
    "failedOrders": 2,
    "restaurants": [
        {
            "restaurantId": "rest789",
            "restaurantName": "Mumbai Spices",
            "totalOrders": 4,
            "successOrders": 3,
            "failedOrders": 1,
            "successfulOrdersWorth": 750.0,
            "currency": "INR"
        },
        {
            "restaurantId": "rest456",
            "restaurantName": "London Delights",
            "totalOrders": 2,
            "successOrders": 2,
            "failedOrders": 0,
            "successfulOrdersWorth": 23.0,
            "currency": "GBP"
        },
        {
            "restaurantId": "rest123",
            "restaurantName": "The Gourmet Haven",
            "totalOrders": 2,
            "successOrders": 1,
            "failedOrders": 1,
            "successfulOrdersWorth": 13.98,
            "currency": "USD"
        }
    ]
}
```

## Disclaimer
Import the `Udaan.postman_collection.json` file into your Postman application to view all the available endpoints and test their functionality. This collection contains pre-configured requests for each API endpoint, making it easy to interact with the system and verify the responses.

Please note: For testing API functionality, security features have been temporarily disabled by commenting out the appropriate annotations. You can reactivate these features by uncommenting the annotations as required.
## License

The MIT License (MIT)

Copyright (c) 2025 Ashish Dubey

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
