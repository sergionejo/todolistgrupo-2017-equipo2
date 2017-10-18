#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

docker run -d --rm --name play-mysql -v ${PWD}/stage:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=mads -e MYSQL_DATABASE=mads mysql

#docker run -d --rm -p 3306:3306 --name play-mysql -e MYSQL_ROOT_PASSWORD=mads -e MYSQL_DATABASE=mads mysql
