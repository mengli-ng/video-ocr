#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-web/baidu-ocr "$dir"
docker tag mengli/video-ocr-web/baidu-ocr registry.cn-beijing.aliyuncs.com/mengli/video-ocr-web_baidu-ocr
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-web_baidu-ocr
