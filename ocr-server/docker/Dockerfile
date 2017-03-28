FROM nimmis/java-centos:oracle-8-jdk
MAINTAINER mengli <dream83619@gmail.com>

RUN yum update -y && yum install -y ImageMagick.x86_64
RUN yum install -y gcc gcc-c++ make autoconf automake libtool libjpeg-devel libpng-devel libtiff-devel zlib-devel unzip
RUN wget http://www.leptonica.org/source/leptonica-1.72.tar.gz && \
tar -xzvf leptonica-1.72.tar.gz && \
cd leptonica-1.72 && \
./configure && \
make && \
make install && \
cd .. && rm -f leptonica-1.72.tar.gz && rm -rf leptonica-1.72 && \
wget https://github.com/tesseract-ocr/tesseract/archive/3.04.zip && \
unzip 3.04.zip && \
cd tesseract-3.04 && \
./autogen.sh && \
./configure && \
make && \
make install && \
ldconfig && \
cd .. && rm -f 3.04.zip && rm -rf tesseract-3.04

RUN wget https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chi_sim.traineddata -P /usr/share/tessdata/ && \
wget https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chi_tra.traineddata -P /usr/share/tessdata/

RUN wget https://johnvansickle.com/ffmpeg/builds/ffmpeg-git-64bit-static.tar.xz && \
mkdir ffmpeg-git-64bit-static && tar -xvJf ffmpeg-git-64bit-static.tar.xz -C ffmpeg-git-64bit-static --strip-components=1 && \
cd ffmpeg-git-64bit-static && mv ffmpeg ffprobe /usr/bin && \
cd .. && rm -f ffmpeg-git-64bit-static.tar.xz && rm -rf ffmpeg-git-64bit-static

ADD entrypoint.sh entrypoint.sh
ADD wait-for-it.sh wait-for-it.sh
ADD video_info.sh /scripts/video_info.sh
ADD video_transcode.sh /scripts/video_transcode.sh
ADD video_ocr.sh /scripts/video_ocr.sh
ADD ocr-server.jar ocr-server.jar
RUN chmod +x /usr/bin/ffmpeg /usr/bin/ffprobe /scripts/video_info.sh /scripts/video_transcode.sh /scripts/video_ocr.sh ocr-server.jar
ENTRYPOINT ["./entrypoint.sh"]