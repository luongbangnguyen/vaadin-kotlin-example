FROM jdkserver
MAINTAINER luongbangvh@gmail.com

# Define some directories
ENV DIR_ROOT /home/config
ENV DIR_CONFIG /opt/config-server

# Install necessary software
Run apt-get update
RUN apt-get install -y netcat git openssh-client dos2unix

# add necessary assets to server
ADD environment.properties ${DIR_CONFIG}/environment.properties

ADD config-server.jar ${DIR_CONFIG}/config-server.jar
ADD start-server.sh ${DIR_CONFIG}/start-server.sh
RUN apt-get install -y dos2unix
RUN dos2unix ${DIR_CONFIG}/start-server.sh

RUN adduser config

WORKDIR ${DIR_ROOT}

RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa .ssh/id_rsa
ADD id_rsa.pub .ssh/id_rsa.pub
RUN chmod 600 .ssh/id_rsa && chmod 600 .ssh/id_rsa.pub

RUN chown -R config:config ${DIR_CONFIG}
RUN chown -R config:config ${DIR_ROOT}

USER config
RUN chmod 700 ${DIR_CONFIG}/start-server.sh

WORKDIR ${DIR_CONFIG}
ENTRYPOINT ["/opt/config-server/start-server.sh"]

EXPOSE 8888:8888