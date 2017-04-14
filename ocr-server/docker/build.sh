#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t dreamcoder/video-ocr-server "$dir"
docker tag dreamcoder/video-ocr-server:latest registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-server:latest
docker push registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-server:latest