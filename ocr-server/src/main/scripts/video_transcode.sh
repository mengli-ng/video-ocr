#!/usr/bin/env bash

while getopts i:o:f:v:b: option
do
        case "${option}"
        in
                i) input=${OPTARG};;
                o) output=${OPTARG};;
                f) format=${OPTARG};;
                v) vcodec=${OPTARG};;
                b) bitrate=${OPTARG};;
        esac
done

ffmpeg -y -i ${input} -v error -filter_complex "[0:v]scale=640:-2[out1]" -map_metadata -1 -map [out1] -c:v ${vcodec} -b:v ${bitrate} -r 25.0 -g 33 -pix_fmt yuv420p -map 0:a -c:a aac -ab 64k -ac 2 -ar 44100 -f ${format} ${output}