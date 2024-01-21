
# VehicleRestApi

VehicleRestAPI is a robust microservices-based project developed using Spring Boot, adhering to the principles of microservices architecture. The project provides a comprehensive set of functionalities, including CRUD operations (GET, POST, PUT, DELETE) for vehicle-related data. It incorporates advanced features like filter-based search options, making it a versatile solution for managing vehicle information.


## Key Features
1. Microservices Architecture: The project is designed with a microservices architecture to enhance scalability, flexibility, and maintainability.

2. API Gateway: An API gateway is employed to manage different services' ports efficiently, ensuring seamless communication between microservices.

3. Service Registry (Eureka Server): Eureka Server is utilized for service registration and discovery, enabling effective management of microservices.

4. Configuration Management: Configuration settings are stored in a centralized GitHub file and connected to all services using a Config Server. This ensures consistent configurations across the entire application.

5. Authentication (JWT): JSON Web Token (JWT) is implemented for secure authentication, providing a robust and scalable method for user verification.

6. Circuit Breaker (Resilience4j): Resilience4j is employed for circuit-breaking mechanisms, offering features like fault tolerance, retry, and rate limiting to enhance system resilience.

7. Unit Testing with Test Container: Unit tests are conducted on DAO and service layers using Test Containers, ensuring the reliability and correctness of critical components.And it has used Behavior-Driven Development approach for testing.

8. DTO (Data Transfer Object) and Object Mapper: To fetch data from the vehicle details service into the vehicle search service, a DTO class is utilized. The Object Mapper concept is employed to efficiently map and transfer data between services.
## Demo

In this section i will show you some of my project's features.

### Service Registry
So, for runing this project we need to at first start the ServiceRegistry then we will start the other services.But one thing we have to keep in our mind that we have to start service registry first otherwise it will give us some error message.

After start all of the services if we go to the Eureka Server's default port no 8761 alongwith localhost as host.Then we will be able to get some info like this-


![EurekaClient](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/6b98d45b-d9cb-4734-ab7c-8886fac4f84b)

### Authentication & Authorization

Now if we want to enter any rest end point into postman and try to run it we will give us some error.Because at first we need to authenticate and based on the role we are given(user/admin) it will check are the user/admin role has the privilege to run this particular endpoint or not.

Now run the authentication using this endPoint http://localhost:9196/api/v1/vehicle-search/auth/authenticate If we give the correct information then it will give us a bearer token.And using that bearer token we can access the other endpoints.And one thing i have forgot to mention if you haven't register here yet then you can also register by using this endpoint http://localhost:9196/api/v1/vehicle-search/auth/register .Although you can see those endpoints by going to the vehicleSearch->auth->authcontroller class.But for making things simple i have mention these endpoints in here.

Authenticate

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/dfb44d87-7511-4f5a-829f-9a54275975ae)

Register


![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/e09c6ac5-702d-4db3-be24-150ee6e61c54)

Database

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/73e8643b-a438-4470-a0ef-7fddc9e46f55)

### Crud Operation

In here I will do some of the crud operation inside vehicleDetails service.

GET 

This operation will findout all of the vehicle details information available in the database.

https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/21e1a0d0-e873-45cf-a200-e35164052c2f

GETBYID

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/87371126-a499-4a71-b69c-76b5d0a81c75)

If the id isn't available on the database then it will give a exception.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/a90b2191-10de-465e-b376-adc96afaa655)

PUT 

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/b0457081-fa53-4149-a953-ab4ac4239007)

### Role Based Operation

In my project i have given user and admin all of the privileges but in delete operation admin can only do the deletion.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/4cb41703-b7e1-427f-b566-a2fa0ba4613b)

As I am now logged in as a USER.Not as ADMIN Role.
Point To Be Noted - I know this is not the write way to figure out that i can't do the operation.I need to handle the exception but for some of the fields i didn't handle it.But will fix this in future.

Logged in as an ADMIN

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/5499eacb-d8ce-4a48-ae32-de2a01fbf174)


Now do the deleteion and let's see what happens

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/5199970a-9ebf-41eb-a6b2-888ea1dda593)

I can successfully delete that particular manufacturer.

### ManyToMany Mapping

I have made an endpoint which actually make relationship between 3 table as it is a manytomany mapping it will create a new table.Here the new Table name is model_trim and inside that table model and it's coresponding trim id has been listed.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/3187a003-4bf5-4a91-9ef2-cdfe1e37abf5)

And new model has been listed to the model table and there is also a reltionship between model and manufacturer.So, inside model table according to each model corresponding manufacturer_id also has been listed.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/af93caae-6f1f-43d2-8c38-82abfe98e4c7)

When we are adding a new model if the trim has not been inside the trim  table of database.Then it will not added that trim to the model_trim table.Only the existence trim will be added on the model_trim.

And it is happening because of of the cascade = CascadeType.MERGE.Which has been set on the Model entity class.

Trim 

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/f23692e6-b23e-4aa7-81a3-488b74998c71)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/983d435a-7eea-4fa0-959a-b687ef347902)

Here i have used trim types and those trim types already exits in the trim table.So, in the model_trim table it will make relationships with the newly created model id.
Manufacturer which i have declare in here it is also showing in the manufacturer table too.So, this will also make a relationship with newly created model.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/2e4e53d0-7ad9-43d2-9fd9-30b465340cb2)

Model Table

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/9e8f3cfe-218b-4b21-ad73-0ccf1bd404d7)

ModelTrim Table

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/f5ba150c-0c57-4582-8026-a2127c5ca19d)

Trim Table

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/e9efc5f0-2bd8-41f6-b240-9c76f9efc499)

Manufacturer Table

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/784192d4-1c45-49a4-ab5d-5f08609c7c8e)

As you can see model and model_trim table has just changed.And the 2 tables remains the same.

But now if i pass a trim type which is not in the trim table then what will happen? Now let's observe this scenario.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/d0b66c1f-f82a-4bf1-b315-80a4949a83bf)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/51b10abf-bb98-4b4f-9dfe-bb8656849d87)

I can't add this model because the trim type isn't available on the trim table.

Now, let's see what will happen if we pass a manufacturer which doesn't exist on the manufacturer table.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/6bce8ec2-b08f-4ff9-9103-b5f3c48a8e43)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/acbd57ec-478b-4c2d-80fb-72d7e291e838)

### Delete

One question will come into your mind what will happen if we delete a model.
So, when doing deletion operation it will delete the model and corresponding model trim relationship from the model_trim table.

Let's do this operation----

Before deletion database tables----

Model
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/947a61a8-b585-452c-b303-70993af0e871)

ModelTrim
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/a4b2cf4d-ca56-479d-b752-b5398045d95b)

Trim
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/c7e9ee10-00ff-4564-b00a-1a7d4b4b621b)

Manufacturer
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/eac22b04-f09d-4229-bc3c-71c80c7f4f25)

After Deletion
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/8699781e-4be6-4b66-9f08-74ea40618a10)

Model
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/24b3360e-a090-4e71-ab8f-974180333d69)

ModelTrim
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/d2790ad0-8cb0-4d05-9c0d-bed91988bdd9)

Trim
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/4aa44ff2-68e4-44a7-a179-dad5eab06b6b)

Manufacturer
![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/dac92489-af1b-4d10-b9d2-f1c9e07b7622)

So, we are getting the output as per our expectation. 

### Fetch values from another Service

In our project we got many services but the values are coming from 2 services one is vehicle details and another is vehicle search.

![MicroServiceArchitechure](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/fb6f336a-b24e-4959-8113-65b74ca54974)

In vehicleSearch we have to fetch value from vehicleDetails to display some endpoints value.For that we have use restTemplate.And we have use the mapper concept to fetch those values.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/14bcb095-7fe6-4b34-ac97-1a67f16768f3)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/7f60cee8-c012-4ae0-afe3-ec1f82a257b8)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/e7009856-962a-4238-807f-53a3e7b0a867)

Here we are getting values from vehicleDetails service.And also getting some additional value with it.

### Circuit Breaker

We have applied circuit breaker in this method.So, if for any reason the vehicle details service is down then it will show the end user some diffult values.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/c23c8590-af8f-4c78-8a1d-697122b89da2)

As you can see vehicleDetails service is not there in the Eureka server's client list.So, the vehicleDetails service is down.

Now, if we send the request to get the vehicle details value.We will be getting some dummy data.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/d963edcb-0ac7-4473-9ef4-94a1b5d25185)

We have also applied retry functionality so that, if the service is little bit slow then it will automatically send the request to the  vehicleDetails service and try to connect with it.If according to our given requirement still it can't able to connect with vehicleDetails service then it will show the dummy data.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/b976ee98-702c-496b-bd27-f35d8b99518f)

We can also check the health status and other info using actuator.And observing those value we can get an idea of our services present condition.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/1842ab48-b54d-4874-9714-797f89f85f9c)

We have also applied rate limiter in this method.Rate limiter actually rate the limit of sending request to a particular endpoint.This thing actually secured our application by defending the DoS attack.And for checking is it working correctly or not.We have used Jmeter.

### Search Vehicle By Filter

Fetching value from the vehicleDetails Service By passing value as parameter.

Showing value for the modelYear 2019

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/4bfa24c3-76d1-427d-b59e-878f02d3b520)

### BusinessLogic

To find out the estimated monthly price we have use Compount interest rule.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/b2b1e974-94b7-478a-992a-fca4aae7349d)

### Testing

For doing the testing we can't store the value inside the databases.Then it will be kind of look like manual process of doing the testing.
Instead, we aim to automate testing procedures for efficiency.

For doing the test for controller layer we need to mock the data.That's why we are using Mockito framework.ObjectMapper also has been used here to make a relationship between 2 services.We can fetch value from another service by using the ObjectMapper.And for testing we have used jUnit5.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/da9dd69f-064c-4b6e-bb47-5602e6f3f385)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/7befefbb-c826-4816-a25b-8f70a391efcc)

Both AAA (Arrange-Act-Assert) and GWT (Given-When-Then) are patterns in Behavioral-Driven Development, serving the same purpose of structuring test cases.In this test cases i have followed the Behavioral Driven Development.

Specially when it's come to writing the unit test cases for the dao/repository layer then we need to store data inside the database temporarily.So, that's why here we can use h2 embedded database.But testContainers spins up actual database instances in Docker containers, mirroring production environments more accurately than H2's in-memory database. This approach mitigates inconsistency between testing and production, enhancing the reliability of unit tests for database-related code.

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/b7fe4812-41a0-4663-b024-45f361537013)

![image](https://github.com/Emon-Khan/VehicleRestApi/assets/42010220/8fd5fa79-7867-48d9-9e71-0b0d4f6de8fc)






