#!/bin/bash

#sudo groupadd docker
#sudo gpasswd -a ${USER} docker

docker exec play-mysql sh -c 'exec mysqldump --no-data mads -uroot -pmads' > database/schema.sql
cp database/schema.sql stage/aa_schema.sql