#!/bin/sh
BASEDIR=$(dirname "$0")
while (! nc -z elasticsearch 9300; ! nc -z mysql 3306; ! nc -z configserver 8888;); do
    echo "Waiting for upcoming Elasticsearch, Mysql, Config Server"
    sleep 2
done
java -Djava.security.egd=file:/dev/./urandom -jar ${BASEDIR}/vaadin-example-api.jar