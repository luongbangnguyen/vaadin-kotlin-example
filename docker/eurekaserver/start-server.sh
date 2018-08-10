#!/bin/sh
BASEDIR=$(dirname "$0")
java -Djava.security.egd=file:/dev/./urandom -jar ${BASEDIR}/${WEB_FILE}