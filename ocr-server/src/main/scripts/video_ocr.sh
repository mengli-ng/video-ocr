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
                l) language=${OPTARG};;
                t) threads=${OPTARG};;
        esac
done

ocr () {
    file=$1

    # 从图片中截取文字区域
    source=${file}
    dest="${file%.*}_convert_1.jpg"
    convert ${source} -crop ${w}x${h}+${x}+${y} ${dest}

    # 过滤背景色（保留白色）
    source=${dest}
    dest="${file%.*}_convert_2.jpg"
    convert ${source} -alpha set -channel RGBA -fuzz 35% -fill "rgb(0,0,0)" +opaque "rgb(255,255,255)" ${dest}

    # 字幕识别
    result="${file%.*}"
    tesseract ${dest} ${result} -l ${language}
}

mkdir -p ${output}
ffmpeg -i ${input} -vf fps=1 "${output}/%5d.jpg"

for file in `find ${output} -name '*.jpg'`; do
    while [[ `jobs | wc -l` -gt ${threads} ]]; do
        sleep 1
    done
    ocr ${file} &
done
wait