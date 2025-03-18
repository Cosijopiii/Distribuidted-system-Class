FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM maven:3.8.3-openjdk-17
COPY --from=build /app/target/*-jar-with-dependencies.jar /app.jar
CMD ["sleep", "infinity"]

