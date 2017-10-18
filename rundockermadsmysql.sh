#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

docker run -d --rm -p 3306:3306 --name play-mysql -e MYSQL_ROOT_PASSWORD=mads -e MYSQL_DATABASE=mads mysql
