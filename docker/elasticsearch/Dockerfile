FROM jdkserver
MAINTAINER luongbangvh@gmail.com

ENV DIR_ROOT /home/elastic/elasticsearch
ENV ELASTIC_VERSION elasticsearch-6.4.0
ENV DIR_ELASTIC /home/elastic/elasticsearch/${ELASTIC_VERSION}

RUN adduser elastic

WORKDIR ${DIR_ROOT}
RUN wget -O ${ELASTIC_VERSION}.tar.gz https://artifacts.elastic.co/downloads/elasticsearch/${ELASTIC_VERSION}.tar.gz
RUN tar -xvf ${ELASTIC_VERSION}.tar.gz
ADD elasticsearch.yml ${DIR_ELASTIC}/config/elasticsearch.yml
RUN chown -R elastic:elastic ${DIR_ROOT}

USER elastic
WORKDIR ${DIR_ELASTIC}/bin
ENTRYPOINT ["./elasticsearch"]
EXPOSE 9200 9300