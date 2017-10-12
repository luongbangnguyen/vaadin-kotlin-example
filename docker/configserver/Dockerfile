FROM ubuntu
MAINTAINER luongbangvh@gmail.com

# Define some directories
ENV DIR_ROOT /home/git
ENV DIR_GIT /srv/project.git
ENV DIR_CONFIG /opt/config-server

# Install necessary software
RUN apt-get update && apt-get install -y git openssh-server dos2unix
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && apt-get update && apt-get install -y software-properties-common && add-apt-repository -y ppa:webupd8team/java && apt-get update && apt-get install -y oracle-java8-installer && rm -rf /var/lib/apt/lists/* && rm -rf /var/cache/oracle-jdk8-installer

RUN service ssh start

# Create user for git
RUN useradd -ms /bin/bash git

# add necessary assets to server
ADD config-server.jar ${DIR_CONFIG}/config-server.jar
ADD start-server.sh ${DIR_CONFIG}/start-server.sh
RUN chown -R git:git ${DIR_CONFIG}

RUN mkdir -p ${DIR_GIT}
RUN chown -R git:git ${DIR_GIT}

WORKDIR ${DIR_ROOT}

RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa .ssh/id_rsa
ADD id_rsa.pub .ssh/id_rsa.pub
RUN touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys
RUN cat .ssh/id_rsa.pub >> .ssh/authorized_keys
RUN chown -R git:git ${DIR_ROOT}

USER git
WORKDIR ${DIR_GIT}
RUN git --bare init

ENTRYPOINT ["/opt/config-server/start-server.sh"]

EXPOSE 8888:8888