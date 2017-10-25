FROM ubuntu
MAINTAINER luongbangvh@gmail.com

ENV DIR_ROOT /home/git
ENV DIR_GIT /srv/project.git
ENV DIR_BIN /opt/git-server

# Install necessary software
RUN apt-get update && apt-get install -y git openssh-server dos2unix

# Add script start services
RUN mkdir ${DIR_BIN}
ADD start-service.sh ${DIR_BIN}/start-service.sh
RUN dos2unix ${DIR_BIN}/start-service.sh
RUN chmod 700 ${DIR_BIN}/start-service.sh


# Create user for git
RUN useradd -ms /bin/bash git

WORKDIR ${DIR_ROOT}

RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa.pub .ssh/id_rsa.pub
RUN touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys
RUN cat .ssh/id_rsa.pub >> .ssh/authorized_keys
RUN rm .ssh/id_rsa.pub

RUN mkdir -p ${DIR_GIT}
RUN mkdir -p /var/run/sshd

RUN chown -R git:git ${DIR_ROOT}
RUN chown -R git:git ${DIR_GIT}

USER git
WORKDIR ${DIR_GIT}
RUN git --bare init

USER root
WORKDIR ${DIR_BIN}
ENTRYPOINT ["./start-service.sh"]
EXPOSE 22
EXPOSE 2222





