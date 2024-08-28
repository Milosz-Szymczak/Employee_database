FROM openjdk:22-oracle
COPY target/employees-database.jar employees-database.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "employees-database.jar"]