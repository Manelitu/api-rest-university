FROM maven:3.9.0-amazoncorretto-17

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

CMD mvn spring-boot:run