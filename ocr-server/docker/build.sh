#!/bin/bash

docker build -t dreamcoder/video-ocr-server .
docker tag dreamcoder/video-ocr-server registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-server
docker push registry.cn-beijing.aliyuncs.com/dreamcoder/video-ocr-server