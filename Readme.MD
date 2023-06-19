### Project

#### Tech stack:
    Run the application

    'docker-compose -p viswals-pt up'

    Solution 1:
    Navigate to the publisher location and execute the command "mvn clean install."
    Proceed to the consumer location and run the command "mvn clean install."
    
    Finally, execute the command "docker-compose -p viswals-pt up". Please ensure that Maven is installed on your system.
    
    Solution 2:
    I have included a convenient build script that automates the process. Before using it, ensure that Maven is installed. Follow these steps:
    Run the command "chmod +x build_and_run.sh" to change the permissions of the script.
    Execute the script to handle the necessary build and run operations by using this command "./build_and_run.sh".
    
    Solution 3:
    Although it is generally discouraged, I have uploaded the required JAR files to a repository. To proceed with this approach, execute the command:
    "docker-compose -p viswals-pt up"
    
    Solution 4:
    If you prefer immediate assistance, I would be more than happy to join a call to guide you through the setup process promptly.
#### Tech stack:
    # Java - 17

    # Springboot - 3.1.0
    # RabbitMQ - rabbitmq:3.7.4-management
    # Redis - latest
    # Postgres Database - 15.3
    # Pgadmin4 - latest
    # Dcoker - Engine: 20.10.23
    # Docker Composer

### Project flow

    Publisher Application:

        The Publisher Application performs the following steps:

        a) It receives a CSV data file through a REST controller.
        b) The application reads the data from the CSV file.
        c) The data is then published into RabbitMQ, a message broker, for further processing.

    Consumer Application:

        The Consumer Application consists of several components that work together to handle the 
        data received from the Publisher Application:

        a) Message Consumer Component:
        This component acts as a message consumer, responsible for retrieving data from RabbitMQ.

        b) Redis Cache:
        Once the data is consumed from RabbitMQ, it is posted to a Redis Cache. Redis is a 
        high-performance in-memory data store that allows for quick data retrieval and storage.

        c) Scheduler Component:
        The Consumer Application includes a scheduler component that runs every 5 seconds. Its 
        main task is to periodically check the Redis Cache for any available data.
        
        d) Postgres Database:
        If the scheduler component finds any data in the Redis Cache, it stores the data into a 
        Postgres Database. Postgres is a reliable and widely-used open-source relational database 
        management system.

        By breaking down the process into detailed steps, we can understand the flow of data from 
        the initial CSV file to its storage in the Postgres Database via RabbitMQ and Redis Cache.
