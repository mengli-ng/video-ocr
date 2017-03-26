#!/usr/bin/env bash

while getopts i: option
do
        case "${option}"
        in
                i) input=${OPTARG};;
        esac
done

ffprobe -v quiet -print_format json -show_format -show_streams ${input}