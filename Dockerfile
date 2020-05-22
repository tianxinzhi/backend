FROM maven:3.6.3-jdk-8

WORKDIR /backend

COPY ./ ./

RUN mvn install:install-file -Dfile=./ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar

RUN mvn package -Dmaven.test.skip=true


ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/backend/app.jar"]
