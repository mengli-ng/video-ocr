#!/bin/bash

while getopts h:p: option
do
        case "${option}"
        in
                h) host=${OPTARG};;
                p) port=${OPTARG};;
        esac
done

if [ -z "$host" ]; then
    host=`ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'`
fi

if [ -z "$port" ]; then
    port=80
fi

export VIDEO_OCR_HOME=$( cd $(dirname $0) ; pwd -P )

export HOST_NAME=${host}
export HOST_PORT=${port}

export MYSQL_DATABASE=video_ocr
export MYSQL_ROOT_PASSWORD=password
export SERVER_CONTEXT_PATH=/ocr-server

docker-compose up
echo "started at ${host}:${port}"