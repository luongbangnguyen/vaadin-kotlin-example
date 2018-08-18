FROM centos
MAINTAINER luongbangvh@gmail.com

ENV DIR_ROOT /home/git
ENV DIR_GIT /srv/project.git
ENV DIR_BIN /opt/gitserver

RUN adduser git
RUN yum install -y openssh openssh-server git dos2unix

RUN mkdir -p ${DIR_GIT}
WORKDIR ${DIR_GIT}
RUN git --bare init

RUN mkdir -p /var/run/sshd
RUN /usr/bin/ssh-keygen -A

WORKDIR ${DIR_ROOT}
RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa.pub .ssh/id_rsa.pub
RUN touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys
RUN cat .ssh/id_rsa.pub >> .ssh/authorized_keys
RUN rm .ssh/id_rsa.pub

RUN chown -R git:git ${DIR_ROOT}
RUN chown -R git:git ${DIR_GIT}

RUN mkdir -p ${DIR_BIN}
ADD start.sh ${DIR_BIN}/start.sh
RUN chmod 700 ${DIR_BIN}/start.sh
RUN dos2unix ${DIR_BIN}/start.sh
WORKDIR ${DIR_BIN}
ENTRYPOINT ["./start.sh"]

EXPOSE 2222:22
EXPOSE 22:22