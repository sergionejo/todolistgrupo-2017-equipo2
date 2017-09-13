#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

docker run --rm  -it -v "${PWD}:/code" -p 8080:9000 domingogallardo/playframework
