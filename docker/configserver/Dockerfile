FROM anapsix/alpine-java:8_jdk
MAINTAINER luongbangvh@gmail.com

# Define some directories
ENV DIR_ROOT /home/config
ENV DIR_CONFIG /opt/config-server

# Install necessary software
RUN apk --no-cache add netcat-openbsd

# add necessary assets to server
ADD application.properties ${DIR_ROOT}/application.properties
ADD config-server.jar ${DIR_CONFIG}/config-server.jar
ADD start-server.sh ${DIR_CONFIG}/start-server.sh
RUN dos2unix ${DIR_CONFIG}/start-server.sh

RUN useradd -ms /bin/bash config

WORKDIR ${DIR_ROOT}

RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa .ssh/id_rsa
ADD id_rsa.pub .ssh/id_rsa.pub
RUN chmod 600 .ssh/id_rsa && chmod 600 .ssh/id_rsa.pub

RUN chown -R config:config ${DIR_CONFIG}
RUN chown -R config:config ${DIR_ROOT}

USER config
RUN chmod 700 ${DIR_CONFIG}/start-server.sh

ENTRYPOINT ["/opt/config-server/start-server.sh"]

EXPOSE 8888:8888