FROM ubuntu
MAINTAINER luongbangvh@gmail.com

ENV DIR_ROOT /home/git
ENV DIR_GIT /srv/project.git
ENV DIR_CONFIG /opt/config-server

RUN apt-get update
RUN apt-get install software-properties-common
RUN apt-get install git openssh-server
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN sudo apt-get -y install oracle-java8-installer
RUN adduser -D git

WORKDIR ${DIR_ROOT}

RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa .ssh/id_rsa
ADD id_rsa.pub .ssh/id_rsa.pub
RUN touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys
RUN cat .ssh/id_rsa.pub >> .ssh/authorized_keys

RUN mkdir -P /srv/project.git
WORKDIR /srv/project.git
RUN git --bare init

ADD config-server.jar ${DIR_CONFIG}/config-server.jar
ADD start-server.sh ${DIR_CONFIG}/start-server.sh
RUN dos2unix ${DIR_CONFIG}/start-server.sh

RUN chown -R git:git ${DIR_ROOT}
RUN chown -R git:git ${DIR_GIT}

RUN service ssh start

ENTRYPOINT ["opt/config-server/start-server.sh"]

EXPOSE 8888:8888