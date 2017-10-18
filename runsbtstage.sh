#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

sbt clean stage

target/universal/stage/bin/mads-todolist-2017 -Dconfig.file=${PWD}/conf/stage.conf