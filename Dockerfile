FROM openjdk:11.0.11-jdk
COPY "./target/heroes-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
