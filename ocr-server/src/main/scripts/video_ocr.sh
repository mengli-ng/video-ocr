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

    # 处理原始图片
    # 1. 从图片中截取文字区域
    # 2. 将图片二值化处理
    # 3. 边缘突出显示
    # 4. 过滤背景色（保留白色）
    mogrify -crop ${w}x${h}+${x}+${y} ${file}
    mogrify -scale 150% -compress none -depth 8 -alpha off -monochrome ${file}
    mogrify -edge 5 ${file}
    mogrify -alpha set -channel RGBA -fuzz 20% -fill "rgb(0,0,0)" +opaque "rgb(255,255,255)" ${file}

    # 字幕识别
    result_file="${file%.*}"
    tesseract ${file} ${result_file} -l ${language}
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