#!/bin/bash

while getopts h:p:v:m: option
do
        case "${option}"
        in
                h) host=${OPTARG};;
                p) port=${OPTARG};;
                v) volume=${OPTARG};;
                m) mac=${OPTARG};;
        esac
done

if [ -z "$host" ]; then
    host=`ip route get 1 | awk '{print $NF;exit}'`
fi

if [ -z "$port" ]; then
    port=80
fi

if [ -z "$volume" ]; then
    volume=/var/video-ocr
fi

if [ -z "$mac" ]; then
    mac=`cat /sys/class/net/eth0/address`
fi

echo "video-ocr starting at ${host}:${port}"

export VIDEO_OCR_HOME=$( cd $(dirname $0) ; pwd -P )
export HOST_NAME=${host}
export HOST_PORT=${port}
export HOST_VOLUME=${volume}
export HOST_MAC_ADDRESS=${mac}
export MYSQL_DATABASE=video_ocr
export MYSQL_ROOT_PASSWORD=password
export SERVER_CONTEXT_PATH=/ocr-server

cd "${VIDEO_OCR_HOME}"
docker-compose pull
docker-compose up --force-recreate
