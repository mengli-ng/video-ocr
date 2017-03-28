# video-ocr
**video-ocr**是一个视频字幕识别的应用程序，包括服务端(ocr-server)和客户端（web-console），具有视频管理和字幕识别任务管理的功能，
字幕识别使用了[imagemagick](https://www.imagemagick.org)和[tesseract-ocr](https://github.com/tesseract-ocr/tesseract)。

## 开发
**video-ocr**使用了以下框架

* [spring-boot](https://projects.spring.io/spring-boot/)
* [angular2](https://angular.io/)
* [angular-cli](http://cli.angular.io/)
* [angular-material](https://material.angular.io/)

## 发布到[Docker Hub](https://hub.docker.com/)
更新应用程序时，需要重新发布到Docker Hub用于应用程序的部署。

* 在ocr-server项目中执行以下步骤
<pre>
mvn clean package
cd docker
./build.sh
</pre>

* 在web-console项目中执行以下步骤
<pre>
npm run release
cd docker
./build.sh
</pre>

发布的docker repository
* [dreamcoder/video-ocr-server](https://hub.docker.com/r/dreamcoder/video-ocr-server/)
* [dreamcoder/video-ocr-web](https://hub.docker.com/r/dreamcoder/video-ocr-web/)

## 部署
**video-ocr**使用[docker](https://www.docker.com/)进行部署，安装时需要执行以下步骤，以centos为例。

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
sudo yum-config-manager --disable docker-ce-edge
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

3. 下载并运行docker-compose脚本

<pre>
wget https://github.com/dream83619/video-ocr/releases/download/v0.0.1/docker-compose.zip
unzip docker-compose.zip && cd docker-compose
</pre>

* 启动服务
<pre>
./start.sh
</pre>

start.sh支持以下参数：
* -h _host_: 服务IP地址，默认为网卡1的IP地址
* -p _ip_: 服务端口号，默认为80

客户端将通过浏览器打开 _http://host:ip/_ 访问服务

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
ExecStart=/path/to/start.sh
ExecStop=/path/to/stop.sh
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
</pre>
需要修改 _/path/to/start.sh_ 和 _/path/to/stop.sh_ 为实际的路径

启用服务
<pre>
systemctl enable video-ocr.service
</pre>
启动，停止，重启服务
<pre>
systemctl start video-ocr.service
systemctl stop video-ocr.service
systemctl restart video-ocr.service
</pre>