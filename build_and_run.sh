#!/bin/bash

# Build Project publisher
cd publisher
mvn clean install -DskipTests

# Build Project consumer
cd ../consumer
mvn clean install -DskipTests

# Run Docker composer file
docker-compose -p viswals-pt up
