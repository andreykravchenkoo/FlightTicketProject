# Flight Ticket Project

## Introduction

Application for booking tickets for real flights from API https://app.goflightlabs.com/dashboard
The user can find flights by his filter, book a ticket and pay the payment.

## Features

* Registration and authorization by JWT
* Search for flights in the integrated API
* Ticket booking
* Execute payment

## Technical details

**Technology stack**: 

* JDK 17
* Spring Boot 2.7.7
* Spring Data
* Spring Security (JWT)
* Database PostgreSQL for production and H2 for tests
* Swagger UI
* Lombok

**Dependencies**

* API FlightLabs: https://app.goflightlabs.com/dashboard

## Contributing

1. Clone the project locally on your machine
2. Get API key here: https://app.goflightlabs.com/dashboard
3. Paste the resulting key into application.yml
4. Launch by URL: http://localhost:8080/air-ticket-booking/swagger-ui/index.html#
