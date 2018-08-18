FROM centos
MAINTAINER luongbangvh@gmail.com
ENV JDK_INSTALL jdk-10.0.2
RUN mkdir -p /opt/jdk
WORKDIR /opt/jdk
RUN  yum install -y wget
RUN wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/10.0.2+13/19aef61b38124481863b1413dce1855f/jdk-10.0.2_linux-x64_bin.tar.gz
RUN tar -xvf jdk-10.0.2_linux-x64_bin.tar.gz
RUN rm jdk-10.0.2_linux-x64_bin.tar.gz

ENV JAVA_HOME=/opt/jdk/${JDK_INSTALL}
ENV PATH=${PATH}:/opt/jdk/${JDK_INSTALL}/bin