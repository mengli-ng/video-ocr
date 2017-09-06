#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-web "$dir"
docker tag mengli/video-ocr-web:latest registry.cn-beijing.aliyuncs.com/mengli/video-ocr-web:latest
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-web:latest
