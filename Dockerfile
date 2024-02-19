FROM eclipse-temurin:17-jdk-alpine
RUN apk add curl
VOLUME /tmp
EXPOSE 8085
ADD target/pipelinecodare.jar pipelinecodare.jar
ENTRYPOINT ["java","-jar","/pipelinecodare.jar"]
