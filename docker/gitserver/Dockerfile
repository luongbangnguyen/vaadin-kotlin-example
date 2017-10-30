FROM solita/ubuntu-systemd
MAINTAINER luongbangvh@gmail.com

EXPOSE 22

ENV DIR_ROOT /home/git
ENV DIR_GIT /srv/project.git

RUN useradd -ms /bin/bash git
RUN apt-get update && apt-get install -y --no-install-recommends openssh-server git && rm -rf /var/lib/apt/lists/*

RUN mkdir -p ${DIR_GIT}
WORKDIR ${DIR_GIT}
RUN git --bare init

RUN mkdir -p /var/run/sshd

COPY sshd.service /etc/systemd/system/
RUN chmod 664 /etc/systemd/system/sshd.service
RUN systemctl enable sshd.service

COPY sshd.service /etc/systemd/system/
RUN chmod 664 /etc/systemd/system/sshd.service
RUN systemctl enable sshd.service


WORKDIR ${DIR_ROOT}
RUN mkdir .ssh && chmod 700 .ssh
ADD id_rsa.pub .ssh/id_rsa.pub
RUN touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys
RUN cat .ssh/id_rsa.pub >> .ssh/authorized_keys
RUN rm .ssh/id_rsa.pub

RUN chown -R git:git ${DIR_ROOT}
RUN chown -R git:git ${DIR_GIT}