#!/bin/sh
BASEDIR=$(dirname "$0")
while (! nc -z gitserver 22); do
    echo "Waiting for git is stared"
    sleep 2
done
if [ -d "project" ]; then
    echo "project folder existed"
else
    ssh-keyscan -H gitserver >> ~/.ssh/known_hosts
    git config --global user.email "luongbangvh@gmail.com"
    git config --global user.name "Nguyen Luong Bang"
    git clone git@gitserver:/srv/project.git
    ls -A project

    if [ -f "project/environment.properties" ]; then
        echo "environment.properties existed"
    else
        mv  environment.properties project
    fi

    if [ -f "project/environment.properties" ]; then
        cd project
        git add .
        git commit -m "initial configuration"
        git push -u origin master
    fi

fi

java -Djava.security.egd=file:/dev/./urandom -jar ${BASEDIR}/config-server.jar