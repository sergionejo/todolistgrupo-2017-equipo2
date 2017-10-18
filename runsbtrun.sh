#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

sbt '; set javaOptions += "-Dconfig.file=conf/integration.conf"; run'
