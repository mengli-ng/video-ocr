# video-ocr

## 安装说明 (centos)

1. [安装docker](https://docs.docker.com/engine/installation/linux/centos/)
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
