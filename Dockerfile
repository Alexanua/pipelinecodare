FROM eclipse-temurin:17-jdk-alpine
RUN apk add curl
VOLUME /tmp
EXPOSE 8085
ADD target/pipelinecodare-0.0.1-SNAPSHOT.jar pipelinecodare.jar
ENTRYPOINT ["java","-jar","/pipelinecodare.jar"]
