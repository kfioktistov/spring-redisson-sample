FROM java:8-jre-alpine

RUN apk update && apk add tzdata && cp /usr/share/zoneinfo/Asia/Baku /etc/localtime

WORKDIR /code/
COPY ./target/redisson-sample.jar .

EXPOSE 80

CMD java -jar -Dspring.profiles.active=dev redisson-sample.jar --server.port=80