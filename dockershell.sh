#!/bin/bash

if [ -z "$1" ] || [ "$1" != "dev" ] && [ "$1" != "prod" ]; then
 echo "Usage: $0 <dev|prod>"
 exit 1
fi

PROFILE="$1"


DOCKER_IMAGE_NAME="s3dockerimgdev_ps"
CONTAINER_NAME="s3dockercontainerdev_ps"

DOCKER_IMAGE_TAG="v2.2.3"  # Use any desired tag

DOCKER_IMAGE_NAME_PROD="s3dockerimgprod"
CONTAINER_NAME_PROD="s3dockercontainerprod"



docker_available() {
  command -v docker &>/dev/null
}

# Function to check if the container exists (either running or stopped)
container_exists() {
  local container_name=$1
  local existing_containers=$(docker ps -aq --filter "name=$container_name")
  [ -n "$existing_containers" ]
}

# Function to check if the container is running
container_running() {
  local container_name=$1
  local running_containers=$(docker ps --quiet --filter "name=$container_name")
  [ -n "$running_containers" ]
}


if [ "$PROFILE" = "dev" ]; then
 if container_exists $CONTAINER_NAME; then
  # Check if the container is running
    if container_running $CONTAINER_NAME; then
      echo "Container '$CONTAINER_NAME' is already in running state."
      docker stop $CONTAINER_NAME
      echo "Container '$CONTAINER_NAME' has stopped"
      echo "Re-run the script to start the Container '$CONTAINER_NAME_PROD'"
    else
      echo "Container '$CONTAINER_NAME' is started."
      docker start $CONTAINER_NAME
   fi
 else
        echo "Building Spring Boot application with profile: $PROFILE"

        mvn clean install -P$PROFILE
        docker build -t $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG .
        docker run -p 8080:8080 --name $CONTAINER_NAME --network="host" $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG

 fi

elif [ "$PROFILE" = "prod" ]; then
 if container_exists $CONTAINER_NAME_PROD; then
  # Check if the container is running
    if container_running $CONTAINER_NAME_PROD; then
      echo "Container '$CONTAINER_NAME_PROD' is already in running state."
      docker stop $CONTAINER_NAME_PROD
      echo "Container '$CONTAINER_NAME_PROD' has stopped"
      echo "Re-run the script to start the Container '$CONTAINER_NAME_PROD'"
    else
      echo "Container '$CONTAINER_NAME_PROD' is started."
      docker start $CONTAINER_NAME_PROD
   fi
 else
        echo "Building Spring Boot application with profile: $PROFILE"

        mvn clean install -P$PROFILE
        docker build -t $DOCKER_IMAGE_NAME_PROD:$DOCKER_IMAGE_TAG .
        docker run -p 8080:8080 --name $CONTAINER_NAME_PROD $DOCKER_IMAGE_NAME_PROD:$DOCKER_IMAGE_TAG


 fi
fi


