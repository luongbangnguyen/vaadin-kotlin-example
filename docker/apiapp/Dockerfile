FROM anapsix/alpine-java:8_jdk
MAINTAINER luongbangvh@gmail.com

ENV JAR_FILE vaadin-example-api.jar
ENV SCRIPT_FILE start-server.sh
ENV LIB_DIR /opt/vaadin-example
ENV SPRING_OUTPUT_ANSI_ENABLED ALWAYS

RUN mkdir -p ${LIB_DIR}

RUN apk --no-cache add netcat-openbsd

COPY ${JAR_FILE} ${LIB_DIR}/${JAR_FILE}
COPY ${SCRIPT_FILE} ${LIB_DIR}/${SCRIPT_FILE}
RUN dos2unix ${LIB_DIR}/${SCRIPT_FILE}

WORKDIR ${LIB_DIR}
RUN chmod 700 ${SCRIPT_FILE}
ENTRYPOINT ["./start-server.sh"]

EXPOSE 8181