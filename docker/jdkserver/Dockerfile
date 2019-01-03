FROM ubuntu
MAINTAINER luongbangvh@gmail.com
ENV JDK_TAR_FILE jdk-11.0.1_linux-x64_bin.tar.gz
ENV JDK_INSTALL jdk-11.0.1
RUN mkdir -p /opt/jdk
WORKDIR /opt/jdk
RUN apt-get update
RUN  apt-get install -y wget
RUN wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/11.0.1+13/90cf5d8f270a4347a95050320eef3fb7/jdk-11.0.1_linux-x64_bin.tar.gz
RUN tar -xvf ${JDK_TAR_FILE}
RUN rm ${JDK_TAR_FILE}

ENV JAVA_HOME=/opt/jdk/${JDK_INSTALL}
ENV PATH=${PATH}:/opt/jdk/${JDK_INSTALL}/bin