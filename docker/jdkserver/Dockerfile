FROM ubuntu
MAINTAINER luongbangvh@gmail.com
ENV JDK_TAR_FILE jdk-8u192-linux-x64.tar.gz
ENV JDK_INSTALL jdk1.8.0_192
RUN mkdir -p /opt/jdk
WORKDIR /opt/jdk
RUN apt-get update
RUN  apt-get install -y wget
RUN wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u192-b12/750e1c8617c5452694857ad95c3ee230/jdk-8u192-linux-x64.tar.gz
RUN tar -xvf ${JDK_TAR_FILE}
RUN rm ${JDK_TAR_FILE}

ENV JAVA_HOME=/opt/jdk/${JDK_INSTALL}
ENV PATH=${PATH}:/opt/jdk/${JDK_INSTALL}/bin