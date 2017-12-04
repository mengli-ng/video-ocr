#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-server/baidu-ocr "$dir"
docker tag mengli/video-ocr-server/baidu-ocr registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server_baidu-ocr
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server_baidu-ocr
