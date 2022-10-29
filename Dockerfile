FROM openjdk:8-jdk-alpine3.7 AS builder
RUN java -version

COPY . /usr/src/WebScraper/
WORKDIR /usr/src/WebScraper/
RUN apk --no-cache add maven && mvn --version
RUN mvn package

FROM openjdk:8-jre-alpine3.7
WORKDIR /root/
COPY --from=builder /usr/src/WebScraper/target/WebScraper.jar .

EXPOSE 8123
ENTRYPOINT ["java", "-jar", "./WebScraper.jar"]