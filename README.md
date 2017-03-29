# video-ocr
video-ocr是一个视频字幕识别的应用程序，包括服务端(ocr-server)和客户端(web-console)，具有视频管理和任务管理的功能，
字幕识别通过[imagemagick](https://www.imagemagick.org)和[tesseract-ocr](https://github.com/tesseract-ocr/tesseract)实现。

## 开发
video-ocr使用了以下框架

* [spring-boot](https://projects.spring.io/spring-boot/)
* [angular2](https://angular.io/)
* [angular-cli](http://cli.angular.io/)
* [angular-material](https://material.angular.io/)

## 发布到阿里云镜像仓库

因为Docker Hub的访问速度很慢，使用了阿里云的镜像仓库

* 在ocr-server中执行
<pre>
mvn clean package
cd docker
./build.sh
</pre>

* 在web-console中执行
<pre>
npm run release
cd docker
./build.sh
</pre>

## 部署
video-ocr使用[docker](https://www.docker.com/)进行部署，部署时需要执行以下步骤（以下以centos为例，其余环境下的操作类似）

1. 安装[docker](https://docs.docker.com/engine/installation/linux/centos/)

<pre>
sudo yum install -y yum-utils
</pre>
<pre>
$ sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
</pre>
<pre>
sudo yum-config-manager --enable docker-ce-edge
</pre>
<pre>
sudo yum makecache fast
</pre>
<pre>
sudo yum install docker-ce
</pre>
<pre>
sudo systemctl start docker
</pre>


2. 安装[docker-compose](https://docs.docker.com/compose/install/)

<pre>
$ curl -L "https://github.com/docker/compose/releases/download/1.11.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
</pre>
<pre>
chmod +x /usr/local/bin/docker-compose
</pre>

3. 配置阿里云镜像加速器

从国内直接下载docker的速度很慢，需要配置国内的镜像加速器
<pre>
sudo mkdir -p /etc/docker
sudo vi /etc/docker/daemon.json
</pre>
输入以下内容
<pre>
{
  "registry-mirrors": ["https://6adape9u.mirror.aliyuncs.com"]
}
</pre>
重启Docker
<pre>
sudo systemctl daemon-reload
sudo systemctl restart docker
</pre>

4. 下载并运行docker-compose脚本

<pre>
wget https://github.com/dream83619/video-ocr/releases/download/v0.0.1/video-ocr.zip
unzip video-ocr.zip
cd video-ocr
</pre>

* 启动服务
<pre>
./start.sh -h ip -p 80
</pre>

start.sh包括以下参数：
* -h 服务器IP地址，默认为网卡1的IP地址
* -p 服务端口号，默认为80

客户端通过 _http://ip:port/_ 访问服务

* 停止服务
<pre>
./stop.sh
</pre>


4. 将docker-compose脚本制作为service

将服务制作为service，使服务以daemon方式运行，并随系统自启动，通过如下方式制作service
<pre>
vi /etc/systemd/system/video-ocr.service
</pre>
输入以下内容
<pre>
[Unit]
Description=video-ocr
After=syslog.target

[Service]
User=root
ExecStart=/path/to/start.sh -h ip -p 80
ExecStop=/path/to/stop.sh
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
</pre>
将User, ExecStart, ExecStop修改为为实际的用户和路径
启用服务
<pre>
systemctl enable video-ocr.service
</pre>
启动，停止，重启服务
<pre>
systemctl start video-ocr
systemctl stop video-ocr
systemctl restart video-ocr
</pre>
