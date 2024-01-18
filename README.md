# VehicleRestApi
VehicleRestAPI is a robust microservices-based project developed using Spring Boot, adhering to the principles of microservices architecture. The project provides a comprehensive set of functionalities, including CRUD operations (GET, POST, PUT, DELETE) for vehicle-related data. It incorporates advanced features like filter-based search options, making it a versatile solution for managing vehicle information.

Key Features:

1. Microservices Architecture: The project is designed with a microservices architecture to enhance scalability, flexibility, and maintainability.

2. API Gateway: An API gateway is employed to manage different services' ports efficiently, ensuring seamless communication between microservices.

3. Service Registry (Eureka Server): Eureka Server is utilized for service registration and discovery, enabling effective management of microservices.

4. Configuration Management: Configuration settings are stored in a centralized GitHub file and connected to all services using a Config Server. This ensures consistent configurations across the entire application.

5. Authentication (JWT): JSON Web Token (JWT) is implemented for secure authentication, providing a robust and scalable method for user verification.

6. Circuit Breaker (Resilience4j): Resilience4j is employed for circuit-breaking mechanisms, offering features like fault tolerance, retry, and rate limiting to enhance system resilience.

7. Unit Testing with Test Container: Unit tests are conducted on DAO and service layers using Test Containers, ensuring the reliability and correctness of critical components.And it has used Behavior-Driven Development approach for testing.

8. DTO (Data Transfer Object) and Object Mapper: To fetch data from the vehicle details service into the vehicle search service, a DTO class is utilized. The Object Mapper concept is employed to efficiently map and transfer data between services.
