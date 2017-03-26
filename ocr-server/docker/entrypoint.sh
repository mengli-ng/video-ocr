#!/bin/bash

# wait for 15 seconds until mysql is up
./wait-for-it.sh -t 60 db:3306

if [ $? -eq 0 ]
then
  # To reduce Tomcat startup time we added a system property pointing to "/dev/urandom" as a source of entropy.
  java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar /ocr-server.jar
fi