#!/bin/bash

if [ -z "$1" ] || [ "$1" != "dev" ] && [ "$1" != "prod" ]; then
 echo "Usage: $0 <dev|prod>"
 exit 1
fi

PROFILE="$1"
APP_JAR="controller/target/controller-0.0.1-SNAPSHOT.war"

mvn clean package -P$PROFILE

if [ $? -ne 0 ]; then
 echo "Maven build failed. Exiting..."
 exit 1
fi

java -jar $APP_JAR