FROM maven:3.6.3-jdk-13

RUN mkdir /workspace/

COPY pom.xml /workspace/
COPY src /workspace/src/

CMD ["mvn", "compile", "quarkus:dev", "-Dquarkus.live-reload.password=123"]
