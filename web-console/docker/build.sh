#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t dreamcoder/video-ocr-web "$dir"
docker tag dreamcoder/video-ocr-web:latest registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-web:latest
docker push registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-web:latest
