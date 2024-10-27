FROM debian:bookworm-slim AS build

RUN apt-get update && \
    apt-get install -y wget unzip

#OpenJDK 21
RUN wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz -P /tmp && \
    mkdir -p /usr/local/openjdk-21 && \
    tar -xzf /tmp/jdk-21_linux-x64_bin.tar.gz -C /usr/local/openjdk-21 --strip-components=1 && \
    ln -s /usr/local/openjdk-21/bin/java /usr/bin/java && \
    ln -s /usr/local/openjdk-21/bin/javac /usr/bin/javac

#Gradle 8.2
RUN wget https://services.gradle.org/distributions/gradle-8.2-bin.zip -P /tmp && \
    unzip /tmp/gradle-8.2-bin.zip -d /opt && \
    ln -s /opt/gradle-8.2/bin/gradle /usr/bin/gradle

WORKDIR /app
COPY . .
RUN gradle bootJar
FROM debian:bookworm-slim
COPY --from=build /usr/local/openjdk-21 /usr/local/openjdk-21
RUN ln -s /usr/local/openjdk-21/bin/java /usr/bin/java
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
