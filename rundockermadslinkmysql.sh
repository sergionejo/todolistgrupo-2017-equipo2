#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

docker run --link play-mysql:mysql --rm -it -p 8080:9000 -e \
DB_URL="jdbc:mysql://play-mysql:3306/mads" -e DB_USER_NAME="root" -e \
DB_USER_PASSWD="mads" -v${PWD}:/code domingogallardo/playframework /bin/bash
