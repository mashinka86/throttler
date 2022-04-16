FROM openjdk:11.0.14-jdk-oracle
COPY build/libs/throttler-0.0.1-SNAPSHOT.jar throttler-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/throttler-0.0.1-SNAPSHOT.jar"]