FROM adoptopenjdk/maven-openjdk11 as builder
LABEL MAINTAINER="Clement PERRIN<clement.perrin@etu.univ-smb.fr>"

WORKDIR /usr/src/myapp
COPY . .

RUN ./mvnw install -DskipTests

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

RUN java -Djarmode=layertools -jar target/application.jar extract --destination target/extracted

FROM adoptopenjdk/maven-openjdk11

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG EXTRACTED=/usr/src/myapp/target/extracted
WORKDIR application

COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./

ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-Dspring.main.lazy-initialization=true","org.springframework.boot.loader.JarLauncher"]
