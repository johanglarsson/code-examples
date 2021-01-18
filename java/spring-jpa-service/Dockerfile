FROM adoptopenjdk:11 as builder

WORKDIR /app
COPY . /app

RUN ./mvnw package -DskipTests

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar


RUN true
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder app/dependencies/ ./
RUN true
COPY --from=builder app/snapshot-dependencies/ ./
RUN true
COPY --from=builder /app/spring-boot-loader/ ./
RUN true
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
