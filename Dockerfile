FROM openjdk:24

WORKDIR /app

COPY ./src ./src

RUN javac src/Main.java -d .

CMD ["java", "Main"]