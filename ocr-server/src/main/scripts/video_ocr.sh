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
    crop_file="${file%.*}_crop.jpg"
    convert ${file} -crop ${w}x${h}+${x}+${y} ${crop_file}

    # 将图片二值化处理
    threshold_file="${file%.*}_threshold.jpg"
    convert -scale 150% -compress none -depth 8 -alpha off -monochrome ${crop_file} ${threshold_file}

    # 字幕识别
    result_file="${file%.*}"
    tesseract ${threshold_file} ${result_file} -l ${language}
}

mkdir -p ${output}
ffmpeg -i ${input} -vf fps=1 "${output}/%5d.jpg"

for file in `find ${output} -name '*.jpg'`; do
    while [[ `jobs | wc -l` -gt ${threads} ]]; do
        sleep 5
    done
    ocr ${file} &
done
wait