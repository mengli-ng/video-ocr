yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2

yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

yum install docker-ce
systemctl enable docker; systemctl start docker

mkdir -p /etc/docker
echo '{ "registry-mirrors": ["https://6adape9u.mirror.aliyuncs.com"] }' > /etc/docker/daemon.json
systemctl daemon-reload; systemctl restart docker
