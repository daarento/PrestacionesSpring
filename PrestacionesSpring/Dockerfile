FROM openjdk:17
COPY "./target/PrestacionesSpring-0.0.1-SNAPSHOT.war" "pres.war"
EXPOSE 8080
ENTRYPOINT ["java","-jar","pres.war"]