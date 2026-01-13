#it jdk used maven to build our code every time we run.
FROM maven:3.9.9-eclipse-temurin-21 AS builder

#start container by specifying working directory
WORKDIR /app

#for install all dependency as we need by copy pox.ml
COPY pom.xml .

#to cache dependency every time we start container using mvn
RUN mvn dependency:go-offline -B

#copy all source code form src

COPY src ./src

RUN mvn clean package

#to run our builder code using jdk
FROM eclipse-temurin:21-jdk AS runner

#copy below file from above builder
COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "/app.jar"]
