FROM amazoncorretto:17-alpine-jdk
MAINTAINER bitoffer.cn
COPY target/gateway.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]