## clone项目
<pre>
git clone https://github.com/dream83619/video-ocr
cd video-ocr
git checkout "branch"
</pre>

## 安装docker

<pre>./install-docker.sh</pre>

## 设置env

<pre>vim /etc/profile</pre>

在最后增加一行
<pre>export HOST_IP_ADDRESS="public_ip_address"</pre>

执行
<pre>source /etc/profile</pre>

## 创建文件夹
<pre>
mkdir -p /var/lib/mysql
mkdir -p /var/logs
mkdir -p /var/lib/video-ocr/storage
</pre>

## 初始化docker swarm并部署
<pre>docker swarm init</pre>
<pre>make up</pre>


