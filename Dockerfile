FROM openjdk:17-jdk-alpine

# Copy war file
COPY build/libs/ipLimitTest-0.0.1-SNAPSHOT.jar /ipLimitTest.jar

# run the app
ENTRYPOINT ["java","-jar","/ipLimitTest.jar"]