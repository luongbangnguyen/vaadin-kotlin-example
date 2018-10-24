FROM jdkserver
MAINTAINER luongbangvh@gmail.com

ENV WEB_FILE vaadin-example-web.jar
ENV SCRIPT_FILE start-server.sh
ENV LIB_DIR /opt/vaadin-example
ENV SPRING_OUTPUT_ANSI_ENABLED ALWAYS

RUN mkdir -p ${LIB_DIR}

RUN apt-get install -y netcat dos2unix

COPY ${WEB_FILE} ${LIB_DIR}/${WEB_FILE}
COPY ${SCRIPT_FILE} ${LIB_DIR}/${SCRIPT_FILE}
RUN dos2unix ${LIB_DIR}/${SCRIPT_FILE}

WORKDIR ${LIB_DIR}
RUN chmod 700 ${SCRIPT_FILE}
ENTRYPOINT ["./start-server.sh"]

EXPOSE 8080