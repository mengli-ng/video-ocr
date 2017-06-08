FROM nimmis/java-centos:oracle-8-jdk
MAINTAINER mengli <dream83619@gmail.com>

RUN yum install -y gcc gcc-c++ make autoconf automake libtool libjpeg-devel libpng-devel libtiff-devel zlib-devel unzip && yum -y clean all
RUN yum install -y ImageMagick.x86_64  && yum -y clean all
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

RUN wget https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chi_sim.traineddata -P /usr/local/share/tessdata/ && \
wget https://github.com/tesseract-ocr/tessdata/raw/3.04.00/chi_tra.traineddata -P /usr/local/share/tessdata/

RUN wget https://johnvansickle.com/ffmpeg/builds/ffmpeg-git-64bit-static.tar.xz && \
mkdir ffmpeg-git-64bit-static && tar -xvJf ffmpeg-git-64bit-static.tar.xz -C ffmpeg-git-64bit-static --strip-components=1 && \
cd ffmpeg-git-64bit-static && mv ffmpeg ffprobe /usr/bin && \
cd .. && rm -f ffmpeg-git-64bit-static.tar.xz && rm -rf ffmpeg-git-64bit-static

ADD wentong /wentong/
COPY entrypoint.sh wait-for-it.sh video_info.sh video_transcode.sh video_ocr.sh ocr-server.jar /

ENTRYPOINT ["/entrypoint.sh"]