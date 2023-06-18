### Project

#### Tech stack:
    Run the application

    'docker-compose -p viswals-pt up'
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
        Once the data is consumed from RabbitMQ, it is posted to a Redis Cache. Redis is a high-performance 
        in-memory data store that allows for quick data retrieval and storage.

        c) Scheduler Component:
        The Consumer Application includes a scheduler component that runs every 5 seconds. Its main task is 
        to periodically check the Redis Cache for any available data.
        
        d) Postgres Database:
        If the scheduler component finds any data in the Redis Cache, it stores the data into a Postgres Database. 
        Postgres is a reliable and widely-used open-source relational database management system.

        By breaking down the process into detailed steps, we can understand the flow of data from the initial CSV 
        file to its storage in the Postgres Database via RabbitMQ and Redis Cache.
