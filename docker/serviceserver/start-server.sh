#!/bin/sh
BASEDIR=$(dirname "$0")
while (! nc -z elasticsearch 9300); do
    echo "Waiting for upcoming Elastic search server"
    sleep 2
done

while (! nc -z mysql 3306); do
    echo "Waiting for upcoming Mysql Server"
    sleep 2
done

while (! nc -z configserver 8888); do
    echo "Waiting for upcoming Config Server"
    sleep 2
done

while (! nc -z eurekaserver 8761); do
    echo "Waiting for upcoming Eureka Server"
    sleep 2
done

java -Djava.security.egd=file:/dev/./urandom -jar ${BASEDIR}/${WEB_FILE}