#FROM openjdk:24
#
#WORKDIR /app
#
#COPY ./src ./src
#
#RUN javac src/Main.java -d .
#
#CMD ["java", "Main"]


FROM openjdk:24-slim

WORKDIR /app

# درایور JDBC پستگرس را اضافه می‌کنیم
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /app/lib \
 && wget -O /app/lib/postgresql.jar https://jdbc.postgresql.org/download/postgresql-42.7.4.jar

# کدها
COPY ./src ./src

# در زمان اجرای کانتینر کامپایل و اجرا می‌کنیم (به‌خصوص چون src را mount می‌کنی)
CMD sh -c "javac -cp /app/lib/postgresql.jar src/*.java -d . && java -cp .:/app/lib/postgresql.jar Main"
