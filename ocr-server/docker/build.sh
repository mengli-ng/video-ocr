#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-server/tesseract-ocr "$dir"
docker tag mengli/video-ocr-server/tesseract-ocr registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server_tesseract-ocr
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-server_tesseract-ocr
