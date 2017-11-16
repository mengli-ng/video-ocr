#!/bin/bash

dir=`cd $(dirname $0) ; pwd -P`

docker build -t mengli/video-ocr-web/tesseract-ocr "$dir"
docker tag mengli/video-ocr-web/tesseract-ocr registry.cn-beijing.aliyuncs.com/mengli/video-ocr-web_tesseract-ocr
docker push registry.cn-beijing.aliyuncs.com/mengli/video-ocr-web_tesseract-ocr
