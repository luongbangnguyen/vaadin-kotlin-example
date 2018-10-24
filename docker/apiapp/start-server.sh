#!/bin/sh
BASEDIR=$(dirname "$0")
while (! nc -z serviceserver 8881); do
    echo "Waiting for upcoming service server"
    sleep 2
done
java -Djava.security.egd=file:/dev/./urandom -jar ${BASEDIR}/${JAR_FILE}