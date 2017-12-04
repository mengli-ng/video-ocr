#!/usr/bin/env bash

while getopts i:o:x:y:w:h:l:t: option
do
        case "${option}"
        in
                i) input=${OPTARG};;
                o) output=${OPTARG};;
                x) x=${OPTARG};;
                y) y=${OPTARG};;
                w) w=${OPTARG};;
                h) h=${OPTARG};;
        esac
done

mkdir -p ${output}
ffmpeg -i ${input} -vf fps=1 "${output}/%5d.jpg"

for file in `find ${output} -name '*.jpg'`; do
    dest="${file%.*}_crop.jpg"
    convert ${file} -crop ${w}x${h}+${x}+${y} ${dest}
    rm -f ${file}
done