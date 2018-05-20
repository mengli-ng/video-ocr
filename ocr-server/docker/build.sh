#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-server/baidu-ocr_v2 "$dir"
docker tag mengli/video-ocr-server/baidu-ocr_v2 registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server_baidu-ocr_v2
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server_baidu-ocr_v2