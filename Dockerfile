FROM gradle:7.2.0-jdk11 AS build
LABEL authors="Losokos"

COPY . .

RUN gradle build --no-daemon

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /matchmaker
COPY --from=build /home/gradle/build/libs/*.jar ./Matchmaker-1.0-STABLE.jar

EXPOSE 8080
CMD ["java", "-jar", "./Matchmaker-1.0-STABLE.jar"]
