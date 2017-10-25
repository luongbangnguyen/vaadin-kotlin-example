#!/bin/sh
BASEDIR=$(dirname "$0")
cd ~
while (! nc -z gitserver 22); do
    echo "Waiting for git is stared"
    sleep 2
done
git clone git@gitserver:/srv/project.git
mv application.properties project
cd project
git add .
git commit -m "initial configuration"
git push -u orgin master
java -Djava.security.egd=file:/dev/./urandom -jar ${BASEDIR}/config-server.jar