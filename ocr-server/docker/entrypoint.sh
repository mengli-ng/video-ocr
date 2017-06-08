#!/bin/bash

# wait for 15 seconds until mysql is up
/wait-for-it.sh -t 60 db:3306

if [ $? -eq 0 ]
then

  # add execute permission on scripts
  chmod +x /video_info.sh /video_ocr.sh /video_transcode.sh

  if [ ! -d "/storage/wentong" ]; then
      cp -r /wentong /storage/wentong
  fi

  chmod +x /storage/wentong/bin/test_new /storage/wentong/bin/ManagerSrv
  chmod +x /storage/wentong/getinfo/getinfo
  echo /storage/wentong/bin >> /etc/ld.so.conf
  echo /storage/wentong/getinfo >> /etc/ld.so.conf
  ldconfig

  # To reduce Tomcat startup time we added a system property pointing to "/dev/urandom" as a source of entropy.
  java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar /ocr-server.jar
fi