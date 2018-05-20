## 安装docker

<pre>./install-docker.sh</pre>

## 设置env

<pre>vim /etc/profile</pre>

在最后增加一行
<pre>export HOST_IP_ADDRESS="public_ip_address"</pre>

执行
<pre>source /etc/profile</pre>

## 创建文件夹
<pre>mkdir -p /var/lib/mysql</pre>
<pre>mkdir -p /var/logs</pre>

## 初始化docker swarm并部署
<pre>docker swarm init</pre>
<pre>docker stack deploy -c docker-compose.yml video-ocr</pre>
