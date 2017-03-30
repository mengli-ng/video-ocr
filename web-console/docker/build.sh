#!/bin/bash

docker build -t dreamcoder/video-ocr-web .
docker tag dreamcoder/video-ocr-web registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-web
docker push registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-web
