FROM anapsix/alpine-java:8_server-jre

RUN apk add --update curl && \
    rm -rf /var/cache/apk/*

ENV apiPort=8080 dbHost=127.0.0.1 dbPort=3306 database=diluv dbUsername=root

ADD API.jar /app/
CMD ["/bin/sh", "-c", "java -jar /app/API.jar"]