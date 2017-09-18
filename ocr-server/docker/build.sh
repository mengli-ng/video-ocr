#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-server "$dir"
docker tag mengli/video-ocr-server:latest registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server:latest
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server:latest