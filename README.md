# Running the application
* Default destination is Kafka. Destination can be changed via **logger.implementation** property in application.properties
* 2 separate Docker compose files are provided in order to run Kafka and Zookeeper or RabbitMQ easily. Before running the logging application relevant service should be run by using relevant compose file
* Application can be run through bootRun Gradle task

# Exploring the code
Since Gradle is used as the build tool the project can be open in any IDE

# Development process
* Spring initializr is used to create the project
* LoggerServiceApplicationTests is already added by initializr
* contextLoads test is added to ensure that the context is loaded successfully
* testServerIsUp test and the status service are implemented in order to check if the service is up and running
* Kafka topic is created programmatically. So, no need to run a script to create the topic. It is same for RabbitMQ
* Apparently Sprint can generate API documentation. But, I haven't had time to implement that

# Tools & Frameworks
* JDK: 17.0.7
* Gradle: 8.1.1
* Sprint boot: 3.0.6
* Docker Desktop: 4.19.0
** Docker compose: 3.18